package oss.transaction.Bll.Enums;

public enum EStatus {
    Finalized(1),
    Canceled(2);

    public long getValue() {
        return value;
    }

    private final long value;

    EStatus(final long newValue) {
        value = newValue;
    }
}
