package sh.omni.domain.services;

import sh.omni.domain.model.*;
import sh.omni.domain.ports.IConverter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class BatchConversionService {

    private final IConverter converter;

    public BatchConversionService(IConverter converter) {
        this.converter = converter;
    }

    public BatchResult convertBatch(Path inputFolder, Path outputFolder, String targetFormat,
                                    CancellationToken cancellationToken, ProgressListener progressListener) {
        try (Stream<Path> files = Files.list(inputFolder)) {
            List<Path> fileList = files.filter(Files::isRegularFile).collect(Collectors.toList());
            int total = fileList.size();
            int count = 0;

            List<ConversionResult> results = new ArrayList<>();
            List<String> messages = new ArrayList<>();

            for (Path file : fileList) {
                if (cancellationToken != null && cancellationToken.isCancelled()) {
                    messages.add("Batch conversion cancelled after " + count + " files.");
                    break;
                }

                String outputFileName = file.getFileName().toString().replaceAll("\\.[^.]+$", "") + "." + targetFormat.toLowerCase();
                Path outputFile = outputFolder.resolve(outputFileName);

                ConversionRequest request = new ConversionRequest(
                        file.toString(),
                        outputFile.toString(),
                        targetFormat.toLowerCase(),
                        false,
                        null,
                        null,
                        null,
                        false,
                        false
                );

                ConversionResult result = converter.convert(request);
                results.add(result);

                if (result.success()) {
                    messages.add("Converted: " + file.getFileName());
                } else {
                    messages.add("Failed: " + file.getFileName() + " - " + result.message());
                }

                count++;
                if (progressListener != null) {
                    progressListener.onProgress((count * 100.0) / total);
                }
            }

            boolean allSuccess = results.stream().allMatch(ConversionResult::success);
            return new BatchResult(allSuccess, messages);

        } catch (IOException e) {
            throw new RuntimeException("Batch conversion failed: " + e.getMessage(), e);
        }
    }
}
