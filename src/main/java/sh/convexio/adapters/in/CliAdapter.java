package sh.convexio.adapters.in;

import sh.convexio.domain.model.ConversionRequest;
import sh.convexio.domain.model.ConversionResult;
import sh.convexio.domain.ports.IConverter;

/**
 * CliAdapter serves as an interface between a command-line client and a conversion service.
 * It handles parsing input arguments, passing them to the appropriate converter logic, and
 * reporting the results back to the console.

 * Responsibilities:
 * - Parses the arguments received from the command line, which specify the input file path,
 *   output file path, and target format for conversion.
 * - Delegates the conversion task to an implementation of {@link IConverter}.
 * - Handles and displays any errors or success messages that result from the conversion process.

 * Constructor:
 * - CliAdapter(IConverter converter): Creates a new instance of CliAdapter
 *   with the specified conversion service. The provided {@link IConverter}
 *   implementation will handle the conversion logic required for the operation.

 * Methods:
 * - handleConversion(String[] args): Processes the supplied command-line arguments
 *   to perform a file or format*/
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
                System.out.println("Conversation Success: " + outputPath);
            } else {
                System.err.println("Conversation Error: " + result.message());
            }
        } catch (Exception e) {
            System.err.println("Error while trying to convert: " + e.getMessage());
        }
    }

    private void printUsage() {
        System.out.println("/");
        System.out.println("/");
    }
}