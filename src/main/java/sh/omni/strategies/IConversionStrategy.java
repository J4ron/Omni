package sh.omni.strategies;

import sh.omni.domain.model.ConversionRequest;
import sh.omni.domain.model.FileContent;

public interface IConversionStrategy {
    boolean canHandle(String sourceFormat, String targetFormat);

    FileContent convert(FileContent source, String targetFormat);

    FileContent convert(ConversionRequest request);
}
