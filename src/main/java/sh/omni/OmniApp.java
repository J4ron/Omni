package sh.omni;

import picocli.CommandLine;
import sh.omni.cli.OmniCliEntry;
import sh.omni.cli.commands.ConvertCommand;
import sh.omni.cli.commands.HelpCommand;
import sh.omni.cli.commands.VersionCommand;
import sh.omni.domain.ports.IConverter;
import sh.omni.domain.services.ConverterService;
import sh.omni.adapters.out.FileIoAdapter;
import sh.omni.strategies.StrategyRegistry;
import sh.omni.strategies.TxtToPdfStrategy;

public class OmniApp {
    public static void main(String[] args) {
        // Setup der StrategyRegistry & ConverterService (dein "Business-Logic"-Core)
        StrategyRegistry strategyRegistry = new StrategyRegistry();
        strategyRegistry.registerStrategy(new TxtToPdfStrategy());

        IConverter converter = new ConverterService(strategyRegistry, new FileIoAdapter());

        // Erstelle deine Commands und injiziere Abhängigkeiten
        ConvertCommand convertCommand = new ConvertCommand(converter);
        HelpCommand helpCommand = new HelpCommand();
        VersionCommand versionCommand = new VersionCommand();

        // Bau die CommandLine Instanz mit allen Subcommands manuell auf
        CommandLine cli = new CommandLine(new OmniCliEntry());
        cli.addSubcommand("convert", convertCommand);
        cli.addSubcommand("help", helpCommand);
        cli.addSubcommand("version", versionCommand);

        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }
}
