package sh.convexio.adapters.in;

import sh.convexio.domain.model.ConversionRequest;
import sh.convexio.domain.model.ConversionResult;
import sh.convexio.domain.ports.IConverter;

public class CliAdapter {
    private final IConverter converter;

    public CliAdapter(IConverter converter) {
        this.converter = converter;
    }

    public void handleConversion(String[] args) {
        if (args.length != 3) {
            printUsage();
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String targetFormat = args[2];

        try {
            ConversionRequest request = new ConversionRequest(inputPath, outputPath, targetFormat);
            ConversionResult result = converter.convert(request);

            if (result.success()) {
                System.out.println("Konvertierung erfolgreich: " + outputPath);
            } else {
                System.err.println("Konvertierung fehlgeschlagen: " + result.message());
            }
        } catch (Exception e) {
            System.err.println("Fehler bei der Konvertierung: " + e.getMessage());
        }
    }

    private void printUsage() {
        System.out.println("Verwendung: convexio <input-file> <output-file> <target-format>");
        System.out.println("Beispiel: convexio input.txt output.pdf pdf");
    }
}