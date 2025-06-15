package sh.convexio.domain.model;

public record FileContent(
        String filename,
        byte[] content,
        String format
) {}