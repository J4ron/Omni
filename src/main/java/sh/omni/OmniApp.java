package sh.omni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OmniApp {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = OmniApp.class.getClassLoader().getResourceAsStream("logo.txt");
        if (inputStream == null) {
            throw new IOException("logo.txt not found in resources");
        }

        List<String> logoLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logoLines.add(line);
            }
        }

        List<String> helpLines = getStrings();

        int logoWidth = logoLines.stream().mapToInt(String::length).max().orElse(0);
        int totalLines = Math.max(logoLines.size(), helpLines.size());

        System.out.println();
        for (int i = 0; i < totalLines; i++) {
            String left = i < logoLines.size() ? logoLines.get(i) : "";
            String right = i < helpLines.size() ? helpLines.get(i) : "";
            System.out.printf("%-" + (logoWidth + 2) + "s%s%n", left, right);
        }
    }

    private static List<String> getStrings() {
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
