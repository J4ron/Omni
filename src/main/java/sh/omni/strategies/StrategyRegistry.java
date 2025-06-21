package sh.omni.strategies;

import java.util.ArrayList;
import java.util.List;

/**
 * The StrategyRegistry class is responsible for managing and providing access to
 * a collection of conversion strategies that implement the {@link IConversionStrategy} interface.

 * This registry allows for the registration of specific conversion strategies and
 * the retrieval of an appropriate strategy based on the source and target formats provided.

 * Key Responsibilities:
 * - Maintain a collection of available conversion strategies.
 * - Provide a mechanism to register new strategies.
 * - Retrieve a strategy capable of handling specific source-to-target format conversions.

 * Thread Safety:
 * This implementation is not thread-safe. If used in a multi-threaded environment,
 * external synchronization is required to ensure thread safety during strategy registration and retrieval.

 * Error Handling:
 * If no suitable strategy is found for a given source and target format, this class
 * throws an {@code UnsupportedOperationException}.

 * Methods:
 * - {@code registerStrategy(IConversionStrategy strategy)}: Registers a new strategy.
 * - {@code findStrategy(String sourceFormat, String targetFormat)}: Finds and retrieves a
 *   strategy capable of handling the provided source and target formats.
 */
public class StrategyRegistry {
    private final List<IConversionStrategy> strategies = new ArrayList<>();

    /**
     * Registers a new conversion strategy to the registry. The strategy must implement
     * the {@link IConversionStrategy} interface and provide the logic for handling specific
     * file format conversions.
     *
     * @param strategy the conversion strategy to be registered, implementing the {@link IConversionStrategy}
     *                 interface. This parameter is expected to define the criteria for supported source
     *                 and target formats, as well as the specific conversion logic.
     */
    public void registerStrategy(IConversionStrategy strategy) {
        strategies.add(strategy);
    }

    /**
     * Finds and retrieves a suitable conversion strategy that supports converting
     * between the specified source format and target format. If no matching strategy
     * is found, an {@link UnsupportedOperationException} is thrown.
     *
     * @param sourceFormat the format of the source file to be converted
     * @param targetFormat the desired target format for the conversion
     * @return an implementation of {@link IConversionStrategy} capable of handling the
     *         specified source and target formats
     * @throws UnsupportedOperationException if no suitable strategy is found for the
     *         given source and target formats
     */
    public IConversionStrategy findStrategy(String sourceFormat, String targetFormat) {
        return strategies.stream()
                .filter(strategy -> strategy.canHandle(sourceFormat, targetFormat))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                        "No Strategy found fore conversion from " +
                                sourceFormat + " to " + targetFormat));
    }
}