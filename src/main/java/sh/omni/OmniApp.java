package sh.omni;

import picocli.CommandLine;
import sh.omni.cli.OmniCliEntry;
import sh.omni.cli.commands.ConvertCommand;
import sh.omni.cli.commands.ExtractCommand;
import sh.omni.cli.commands.HelpCommand;
import sh.omni.cli.commands.VersionCommand;
import sh.omni.domain.ports.IConverter;
import sh.omni.domain.services.ConverterService;
import sh.omni.adapters.out.FileIoAdapter;
import sh.omni.strategies.*;

public class OmniApp {
    public static void main(String[] args) {
        StrategyRegistry strategyRegistry = new StrategyRegistry();

        // Txt
        strategyRegistry.registerStrategy(new TxtToPdfStrategy());
        strategyRegistry.registerStrategy(new TxtToDocxStrategy());

        // Office
        strategyRegistry.registerStrategy(new DocxToTxtStrategy());
        strategyRegistry.registerStrategy(new DocxToPdfStrategy());

        // Pdf
        strategyRegistry.registerStrategy(new PdfToTxtStrategy());
        strategyRegistry.registerStrategy(new PdfToPngStrategy());
        strategyRegistry.registerStrategy(new MergedImagesToPdfStrategy());


        CommandLine cli = getCommandLine(strategyRegistry);

        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }

    private static CommandLine getCommandLine(StrategyRegistry strategyRegistry) {
        IConverter converter = new ConverterService(strategyRegistry, new FileIoAdapter());

        // Commands mit konvertierer
        ConvertCommand convertCommand = new ConvertCommand(converter);
        ExtractCommand exctractCommand = new ExtractCommand(converter);
        HelpCommand helpCommand = new HelpCommand();
        VersionCommand versionCommand = new VersionCommand();

        CommandLine cli = new CommandLine(new OmniCliEntry());
        cli.addSubcommand("convert", convertCommand);
        cli.addSubcommand("extract", exctractCommand);
        cli.addSubcommand("help", helpCommand);
        cli.addSubcommand("version", versionCommand);
        return cli;
    }
}

