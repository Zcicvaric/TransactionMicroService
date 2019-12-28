package oss.transaction.Bll.Enums;

public enum PaymentType {
    Internal(1),
    National(2),
    International(3);

    public long getValue() {
        return value;
    }

    private final long value;

    PaymentType(final long newValue) {
        value = newValue;
    }

}
