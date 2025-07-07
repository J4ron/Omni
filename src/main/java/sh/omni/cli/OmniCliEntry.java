package sh.omni.cli;

import picocli.CommandLine;

@CommandLine.Command(
        name = "omni",
        mixinStandardHelpOptions = true,
        version = "omni.sh v1.0.0",
        description = "Universal file converter CLI"
)
public class OmniCliEntry implements Runnable {
    @Override
    public void run() {
        System.out.println("Welcome to omni.sh - use `omni help` for available commands.");
    }
}
