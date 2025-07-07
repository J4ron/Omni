package sh.omni.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "help", description = "Shows an List of Commands")
public class HelpCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Execute one of the following commands:");
        System.out.println("  help     Shows help");
        System.out.println("  convert  Converts files or folders between supported formats");
        System.out.println("  version  Shows the current version of omni.sh");
    }
}
