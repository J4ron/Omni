package sh.omni.adapters.in;

import sh.omni.cli.service.CommandRegistry;
import sh.omni.cli.ports.ICommand;
import sh.omni.domain.ports.IConverter;

public class CliAdapter {
    private final CommandRegistry commandRegistry;
    private final IConverter converter;

    public CliAdapter(IConverter converter, CommandRegistry commandRegistry) {
        this.converter = converter;
        this.commandRegistry = commandRegistry;
    }

    public void handleInput(String[] args) {
        if (args.length == 0) {
            showHelp();
            return;
        }

        String commandName = args[0];
        String[] commandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, commandArgs, 0, args.length - 1);

        try {
            ICommand command = commandRegistry.findCommand(commandName);
            command.execute(commandArgs);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            showHelp();
        }
    }

    private void showHelp() {
        System.out.println("Verfügbare Befehle:");
        commandRegistry.getAllCommands()
                .forEach(cmd -> System.out.printf("  %s\t%s%n",
                        cmd.getCommandName(), cmd.getHelp()));
    }
}
