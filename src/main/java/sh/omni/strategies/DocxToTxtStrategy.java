package sh.omni.strategies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import sh.omni.domain.model.FileContent;

public class DocxToTxtStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "docx".equalsIgnoreCase(sourceFormat)
                && "txt".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        try (XWPFDocument document = new XWPFDocument(new java.io.ByteArrayInputStream(source.content()));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            StringBuilder textBuilder = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                textBuilder.append(paragraph.getText()).append(System.lineSeparator());
            }

            baos.write(textBuilder.toString().getBytes());

            return new FileContent(
                    replaceExtension(source.filename(), targetFormat),
                    baos.toByteArray(),
                    targetFormat.toLowerCase(),
                    false
            );
        } catch (IOException e) {
            throw new RuntimeException("DOCX to TXT conversion failed: " + e.getMessage(), e);
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
