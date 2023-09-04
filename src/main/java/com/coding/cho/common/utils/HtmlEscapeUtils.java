package com.coding.cho.common.utils;

import org.springframework.web.util.HtmlUtils;

public class HtmlEscapeUtils {
	
	public static String escapeHtml(String input) {
		return HtmlUtils.htmlEscape(input);
	}

    public static String unescapeHtml(String input) {
        return HtmlUtils.htmlUnescape(input);
    }

}
