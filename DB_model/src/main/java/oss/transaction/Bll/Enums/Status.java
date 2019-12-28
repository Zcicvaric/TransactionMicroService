package oss.transaction.Bll.Enums;

public enum Status {
    Finalized(1),
    Canceled(2);

    public long getValue() {
        return value;
    }

    private final long value;

    Status(final long newValue) {
        value = newValue;
    }
}
