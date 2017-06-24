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
}
