package sh.omni.adapters.in;

import sh.omni.cli.ports.ICommand;
import sh.omni.cli.service.CommandRegistry;
import sh.omni.domain.ports.IConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CliAdapter {
    private final IConverter converter;
    private final CommandRegistry registry;

    public CliAdapter(IConverter converter, CommandRegistry registry) {
        this.converter = converter;
        this.registry = registry;
    }

    public void handleInput(String[] args) {
        if (args == null || args.length == 0 || isHelpWithoutArgs(args)) {
            printLogoAndHelp();
            return;
        }

        String commandName = args[0];

        try {
            ICommand command = registry.findCommand(commandName);
            // Übergabe aller Argumente ohne das Kommando selbst (also ab Index 1)
            String[] commandArgs = new String[args.length - 1];
            System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);

            command.execute(commandArgs);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printLogoAndHelp();
        }
    }

    private boolean isHelpWithoutArgs(String[] args) {
        if (args.length == 1) {
            String cmd = args[0];
            return cmd.equalsIgnoreCase("help") || cmd.equalsIgnoreCase("-h") || cmd.equalsIgnoreCase("--help");
        }
        return false;
    }

    private void printLogoAndHelp() {
        try {
            InputStream inputStream = CliAdapter.class.getClassLoader().getResourceAsStream("logo.txt");
            if (inputStream == null) {
                System.err.println("logo.txt nicht gefunden");
                return;
            }

            List<String> logoLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logoLines.add(line);
                }
            }

            List<String> helpLines = getHelpStrings();

            int logoWidth = logoLines.stream().mapToInt(String::length).max().orElse(0);
            int totalLines = Math.max(logoLines.size(), helpLines.size());

            System.out.println();
            for (int i = 0; i < totalLines; i++) {
                String left = i < logoLines.size() ? logoLines.get(i) : "";
                String right = i < helpLines.size() ? helpLines.get(i) : "";
                System.out.printf("%-" + (logoWidth + 2) + "s%s%n", left, right);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden von logo.txt: " + e.getMessage());
        }
    }

    private List<String> getHelpStrings() {
        final String GREEN = "\033[92m";
        final String CYAN = "\033[96m";
        final String WHITE = "\033[97m";
        final String PINK_PURPLE = "\033[38;2;228;122;206m";
        final String GREY = "\033[90m";
        final String RESET = "\033[0m";

        return List.of(
                GREEN + "\t\t\tomni convert" + WHITE + " -i <inputFile> -o <outputFile> -t <targetFormat>" + CYAN + " [options]" + RESET,
                GREEN + "\t\t\tomni convert" + WHITE + " -bc -in <inputFolder> -out <outputFolder> -t <targetFormat>" + CYAN + " [options]" + RESET,
                "\t\t\t--------------------------------------------------------------------------------",
                CYAN + "\t\t\tDescription:" + RESET,
                GREY + "\t\t\tConvert single files or entire folders into another format" + RESET,
                "",
                CYAN + "\t\t\tRequired (Single File):" + RESET,
                PINK_PURPLE + "\t\t\t-i " + WHITE + "<file>" + GREY + "           Input file" + RESET,
                PINK_PURPLE + "\t\t\t-o " + WHITE + "<file>" + GREY + "           Output file" + RESET,
                PINK_PURPLE + "\t\t\t-t " + WHITE + "<format>" + GREY + "         Target format (e.g. pdf, png, txt)" + RESET,
                "",
                CYAN + "\t\t\tRequired (Batch Mode):" + RESET,
                PINK_PURPLE + "\t\t\t-bc" + GREY + "                    Enable batch mode" + RESET,
                PINK_PURPLE + "\t\t\t-in " + WHITE + "<folder>" + GREY + "         Input folder" + RESET,
                PINK_PURPLE + "\t\t\t-out " + WHITE + "<folder>" + GREY + "        Output folder" + RESET,
                PINK_PURPLE + "\t\t\t-t " + WHITE + "<format>" + GREY + "         Target format for all files" + RESET,
                "",
                CYAN + "\t\t\tOptional:" + RESET,
                PINK_PURPLE + "\t\t\t-q " + WHITE + "<1-100>" + GREY + "          Image quality (images or PDF)" + RESET,
                PINK_PURPLE + "\t\t\t-f " + WHITE + "<json>" + GREY + "           Fill PDF form with JSON data" + RESET,
                PINK_PURPLE + "\t\t\t--verbose" + GREY + "              Enable verbose output" + RESET
        );
    }
}
