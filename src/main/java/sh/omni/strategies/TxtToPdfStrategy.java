package sh.omni.strategies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import sh.omni.domain.model.FileContent;
import sh.omni.domain.ports.IPdfConstants;

public class TxtToPdfStrategy implements IConversionStrategy, IPdfConstants {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "txt".equalsIgnoreCase(sourceFormat)
                && "pdf".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        boolean verbose = source.verbose(); // Nutzt verbose-Flag aus ConversionRequest

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                setDocumentFont(contentStream);
                contentStream.newLineAtOffset(DEFAULT_MARGIN_LEFT, DEFAULT_MARGIN_TOP);

                String text = new String(source.content(), StandardCharsets.UTF_8);
                String[] lines = text.replaceAll("\r\n?", "\n").split("\n");

                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    try {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, DEFAULT_LINE_SPACING);
                        if (verbose) {
                            System.out.println("[TXT→PDF] Line " + (i + 1) + ": " + line);
                        }
                    } catch (Exception e) {
                        System.err.println("[ERROR] Failed to write line " + (i + 1) + ": " + line);
                        System.err.println("Reason: " + e.getMessage());
                    }
                }

                contentStream.endText();
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                document.save(baos);
                if (verbose) {
                    System.out.println("[TXT→PDF] Conversion finished successfully.");
                }
                return new FileContent(
                        source.filename(),
                        baos.toByteArray(),
                        "pdf",
                        source.verbose() // Beibehaltung des verbose Flags
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("PDF conversion failed: " + e.getMessage(), e);
        }
    }

    private void setDocumentFont(PDPageContentStream contentStream) {
        try {
            contentStream.setFont(new PDType1Font(DEFAULT_FONT), DEFAULT_FONT_SIZE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to set font: " + e.getMessage(), e);
        }
    }
}
