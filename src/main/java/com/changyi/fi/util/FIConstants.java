package com.changyi.fi.util;

public class FIConstants {

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

    public enum EnterpriseField {

        Address("Address"), Phone("Phone"), Name("Name"), Bank("Bank"), BankAcct("BankAcct");

        private String fieldName;

        private EnterpriseField(String fieldName) { this.fieldName = fieldName; }

        public String getName() { return fieldName; }
    }
}
