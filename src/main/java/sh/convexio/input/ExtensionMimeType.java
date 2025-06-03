package sh.convexio.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ExtensionMimeType implements MimeType {

    /**
     * A set that holds all supported file extensions (in lowercase),
     * loaded from the resource file "supported_extensions.csv".
     */
    private static final Set<String> supportedExtensions = new HashSet<>();

    /*
      Static block to initialize the supportedExtensions set once when the class is loaded.
      It reads the CSV file line by line, trims whitespace, converts to lowercase,
      and stores each extension in the set.
     */
    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                // Load the resource file from the classpath
                Objects.requireNonNull(
                        ExtensionMimeType.class.getClassLoader()
                                .getResourceAsStream("supported_extensions.csv")
                )))
        ) {
            String line;
            // Read each line until EOF
            while ((line = reader.readLine()) != null) {
                // Trim whitespace and convert to lowercase before adding
                supportedExtensions.add(line.trim().toLowerCase());
            }
        } catch (IOException | NullPointerException e) {
            // Print error if file is missing or reading fails
            System.err.println("Could not load supported_extensions.csv: " + e.getMessage());
        }
    }

    /**
     * Checks if the given filename has a supported extension.
     *
     * @param filename The filename to check, e.g. "file.docx"
     * @return An Optional containing the extension in lowercase if supported,
     *         or Optional.empty() if unsupported or invalid.
     */
    @Override
    public Optional<String> getExtensionIfSupported(String filename) {
        // Return empty if filename is null or doesn't contain a dot (no extension)
        if (filename == null || !filename.contains(".")) return Optional.empty();

        // Extract the raw extension substring after the last dot
        String extRaw = filename.substring(filename.lastIndexOf('.') + 1);

        // If the raw extension starts with a whitespace, consider it invalid
        if (extRaw.startsWith(" ")) {
            return Optional.empty();
        }

        // Normalize extension: convert to lowercase and trim surrounding whitespace
        String ext = extRaw.toLowerCase().trim();

        // Check if the normalized extension is in the supported set
        return supportedExtensions.contains(ext) ? Optional.of(ext) : Optional.empty();
    }



}
