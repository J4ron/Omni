package sh.omni.domain.services;

import sh.omni.domain.model.ConversionRequest;
import sh.omni.domain.model.ConversionResult;
import sh.omni.domain.model.FileContent;
import sh.omni.domain.ports.IConverter;
import sh.omni.domain.ports.IFileSystem;
import sh.omni.strategies.IConversionStrategy;
import sh.omni.strategies.StrategyRegistry;

public class ConverterService implements IConverter {

    private final StrategyRegistry strategyRegistry;
    private final IFileSystem fileSystem;

    public ConverterService(StrategyRegistry strategyRegistry, IFileSystem fileSystem) {
        if (strategyRegistry == null) throw new IllegalArgumentException("StrategyRegistry darf nicht null sein");
        if (fileSystem == null) throw new IllegalArgumentException("IFileSystem darf nicht null sein");
        this.strategyRegistry = strategyRegistry;
        this.fileSystem = fileSystem;
    }

    @Override
    public ConversionResult convert(ConversionRequest request) {
        if (request == null) {
            return ConversionResult.error("ConversionRequest darf nicht null sein");
        }

        try {
            // 1. Datei lesen
            FileContent inputContent = fileSystem.readFile(request.inputPath());
            if (inputContent == null) {
                return ConversionResult.error("Input-Datei konnte nicht gelesen werden: " + request.inputPath());
            }

            // 2. Strategie holen
            IConversionStrategy strategy = strategyRegistry.findStrategy(inputContent.format(), request.targetFormat());
            if (strategy == null) {
                return ConversionResult.error("Keine passende Konvertierungsstrategie gefunden für "
                        + inputContent.format() + " -> " + request.targetFormat());
            }

            // 3. Konvertierung durchführen
            FileContent convertedContent = strategy.convert(inputContent, request.targetFormat());
            if (convertedContent == null) {
                return ConversionResult.error("Konvertierung ergab kein Ergebnis");
            }

            // 4. Ergebnis speichern
            fileSystem.writeFile(request.outputPath(), convertedContent);

            // 5. Erfolg zurückgeben
            return ConversionResult.success(convertedContent);

        } catch (Exception e) {
            return ConversionResult.error("Fehler während der Konvertierung: " + e.getMessage());
        }
    }
}
