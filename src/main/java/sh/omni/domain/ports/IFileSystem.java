package sh.omni.domain.ports;
import sh.omni.domain.model.FileContent;

/**
 * Provides an abstraction for file system operations, including reading from and writing to files.
 * Implementations of this interface are responsible for handling low-level file I/O operations
 * while abstracting away the underlying file system details.
 * Methods:
 * - readFile: Reads the contents of a file at a specified path and returns its data encapsulated in
 *   a {@link FileContent} object.
 * - writeFile: Writes data from a {@link FileContent} object to a file at a specified path, creating
 *   any necessary directories if they do not exist.
 */
public interface IFileSystem {
    FileContent readFile(String path);
    void writeFile(String path, FileContent content);
}