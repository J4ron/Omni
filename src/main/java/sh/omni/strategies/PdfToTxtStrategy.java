package sh.omni.strategies;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import sh.omni.domain.model.FileContent;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class PdfToTxtStrategy implements IConversionStrategy {

    @Override
    public boolean canHandle(String sourceFormat, String targetFormat) {
        return "pdf".equalsIgnoreCase(sourceFormat) && "txt".equalsIgnoreCase(targetFormat);
    }

    @Override
    public FileContent convert(FileContent source, String targetFormat) {
        File file = new File(source.filename());
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return new FileContent(
                    source.filename(),
                    text.getBytes(StandardCharsets.UTF_8),
                    "txt",
                    source.verbose()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert PDF to text: " + e.getMessage(), e);
        }
    }
}
