package sh.omni.domain.model;

/**
 * Represents a request for content conversion, encapsulating the necessary
 * details required to perform a file or format conversion operation.

 * This record is primarily used to transfer data between different components
 * of the system that handle content conversions.

 * Fields:
 * - inputPath: The file path or location of the source content to be converted.
 *              This is the input for the conversion process.
 * - outputPath: The file path or location where the converted content should be saved.
 * - targetFormat: The desired format of the converted content, such as "PDF", "DOCX", etc.

 * Instances of this record are immutable and provide a convenient way to encapsulate
 * conversion-related parameters in a single object.
 */
public record ConversionRequest(
        String inputPath,
        String outputPath,
        String targetFormat,
        boolean batch,
        Integer quality,
        String formDataPath,
        boolean verbose
) {}
