package sh.omni.adapters.out;

import org.apache.commons.io.FileUtils;
import sh.omni.domain.model.FileContent;
import sh.omni.domain.ports.IFileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A concrete implementation of {@link IFileSystem} that handles file input/output operations.
 * This class provides functionality for reading from and writing to the file system,
 * encapsulating file content details within {@link FileContent} instances.

 * Core responsibilities include:
 * - Reading a file's contents from a specified path and returning it as a {@link FileContent} object.
 * - Writing the contents of a {@link FileContent} object to a specified path, ensuring that
 *   the necessary directories are created and file system permissions are validated.
 */
public class FileIoAdapter implements IFileSystem {
    @Override
    public FileContent readFile(String path) {
        try {
            byte[] content = Files.readAllBytes(Path.of(path));
            String format = getFileFormat(path);
            return new FileContent(path, content, format);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: " + path, e);
        }
    }

    /**
     * Writes the content of a {@link FileContent} object to a specified file path.
     * This method ensures that the necessary directories exist, validates file permissions,
     * and performs the write operation.
     * If the path or content is null, or if any I/O error occurs during the process,
     * appropriate exceptions will be thrown.
     *
     * @param path the absolute or relative file path where the content will be written.
     *             Must not be null.
     * @param content the {@link FileContent} object containing the data to be written to the file.
     *                Must not be null, and its underlying content must not be null.
     * @throws IllegalArgumentException if the path, content*/
    @Override
    public void writeFile(String path, FileContent content) {
        if (path == null || content == null || content.content() == null) {
            throw new IllegalArgumentException("Path and data cannot be null");
        }

        File file = new File(path);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new RuntimeException("Final Directory was not found: " + parent);
        }


        if (!Objects.requireNonNull(parent).canWrite()) {
            throw new RuntimeException("Not enugh rights to convert: " + parent);
        }

        try {
            FileUtils.writeByteArrayToFile(file, content.content());
        } catch (IOException e) {
            throw new RuntimeException("Error while converting file: " + path +
                    " - " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the file format (extension) from the provided file path.
     * The method determines the substring after the last period ('.') in the file path,
     * which is typically used to indicate the file type.
     *
     * @param path the file path from which the format (extension) is to be extracted.
     *             Must not be null or empty.
     * @return the file format (extension) in lowercase. If no period is found or the file has no
     *         extension, an empty string is returned.
     */
    private String getFileFormat(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return extension.toLowerCase();
    }
}