package sh.omni.strategies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import sh.omni.domain.model.FileContent;

public class TxtToDocxStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "txt".equalsIgnoreCase(sourceFormat)
                && "docx".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        String text = new String(source.content(), StandardCharsets.UTF_8);
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            for (String line : text.replaceAll("\r\n?", "\n").split("\n")) {
                XWPFParagraph paragraph = document.createParagraph();
                paragraph.createRun().setText(line);
            }

            document.write(baos);
            return new FileContent(
                    replaceExtension(source.filename(), targetFormat),
                    baos.toByteArray(),
                    targetFormat.toLowerCase(),
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException("TXT to DOCX conversion failed: " + e.getMessage(), e);
        }
    }

    private String replaceExtension(String filename, String newExt) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return filename + "." + newExt;
        }
        return filename.substring(0, dotIndex) + "." + newExt;
    }
}
