package sh.convexio.input;

import java.util.Optional;

public interface MimeType {
    /**
     * Checks if the file extension is supported.
     *
     * @param filename e.g. "file.docx"
     * @return Optional containing the extension if supported, otherwise empty
     */
    Optional<String> getExtensionIfSupported(String filename);
}
