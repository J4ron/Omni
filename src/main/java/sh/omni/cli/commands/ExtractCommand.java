package sh.omni.cli.commands;

import picocli.CommandLine;
import sh.omni.domain.model.ConversionRequest;
import sh.omni.domain.ports.IConverter;

@CommandLine.Command(name = "extract", description = "Extracts visual content (images) from PDF, DOCX etc.")
public class ExtractCommand implements Runnable {

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input file", required = true)
    private String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output file", required = false)
    private String outputFile;

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Enable verbose output")
    private boolean verbose;

    private final IConverter converter;

    public ExtractCommand(IConverter converter) {
        this.converter = converter;
    }

    @Override
    public void run() {
        String ext = inputFile.substring(inputFile.lastIndexOf('.') + 1).toLowerCase();
        String target = "extract";

        if (outputFile == null) {
            outputFile = inputFile.replaceAll("\\.[^.]+$", "") + "_extracted.zip";
        }

        ConversionRequest request = new ConversionRequest(
                inputFile,
                outputFile,
                target,
                false,
                null,
                null,
                verbose
        );

        var result = converter.convert(request);
        if (result.success()) {
            System.out.println("Extraction successful.");
        } else {
            System.err.println("Extraction failed: " + result.message());
        }
    }
}
