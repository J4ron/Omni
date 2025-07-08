package sh.omni.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "version", description = "Shows the version of omni.sh")
public class VersionCommand implements Runnable {

    private static final String VERSION = "omni.sh v0.0.1 Development Snapshot";

    @Override
    public void run() {
        System.out.println(VERSION);
    }
}
