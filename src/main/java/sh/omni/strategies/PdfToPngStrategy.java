package sh.omni.strategies;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import sh.omni.domain.model.ConversionRequest;
import sh.omni.domain.model.FileContent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfToPngStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "pdf".equalsIgnoreCase(sourceFormat) && "png".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        // ConversionRequest muss in FileContent rein oder anders weitergereicht werden,
        // hier nehmen wir an, dass source.getConversionRequest() existiert,
        // ansonsten musst du die DPI und Quality anders übergeben (z.B. per ctor)
        int dpi = 300; // Default DPI

        if (source instanceof FileContentWithParams fp) {
            dpi = fp.getDpi() != null ? fp.getDpi() : 300;
        }

        try (PDDocument document = Loader.loadPDF(new File(source.filename()))) {
            PDFRenderer renderer = new PDFRenderer(document);
            ByteArrayOutputStream zipOutput = new ByteArrayOutputStream();

            try (ZipOutputStream zip = new ZipOutputStream(zipOutput)) {
                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage image = renderer.renderImageWithDPI(page, dpi);
                    ByteArrayOutputStream pageOutput = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", pageOutput);

                    String entryName = "page_" + (page + 1) + ".png";
                    zip.putNextEntry(new ZipEntry(entryName));
                    zip.write(pageOutput.toByteArray());
                    zip.closeEntry();
                }
            }

            return new FileContent(
                    source.filename(),
                    zipOutput.toByteArray(),
                    "zip", // mehrere PNGs als ZIP zurückgeben
                    source.verbose()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert PDF to PNGs: " + e.getMessage(), e);
        }
    }
}
