package sh.omni.domain.model;

public class CancellationToken {
    private boolean isCancelled;

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