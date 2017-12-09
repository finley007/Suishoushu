package com.changyi.fi.util;

import java.text.SimpleDateFormat;

public class FIConstants {

    public static final String BEAN_TIANYANCHA_API_SERVICE = "tianYanChaAPIService";
    public static final String BEAN_QIXINBAO_API_SERVICE = "qiXinBaoAPIService";
    public static final String BEAN_QICHACHA_API_SERVICE = "qiChaChaAPIService";

    public static final String API_TIANYANCHA = "tyc";
    public static final String API_QIXINBAO = "qxb";
    public static final String API_QICHACHA = "qcc";

    public static final String IMAGE_TYPE_GIF = "gif";
    public static final String IMAGE_TYPE_PNG = "png";
    public static final String IMAGE_TYPE_JPG = "jpg";

    public static final String DEFAULT_CHARSET = "utf-8";

    public static final String SYSTEM = "system";

    public static final String OK = "ok";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_PATTERN1 = "\\d{4}年\\d{1,2}月\\d{1,2}日";
    public static final String NUMBER_PATTERN = "\\d+\\.?\\d+";

    public static final String FIELD_CREDIT_CODE = "creditCode";
    public static final String FIELD_NAME = "name";

    public enum InvoiceType {
        EnterpriseSpecial("2"), EnterpriseNormal("1"), Person("0");

        private String value;

        private InvoiceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Short getShortValue() {return Short.valueOf(value); }
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

        public Short getShortValue() { return Short.valueOf(this.value); }
    }

    public enum NotifyMethod {
        Email, SMS
    }

    public enum EncryptorAlgorithm {
        MD5("MD5"), SHA1("SHA");

        private String value;

        private EncryptorAlgorithm(String value) { this.value = value; }

        public String getValue() { return value; }
    }

}
