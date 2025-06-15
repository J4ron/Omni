package sh.convexio.strategies;
import sh.convexio.domain.model.FileContent;

public interface IConversionStrategy {
    boolean canHandle(String sourceFormat, String targetFormat);
    FileContent convert(FileContent source, String targetFormat);
}