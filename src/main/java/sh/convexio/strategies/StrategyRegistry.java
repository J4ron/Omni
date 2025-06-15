package sh.convexio.strategies;

import java.util.ArrayList;
import java.util.List;

public class StrategyRegistry {
    private final List<IConversionStrategy> strategies = new ArrayList<>();

    public void registerStrategy(IConversionStrategy strategy) {
        strategies.add(strategy);
    }

    public IConversionStrategy findStrategy(String sourceFormat, String targetFormat) {
        return strategies.stream()
                .filter(strategy -> strategy.canHandle(sourceFormat, targetFormat))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                        "No Strategy found fore conversion from " +
                                sourceFormat + " to " + targetFormat));
    }
}