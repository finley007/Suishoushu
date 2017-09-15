package com.changyi.fi.core.maintain;

import com.changyi.fi.core.notification.EmailNotifier;
import com.changyi.fi.core.notification.INotifier;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.util.FIConstants;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MaintainManager {

    private static final String MAINTAINER_PROP = "maintain.email.list";

    private static final String CRITICAL_ERROR_TITLE = "Suishoushu CRITICAL system error";

    private static String maintainer;

    private static String getMaintainer() {
        if (maintainer == null) {
            maintainer = Properties.get(MAINTAINER_PROP);
        }
        return maintainer;
    }

    private static String printException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void criticalErrorNotify(FIConstants.NotifyMethod method, String errorDetail) throws Exception {
        switch (method) {
            case Email: {
                INotifier notifier = new EmailNotifier(getMaintainer());
                notifier.Notify(CRITICAL_ERROR_TITLE, errorDetail);
            }
            case SMS: {

            }
        }
    }

    public static void criticalErrorNotify(FIConstants.NotifyMethod method, Exception e) throws Exception {
        String exceptionContent = printException(e);
        criticalErrorNotify(method, exceptionContent);
    }
}
