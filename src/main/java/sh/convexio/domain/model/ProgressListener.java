package sh.convexio.domain.model;

@FunctionalInterface
public interface ProgressListener {
    void onProgress(double progress);
}