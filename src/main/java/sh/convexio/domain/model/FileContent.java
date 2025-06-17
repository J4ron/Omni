package sh.convexio.domain.model;

/**
 * Represents the content of a file, including its name, raw content as a byte array,
 * and the file format or extension.

 * This class is used to encapsulate all necessary data related to a specific file,
 * which can be used in various operations such as file reading, writing, and format
 * conversion.

 * Fields:
 * - filename: the name or path of the file.
 * - content: the raw binary content of the file.
 * - Format: the format or extension of the file, typically derived from its name.

 * Instances of this class are immutable and suitable for use in functional programming
 * or concurrent scenarios, as they ensure the encapsulated file data cannot be modified
 * once created.
 */
public record FileContent(
        String filename,
        byte[] content,
        String format
) {}