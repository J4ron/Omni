package sh.omni.cli.ports;

public interface ICommand {
    void execute(String[] args);
    String getHelp();
    String getCommandName();
    boolean canHandle(String command);
}
