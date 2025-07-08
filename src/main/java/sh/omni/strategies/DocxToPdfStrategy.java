package sh.omni.strategies;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import sh.omni.domain.model.FileContent;


public class DocxToPdfStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "docx".equalsIgnoreCase(sourceFormat)
                && "pdf".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        try (InputStream inputStream = new ByteArrayInputStream(source.content());
             XWPFDocument docx = new XWPFDocument(inputStream);
             PDDocument pdfDoc = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // PDF-Seite und Cursor-Position initialisieren
            PDPage page = new PDPage(PDRectangle.LETTER);
            pdfDoc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                float margin = 50;
                float yPosition = page.getMediaBox().getHeight() - margin;
                float leading = 14.5f; // Zeilenabstand

                contentStream.newLineAtOffset(margin, yPosition);

                // DOCX Paragrafs lesen und in PDF schreiben
                for (XWPFParagraph para : docx.getParagraphs()) {
                    String text = para.getText();
                    if (text == null || text.isEmpty()) {
                        yPosition -= leading; // Leerzeile
                        contentStream.newLineAtOffset(0, -leading);
                        continue;
                    }

                    // Zeilen in max Länge splitten (ca. 80 Zeichen, damit es auf Seite passt)
                    for (String line : splitText(text)) {
                        if (yPosition <= margin) {
                            // Neue Seite, wenn Rand erreicht
                            contentStream.endText();
                            contentStream.close();

                            page = new PDPage(PDRectangle.LETTER);
                            pdfDoc.addPage(page);
                            contentStream.close();
                            contentStream.close();
                            // Neuer ContentStream auf neuer Seite
                            try (PDPageContentStream csNew = new PDPageContentStream(pdfDoc, page)) {
                                csNew.beginText();
                                csNew.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                                yPosition = page.getMediaBox().getHeight() - margin;
                                csNew.newLineAtOffset(margin, yPosition);
                                csNew.showText(line);
                                csNew.newLineAtOffset(0, -leading);
                                yPosition -= leading;
                            }
                            // ContentStream aktualisieren
                            break;
                        } else {
                            contentStream.showText(line);
                            contentStream.newLineAtOffset(0, -leading);
                            yPosition -= leading;
                        }
                    }
                }

                contentStream.endText();
            }

            pdfDoc.save(baos);

            return new FileContent(
                    source.filename().replaceAll("\\.docx$", "") + ".pdf",
                    baos.toByteArray(),
                    "pdf",
                    false
            );

        } catch (Exception e) {
            throw new RuntimeException("DOCX to PDF conversion failed: " + e.getMessage(), e);
        }
    }

    // Hilfsmethode: Text in Zeilen mit max Länge splitten
    private static String[] splitText(String text) {
        if (text.length() <= 80) {
            return new String[] { text };
        }

        int parts = (text.length() + 80 - 1) / 80;
        String[] lines = new String[parts];

        for (int i = 0; i < parts; i++) {
            int start = i * 80;
            int end = Math.min(start + 80, text.length());
            lines[i] = text.substring(start, end);
        }

        return lines;
    }
}
