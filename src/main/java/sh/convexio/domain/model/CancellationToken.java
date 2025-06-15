package sh.convexio.domain.model;

public class CancellationToken {
    private volatile boolean isCancelled;

    public CancellationToken() {
        this(false);
    }

    public CancellationToken(boolean initialState) {
        this.isCancelled = initialState;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }
}