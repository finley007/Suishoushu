package com.changyi.fi.util;

public class FIConstants {

    public static final String DEFAULT_CHARSET = "utf-8";

    public enum InvoiceType {
        Enterprise("1"), Person("0");

        private String value;

        private InvoiceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum InvoiceStatus {
        Normal(Integer.valueOf(0).shortValue()), Invalid(Integer.valueOf(1).shortValue());

        private Short value;

        private InvoiceStatus(Short value) { this.value = value; }

        public Short getValue() { return value; }
    }

    public enum EnterpriseField {

        Address("Address"), Phone("Phone"), Name("Name"), Bank("Bank"), BankAcct("BankAcct");

        private String fieldName;

        private EnterpriseField(String fieldName) { this.fieldName = fieldName; }

        public String getName() { return fieldName; }
    }
}
