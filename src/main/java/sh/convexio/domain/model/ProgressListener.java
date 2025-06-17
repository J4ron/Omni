package sh.convexio.domain.model;

/**
 * Functional interface representing a listener to monitor progress updates.

 * This interface is typically used in asynchronous or long-running operations
 * to report progress via a callback. The progress value is provided as a
 * percentage ranging from 0.0 to 100.0. Implementations can define specific
 * actions to take based on the reported progress, such as updating a user
 * interface or logging messages.
 */
@FunctionalInterface
public interface ProgressListener {
    void onProgress(double progress);
}