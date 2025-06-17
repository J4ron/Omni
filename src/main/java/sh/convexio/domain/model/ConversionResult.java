package sh.convexio.domain.model;

/**
 * Represents the result of a file or content conversion process.
 * The result encapsulates whether the conversion was successful,
 * an informational message about the process, and the converted content
 * if the operation was successful.

 * Fields:
 * - success: Indicates whether the conversion was successful.
 * - message: Provides additional information about the conversion result,
 *            such as error details in case of failure or a success description.
 * - convertedContent: Contains the converted file content if the operation
 *                     was successful; otherwise, null.

 * Methods:
 * - success(FileContent): A factory method to create a successful conversion result
 *                         with the converted content and a success message.
 * - Error(String): A factory method to create a failed conversion result with an error
 *                  message and no content.

 * This record is primarily used to standardize responses from conversion processes
 * and allow clients to determine the outcome of an operation.
 */
public record ConversionResult(
        boolean success,
        String message,
        FileContent convertedContent
) {
    /**
     * Creates a successful {@code ConversionResult} instance with the provided converted file content.
     * The result indicates a successful conversion operation with a predefined success message.
     *
     * @param content the converted file content, encapsulated in a {@link FileContent} instance.
     *                Must not be null.
     * @return a {@code ConversionResult} instance representing a successful conversion,
     *         containing the success status, a success message, and the converted file content.
     */
    public static ConversionResult success(FileContent content) {
        return new ConversionResult(true, "Conversion successful", content);
    }

    /**
     * Creates a failed {@code ConversionResult} instance with an error message and no converted content.
     * The result indicates a failed conversion operation.
     *
     * @param errorMessage the error message describing the reason for the failure.
     *                     Must not be null or empty.
     * @return a {@code ConversionResult} instance representing a failed conversion,
     *         containing the failure status, the provided error message, and no content.
     */
    public static ConversionResult error(String errorMessage) {
        return new ConversionResult(false, errorMessage, null);
    }
}