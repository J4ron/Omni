package sh.convexio.domain.ports;

import sh.convexio.domain.model.ConversionRequest;
import sh.convexio.domain.model.ConversionResult;

/**
 * Defines an abstraction for handling the conversion of files or content from one format to another.
 * The implementation of this interface manages to process incoming conversion requests and generating
 * results, encapsulating the outcome and the converted content, if successful.

 * Method Details:
 * - convert: Accepts a {@link ConversionRequest} object that contains details about the input file
 *   path, desired output path, and target format. Returns a {@link ConversionResult}, which includes
 *   the status of the conversion (success or failure), a message providing additional context about
 *   the result of the conversion, and the converted file content upon a successful operation.

 * This interface is intended to be implemented by classes that perform specific types of conversion
 * logic, such as converting files between supported content formats.
 */
public interface IConverter {
    ConversionResult convert(ConversionRequest request);
}