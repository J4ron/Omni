package sh.omni.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import sh.omni.domain.model.ConversionRequest;
import sh.omni.domain.ports.IConverter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Command(name = "convert", description = "Convert files or folders to a different format.")
public class ConvertCommand implements Runnable {

    @Option(names = {"-i", "--input"}, description = "Input file")
    private String inputFile;

    @Option(names = {"-o", "--output"}, description = "Output file")
    private String outputFile;

    @Option(names = {"-in"}, description = "Input folder (batch mode)")
    private String inputFolder;

    @Option(names = {"-out"}, description = "Output folder (batch mode)")
    private String outputFolder;

    @Option(names = {"-t", "--target"}, description = "Target format (e.g., pdf, png, txt)", required = true)
    private String targetFormat;

    @Option(names = {"-bc", "--batch"}, description = "Enable batch mode")
    private boolean batch = false;

    @Option(names = {"-q", "--quality"}, description = "Image quality (1-100)")
    private Integer quality;

    @Option(names = {"--dpi"}, description = "Image DPI (dots per inch), e.g. 300")
    private Integer dpi;

    @Option(names = {"-f", "--formdata"}, description = "Fill PDF form with JSON data")
    private String formData;

    @Option(names = {"-v", "--verbose"}, description = "Enable verbose output")
    private boolean verbose = false;

    @Option(names = {"--merge"}, description = "Merge all input files into one output")
    private boolean merge = false;

    private final IConverter converter;

    public ConvertCommand() {
        this.converter = null;
    }

    public ConvertCommand(IConverter converter) {
        this.converter = converter;
    }

    @Override
    public void run() {
        if (batch) {
            if (inputFolder == null || outputFolder == null) {
                System.err.println("Batch mode requires -in <input folder> and -out <output folder>");
                return;
            }
        } else {
            if (inputFile == null) {
                System.err.println("Single file conversion requires -i <input file>");
                return;
            }
            if (outputFile == null) {
                outputFile = generateOutputFileName(inputFile, targetFormat);
            }
        }

        ConversionRequest request = new ConversionRequest(
                batch ? inputFolder : inputFile,
                batch ? outputFolder : outputFile,
                targetFormat.toLowerCase(),
                batch,
                quality,
                dpi,
                formData,
                verbose,
                merge
        );

        try {
            var result = converter.convert(request);
            if (result.success()) {
                System.out.println("Conversion successful.");
            } else {
                System.err.println("Error: " + result.message());
            }
        } catch (Exception e) {
            System.err.println("Conversion failed: " + e.getMessage());
        }
    }

    private String generateOutputFileName(String inputPath, String targetFormat) {
        Path input = Paths.get(inputPath);
        String fileName = input.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        String fileNameWithoutExt = (lastDot == -1) ? fileName : fileName.substring(0, lastDot);
        Path parent = input.getParent();
        String newFileName = fileNameWithoutExt + "." + targetFormat.toLowerCase();
        return (parent != null) ? parent.resolve(newFileName).toString() : newFileName;
    }
}
