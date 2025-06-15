
package sh.convexio.strategies;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import sh.convexio.domain.model.FileContent;

import java.io.IOException;


public class TxtToPdfStrategy implements IConversionStrategy {
    private static final float DEFAULT_FONT_SIZE = 12f;
    private static final Standard14Fonts.FontName DEFAULT_FONT = Standard14Fonts.FontName.HELVETICA;
    private static final float DEFAULT_MARGIN_LEFT = 50f;
    private static final float DEFAULT_MARGIN_TOP = 700f;
    private static final float DEFAULT_LINE_SPACING = -15f;

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "txt".equalsIgnoreCase(sourceFormat) &&
                "pdf".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                setDocumentFont(contentStream, DEFAULT_FONT_SIZE);
                contentStream.newLineAtOffset(DEFAULT_MARGIN_LEFT, DEFAULT_MARGIN_TOP);

                String text = new String(source.content(), "UTF-8");
                String[] lines = text.split("\n");

                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, DEFAULT_LINE_SPACING);
                }

                contentStream.endText();
            }

            try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
                document.save(baos);
                return new FileContent(
                        source.filename(),
                        baos.toByteArray(),
                        "pdf"
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("PDF-Konvertierung fehlgeschlagen: " + e.getMessage(), e);
        }
    }

    private void setDocumentFont(PDPageContentStream contentStream, float fontSize) {
        try {
            if (fontSize <= 0) {
                fontSize = DEFAULT_FONT_SIZE;
            }
            contentStream.setFont(new PDType1Font(DEFAULT_FONT), fontSize);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Setzen der Schriftart: " + e.getMessage(), e);
        }
    }
}