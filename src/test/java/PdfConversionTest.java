import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;

class PDFConversionTest {
    public static void main(String[] args) {
        try {
            // Erstelle ein neues PDF Dokument
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Erstelle den Content Stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Setze Font und Größe
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

            String text = "Dies ist ein Test. Üäöß";

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText(text);
            contentStream.endText();

            contentStream.close();

            File directory = new File("C:\\Users\\Jaron\\Desktop\\ConvexioOut");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            document.save("C:\\Users\\Jaron\\Desktop\\ConvexioOut\\test_output.pdf");

            document.close();

            System.out.println("PDF wurde erfolgreich erstellt!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}