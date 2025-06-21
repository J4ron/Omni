package sh.omni.cli.commands;

import sh.omni.cli.ports.ICommand;
import sh.omni.cli.service.CommandRegistry;

public class HelpCommandStrategy implements ICommand {
    private final CommandRegistry registry;

    public HelpCommandStrategy(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public boolean canHandle(String command) {
        return "help".equals(command) || "-h".equals(command) || "--help".equals(command);
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Verfügbare Befehle:");
        registry.getAllCommands()
                .forEach(cmd -> System.out.printf("  %s\t%s%n",
                        cmd.getCommandName(), cmd.getHelp()));
    }

    @Override
    public String getHelp() {
        return "Zeigt diese Hilfe an";
    }
}