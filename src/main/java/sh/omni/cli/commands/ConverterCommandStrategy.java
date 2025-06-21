package sh.omni.cli.commands;

import sh.omni.cli.ports.ICommand;
import sh.omni.domain.ports.IConverter;
import sh.omni.domain.model.ConversionRequest;

public class ConverterCommandStrategy implements ICommand {
    private final IConverter converter;

    public ConverterCommandStrategy(IConverter converter) {
        this.converter = converter;
    }

    @Override
    public String getCommandName() {
        return "convert";
    }

    @Override
    public boolean canHandle(String command) {
        return "convert".equals(command);
    }

    @Override
    public void execute(String[] args) {
        // Minimal args check: -i/-o/-t at least or -bc + -in/-out + -t
        if (args.length == 0) {
            System.err.println("No arguments provided. Use 'omni help' for usage details.");
            System.out.println(getHelp());
            return;
        }

        String inputFile = null;
        String outputFile = null;
        String inputFolder = null;
        String outputFolder = null;
        String targetFormat = null;
        boolean batch = false;
        Integer quality = null;
        String formData = null;
        boolean verbose = false;

        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-i", "--input" -> inputFile = args[++i];
                    case "-o", "--output" -> outputFile = args[++i];
                    case "-in" -> inputFolder = args[++i];
                    case "-out" -> outputFolder = args[++i];
                    case "-t", "--target" -> targetFormat = args[++i].toLowerCase();
                    case "-bc", "--batch" -> batch = true;
                    case "-q", "--quality" -> quality = Integer.parseInt(args[++i]);
                    case "-f", "--formdata" -> formData = args[++i];
                    case "-v", "--verbose" -> verbose = true;
                    default -> {
                        // ignore unknown args or handle errors if you want
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Missing value for parameter. Use 'omni help convert' for usage details.");
            System.out.println(getHelp());
            return;
        }

        // Validate arguments
        if (batch) {
            if (inputFolder == null || outputFolder == null) {
                System.err.println("Batch mode requires -in <input folder> and -out <output folder>.");
                System.out.println(getHelp());
                return;
            }
            if (targetFormat == null) {
                System.err.println("Target format (-t) must be specified.");
                System.out.println(getHelp());
                return;
            }
            // Proceed with batch conversion logic here (call batch convert method)
        } else {
            if (inputFile == null || outputFile == null || targetFormat == null) {
                System.err.println("Single file conversion requires -i <input file>, -o <output file> and -t <target format>.");
                System.out.println(getHelp());
                return;
            }
            // Proceed with single file conversion logic here
        }

        try {
            // Build ConversionRequest accordingly
            var request = new ConversionRequest(
                    batch ? inputFolder : inputFile,
                    batch ? outputFolder : outputFile,
                    targetFormat,
                    batch,
                    quality,
                    formData,
                    verbose
            );
            converter.convert(request);
            System.out.println("Conversion successful.");
        } catch (Exception e) {
            System.err.println("Conversion failed: " + e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return """
            Usage: omni convert [options]

            Converts files or folders from one format to another.

            Single file conversion:
              -i, --input <file>       Input file path (source format auto-detected)
              -o, --output <file>      Output file path
              -t, --target <format>    Target format (e.g. pdf, docx, txt, jpg, png, csv, json, yaml)

            Batch conversion (folders):
              -bc, --batch             Enable batch mode
              -in <input folder>       Input folder path
              -out <output folder>     Output folder path
              -t, --target <format>    Target format for all files in batch

            Optional parameters:
              -q, --quality <1-100>    Image quality (only for image/PDF conversions)
              -f, --formdata <file>    JSON file path for PDF form data filling
              -v, --verbose            Enable detailed logging output

            Examples:
              # Convert single DOCX to PDF
              omni convert -i report.docx -o report.pdf -t pdf

              # Batch convert PNG images to JPG with quality 80%
              omni convert -bc -in /input/pngs -out /output/jpgs -t jpg -q 80

              # Fill a PDF form with JSON data
              omni convert -i form.pdf -o filled_form.pdf -t pdf -f formdata.json
            """;
    }
}
