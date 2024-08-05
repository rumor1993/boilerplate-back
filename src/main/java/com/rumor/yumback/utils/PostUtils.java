package com.rumor.yumback.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PostUtils {
    public static String extractDescription(String contents, int maxLength) {
        // Parse the HTML
        Document doc = Jsoup.parse(contents);

        // Remove all <img> tags
        for (Element img : doc.select("img")) {
            img.remove();
        }

        // Extract the cleaned text
        String text = doc.text();

        // Trim to the first maxLength characters
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength);
        }

        return text;
    }

    public static String extractImage(String contents) {
        // Parse the HTML
        Document doc = Jsoup.parse(contents);

        // Remove all <img> tags
        for (Element img : doc.select("img")) {
            return img.attribute("src").getValue();
        }

        return "";
    }
}
