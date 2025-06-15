package sh.convexio.domain.model;

public record ConversionRequest(
        String inputPath,
        String outputPath,
        String targetFormat
) {}