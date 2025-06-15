package sh.convexio.domain.ports;

import sh.convexio.domain.model.ConversionRequest;
import sh.convexio.domain.model.ConversionResult;

public interface IConverter {
    ConversionResult convert(ConversionRequest request);
}