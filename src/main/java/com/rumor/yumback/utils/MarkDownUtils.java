package com.rumor.yumback.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;

public class MarkDownUtils {

    public static String clean(String markdown) {
        String html = toHtml(markdown);
        return Jsoup.parse(html).text();
    }

    private static String toHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
