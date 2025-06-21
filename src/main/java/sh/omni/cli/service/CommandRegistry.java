package sh.omni.cli.service;

import sh.omni.cli.ports.ICommand;
import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {
    private final List<ICommand> commands = new ArrayList<>();

    public void registerCommand(ICommand command) {
        commands.add(command);
    }

    public ICommand findCommand(String commandName) {
        return commands.stream()
                .filter(cmd -> cmd.canHandle(commandName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unbekannter Befehl: " + commandName));
    }

    public List<ICommand> getAllCommands() {
        return new ArrayList<>(commands);
    }
}