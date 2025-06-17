package sh.convexio.domain.services;

import sh.convexio.domain.model.ConversionResult;
import sh.convexio.domain.model.FileContent;
import sh.convexio.strategies.IConversionStrategy;
import sh.convexio.strategies.StrategyRegistry;

/**
 * Provides conversion services for transforming file content from one format to another
 * using an appropriate strategy selected from the {@link StrategyRegistry}.

 * Responsibilities:
 * - Accept input content and a target format for conversion.
 * - Validate input parameters.
 * - Use a registered conversion strategy to perform*/
public class ConverterService {
    private final StrategyRegistry strategyRegistry;

    /**
     * Constructs a new instance of the ConverterService with the provided strategy registry.
     * The strategy registry is used to manage and retrieve conversion strategies for transforming
     * file content between different formats.
     *
     * @param strategyRegistry the registry containing available conversion strategies. Must not be null,
     *                         as it is essential for finding and executing appropriate strategies.
     * @throws IllegalArgumentException if the provided strategyRegistry is null.
     */
    public ConverterService(StrategyRegistry strategyRegistry) {
        if (strategyRegistry == null) {
            throw new IllegalArgumentException("StrategyRegistry darf nicht null sein");
        }
        this.strategyRegistry = strategyRegistry;
    }

    /**
     * Converts the given content from its current format to a specified target format.
     * This method uses a registered conversion strategy to handle the transformation,
     * ensuring that the correct conversion logic is applied based on the formats provided.
     *
     * @param input the content to be converted, encapsulating the file data, name, and current format.
     *              Must not be null.
     * @param targetFormat the desired target format for the conversion. Must not be null.
     * @return a {@link ConversionResult} containing information on whether the conversion
     *         was successful, any associated messages, and the converted content (if successful).
     *         If the conversion fails due to unsupported formats or other issues, the result
     *         will contain an error message.
     */
    public ConversionResult convert(FileContent input, String targetFormat) {
        if (input == null || targetFormat == null) {
            return ConversionResult.error("Input or targetFormat must not be null");
        }

        try {
            IConversionStrategy strategy = strategyRegistry.findStrategy(input.format(), targetFormat);
            FileContent converted = strategy.convert(input, targetFormat);
            return converted != null ? ConversionResult.success(converted)
                    : ConversionResult.error("Conversion resulted in null output");
        } catch (Exception e) {
            return ConversionResult.error("Conversion failed: " + e.getMessage());
        }
    }
}