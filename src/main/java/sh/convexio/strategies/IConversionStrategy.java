package sh.convexio.strategies;
import sh.convexio.domain.model.FileContent;

/**
 * Represents a strategy interface for file format conversion. Implementations of this interface
 * define the logic required to convert files between specific formats.

 * This interface provides the ability to:
 * - Determine if a conversion strategy can handle a specific source format and target format.
 * - Perform the conversion of a file from its source format to the desired target format.
 */
public interface IConversionStrategy {
    boolean canHandle(String sourceFormat, String targetFormat);
    FileContent convert(FileContent source, String targetFormat);
}