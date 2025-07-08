package sh.omni.strategies;
import sh.omni.domain.model.FileContent;

import java.io.IOException;

public class TsToMp4Strategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "ts".equalsIgnoreCase(sourceFormat) && "mp4".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        String inputPath = source.filename();
        String outputPath = inputPath.substring(0, inputPath.lastIndexOf('.')) + "." + targetFormat.toLowerCase();

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-i", inputPath,
                "-c:v", "copy",
                "-c:a", "aac",
                "-b:a", "192k",
                "-bsf:a", "aac_adtstoasc",
                outputPath
        );

        try {
            Process process = pb.inheritIO().start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("ffmpeg failed with exit code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("ffmpeg process failed: " + e.getMessage(), e);
        }

        // Return the converted file path wrapped as FileContent (content can be null or loaded if needed)
        return new FileContent(outputPath, null, targetFormat.toLowerCase(), source.verbose());
    }
}
