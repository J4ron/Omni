package sh.convexio.domain.services;

import sh.convexio.domain.model.ConversionResult;
import sh.convexio.domain.model.FileContent;
import sh.convexio.strategies.IConversionStrategy;
import sh.convexio.strategies.StrategyRegistry;

public class ConverterService {
    private final StrategyRegistry strategyRegistry;

    public ConverterService(StrategyRegistry strategyRegistry) {
        if (strategyRegistry == null) {
            throw new IllegalArgumentException("StrategyRegistry darf nicht null sein");
        }
        this.strategyRegistry = strategyRegistry;
    }

    public ConversionResult convert(FileContent input, String targetFormat) {
        if (input == null || targetFormat == null) {
            return ConversionResult.error("Input oder targetFormat darf nicht null sein");
        }

        try {
            IConversionStrategy strategy = strategyRegistry.findStrategy(input.format(), targetFormat);
            FileContent converted = strategy.convert(input, targetFormat);
            return converted != null ? ConversionResult.success(converted) 
                                   : ConversionResult.error("Konvertierung ergab null-Ergebnis");
        } catch (Exception e) {
            return ConversionResult.error("Konvertierung fehlgeschlagen: " + e.getMessage());
        }
    }
}