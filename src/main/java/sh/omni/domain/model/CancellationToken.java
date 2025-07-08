package sh.omni.domain.model;

/**
 * A simple cancellation token that can be used to interrupt
 * long-running operations like file conversions.
 *
 * Can be polled by worker code and cancelled externally
 * (e.g., via CLI Ctrl+C hook or kill signal).
 */
public class CancellationToken {
    private volatile boolean isCancelled;

    public CancellationToken() {
        this(false);
    }

    public CancellationToken(boolean initialState) {
        this.isCancelled = initialState;
    }

    /**
     * Returns whether cancellation has been requested.
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Requests cancellation of the associated operation.
     */
    public void cancel() {
        this.isCancelled = true;
    }
}
