package oss.transaction.Bll.Enums;

public enum PaymentInstrument {
    ContactPayment(1),
    ContactlessPayment(2),
    MobilePaymet(3);

    public long getValue() {
        return value;
    }

    private final long value;

    PaymentInstrument(final long newValue) {
        value = newValue;
    }
}
