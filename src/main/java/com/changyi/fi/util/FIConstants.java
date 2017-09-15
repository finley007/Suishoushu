package com.changyi.fi.util;

import java.text.SimpleDateFormat;

public class FIConstants {

    public static final String DEFAULT_CHARSET = "utf-8";

    public static final String SYSTEM = "system";

    public static final String OK = "ok";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_PATTERN1 = "\\d{4}年\\d{2}月\\d{2}日";
    public static final String NUMBER_PATTERN = "\\d+\\.?\\d+";

    public enum InvoiceType {
        EnterpriseSpecial("2"), EnterpriseNormal("1"), Person("0");

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

    public enum Gendar {
        Male(Integer.valueOf(0).shortValue()), Female(Integer.valueOf(1).shortValue()), Unknow(Integer.valueOf(2).shortValue());

        private Short value;

        private Gendar(Short value) { this.value = value; }

        public Short getValue() { return value; }
    }

    public enum EnterpriseField {
        Address("Address"), Phone("Phone"), Name("Name"), Bank("Bank"), BankAcct("BankAcct");

        private String fieldName;

        private EnterpriseField(String fieldName) { this.fieldName = fieldName; }

        public String getName() { return fieldName; }
    }

    public enum IsDefault {
        True("0"), False("1");

        private String value;

        private IsDefault(String value) { this.value = value; }

        public String getValue() { return value; }
    }

    public enum NotifyMethod {
        Email, SMS
    }
}
