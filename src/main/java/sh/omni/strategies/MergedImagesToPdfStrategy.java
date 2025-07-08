package sh.omni.strategies;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import sh.omni.domain.model.FileContent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class MergedImagesToPdfStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "png-multi".equalsIgnoreCase(sourceFormat) && "pdf".equalsIgnoreCase(targetFormat);
    }

    public FileContent convert(List<FileContent> images, String targetFormat, String outputName, boolean verbose) {
        try (PDDocument document = new PDDocument()) {
            for (FileContent imageContent : images) {
                BufferedImage image = ImageIO.read(new File(imageContent.filename()));
                PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                document.addPage(page);

                PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
                try (PDPageContentStream contents = new PDPageContentStream(document, page)) {
                    contents.drawImage(pdImage, 0, 0);
                }
                if (verbose) System.out.println("[IMG→PDF] added " + imageContent.filename());
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);

            return new FileContent(
                    outputName,
                    baos.toByteArray(),
                    "pdf",
                    verbose
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge images into one PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        throw new UnsupportedOperationException("MergedImagesToPdfStrategy requires a list of FileContent.");
    }
}
