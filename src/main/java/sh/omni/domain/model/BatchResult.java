package sh.omni.domain.model;

import java.util.List;

public class BatchResult {
    private final boolean success;
    private final List<String> messages;

    public BatchResult(boolean success, List<String> messages) {
        this.success = success;
        this.messages = messages;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getMessages() {
        return messages;
    }
}
