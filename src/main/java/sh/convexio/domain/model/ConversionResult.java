package sh.convexio.domain.model;

public record ConversionResult(
        boolean success,
        String message,
        FileContent convertedContent
) {
    public static ConversionResult success(FileContent content) {
        return new ConversionResult(true, "Conversion successful", content);
    }

    public static ConversionResult error(String errorMessage) {
        return new ConversionResult(false, errorMessage, null);
    }
}