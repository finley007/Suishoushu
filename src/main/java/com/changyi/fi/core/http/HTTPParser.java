package com.changyi.fi.core.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by finley on 7/8/17.
 */
public class HTTPParser {

    private Document doc;

    private ResultHandler handler;

    public HTTPParser setHandler(ResultHandler handler) {
        this.handler = handler;
        return this;
    }

    public HTTPParser(String html) {
        doc = Jsoup.parse(html);
    }

    public <T> T select(String selector) {
        if (this.handler != null) {
            return (T)this.handler.handleResult(doc.select(selector));
        } else {
            return (T)doc.select(selector);
        }
    }

    public static interface ResultHandler<T> {
        public T handleResult(Elements elems);
    }
}
