package sh.omni.cli.commands;

import picocli.CommandLine.Command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Command(name = "help", description = "Displays help with usage and available commands", mixinStandardHelpOptions = true)
public class HelpCommand implements Runnable {

    @Override
    public void run() {
        printLogoAndHelp();
    }

    private void printLogoAndHelp() {
        try (InputStream inputStream = HelpCommand.class.getClassLoader().getResourceAsStream("logo.txt")) {
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
        } catch (Exception e) {
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
                GREEN + "\tomni convert" + WHITE + " -i <inputFile> -o <outputFile> -t <targetFormat>" + CYAN + " [options]" + RESET,
                GREEN + "\tomni convert" + WHITE + " -bc -in <inputFolder> -out <outputFolder> -t <targetFormat>" + CYAN + " [options]" + RESET,
                "\t--------------------------------------------------------------------------------",
                CYAN + "\tDescription:" + RESET,
                GREY + "\tConvert single files or entire folders into another format" + RESET,
                "",
                CYAN + "\tRequired (Single File):" + RESET,
                PINK_PURPLE + "\t-i " + WHITE + "<file>" + GREY + "           Input file" + RESET,
                PINK_PURPLE + "\t-o " + WHITE + "<file>" + GREY + "           Output file" + RESET,
                PINK_PURPLE + "\t-t " + WHITE + "<format>" + GREY + "         Target format (e.g. pdf, png, txt)" + RESET,
                "",
                CYAN + "\tRequired (Batch Mode):" + RESET,
                PINK_PURPLE + "\t-bc" + GREY + "                    Enable batch mode" + RESET,
                PINK_PURPLE + "\t-in " + WHITE + "<folder>" + GREY + "         Input folder" + RESET,
                PINK_PURPLE + "\t-out " + WHITE + "<folder>" + GREY + "        Output folder" + RESET,
                PINK_PURPLE + "\t-t " + WHITE + "<format>" + GREY + "         Target format for all files" + RESET,
                "",
                CYAN + "\tOptional:" + RESET,
                PINK_PURPLE + "\t-q " + WHITE + "<1-100>" + GREY + "          Image quality (images or PDF)" + RESET,
                PINK_PURPLE + "\t-f " + WHITE + "<json>" + GREY + "           Fill PDF form with JSON data" + RESET,
                PINK_PURPLE + "\t--verbose" + GREY + "              Enable verbose output" + RESET
        );
    }
}
