package com.xiaoerzuche.common.util;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssUtil {

    private static final Logger log = LoggerFactory.getLogger(XssUtil.class);
    static private String CSRF_TAG = " \t\n\r\f";

    /**
     * XSS漏洞利用的特征词
     */
    static private final String[] XSS_TAG_STRINGS = new String[]{
        "</script>",
        "<script",
        "src=",
        "eval(",
        "expression(",
        "javascript:",
        "vbscript:",
        "onload=",
        "&#", // &#0000106 , &#106; ...

        "onAbort=",
        "onActivate=",
        "onAfterPrint=",
        "onAfterUpdate=",
        "onBeforeActivate=",
        "onBeforeCopy=",
        "onBeforeCut=",
        "onBeforeDeactivate=",
        "onBeforeEditFocus=",
        "onBeforePaste=",
        "onBeforePrint=",
        "onBeforeUnload=",
        "onBegin=",
        "onBlur=",
        "onBounce=",
        "onCellChange=",
        "onChange=",
        "onClick=",
        "ontextMenu=",
        "ontrolSelect=",
        "onCopy=",
        "onCut=",
        "onDataAvailable=",
        "onDataSetChanged=",
        "onDataSetComplete=",
        "onDblClick=",
        "onDeactivate=",
        "onDrag=",
        "onDragEnd=",
        "onDragLeave=",
        "onDragEnter=",
        "onDragOver=",
        "onDragDrop=",
        "onDrop=",
        "onEnd=",
        "onError=",
        "onErrorUpdate=",
        "onFilterChange=",
        "onFinish=",
        "onFocus=",
        "onFocusIn=",
        "onFocusOut=",
        "onHelp=",
        "onKeyDown=",
        "onKeyPress=",
        "onKeyUp=",
        "onLayoutComplete=",
        "onLoad=",
        "onLoseCapture=",
        "onMediaComplete=",
        "onMediaError=",
        "onMouseDown=",
        "onMouseEnter=",
        "onMouseLeave=",
        "onMouseMove=",
        "onMouseOut=",
        "onMouseOver=",
        "onMouseUp=",
        "onMouseWheel=",
        "onMove=",
        "onMoveEnd=",
        "onMoveStart=",
        "onOutOfSync=",
        "onPaste=",
        "onPause=",
        "onProgress=",
        "onPropertyChange=",
        "onReadyStateChange=",
        "onRepeat=",
        "onReset=",
        "onResize=",
        "onResizeEnd=",
        "onResizeStart=",
        "onResume=",
        "onReverse=",
        "onRowsEnter=",
        "onRowExit=",
        "onRowDelete=",
        "onRowInserted=",
        "onScroll=",
        "onSeek=",
        "onSelect=",
        "onChange=",
        "onSelectStart=",
        "onStart=",
        "onStop=",
        "onSyncRestored=",
        "onSubmit=",
        "onTimeError=",
        "onTrackChange=",
        "onUnload=",
        "onURLFlip=",
        "seekSegmentTime=",};

    /**
     * XSS漏洞辅助特征字符
     */
    static private final String[] XSS_URL_STRINGS = new String[]{
        "%00"
    };

    /**
     * 需要进行
     */
    static private final List<String> STRICT_PARAMS = Arrays.asList(new String[]{
        "callback",
        "jsonpcallback",});

    static private final List<String> XSS_STRINGS = new ArrayList<String>();

    /**
     * 正则表达式
     */
    static private final String regex = "([a-z]|[A-Z]|[0-9]|_)*";

    /**
     * 严格检测是否含XSS攻击
     *
     * @param key
     * @param value
     * @return true可能含，false未检测出XSS
     */
    private static boolean strictXSScheck(String key, String value) {
        if (STRICT_PARAMS.contains(key) == false) {
            return false;
        }

        if (value.matches(regex) == false) {
            return true;
        }
        return false;
    }

    static {
        for (int i = 0; i < XSS_TAG_STRINGS.length; i++) {
            XSS_STRINGS.add(XSS_TAG_STRINGS[i].toLowerCase());
        }

        try {
            for (int i = 0; i < XSS_URL_STRINGS.length; i++) {
                String urlXss = URLDecoder.decode(XSS_URL_STRINGS[i], "UTF-8");
                XSS_STRINGS.add(urlXss);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("XSSUTIL init", e);
        }
    }

    /**
     * 判断字符串是否有XSS攻击风险
     *
     * @param value
     * @return true可能含，false未检测出XSS
     */
    public static boolean hasXSS(String value) {
        if (value == null) {
            return false;
        }

        value = removeWriteSpaceAndLower(value);

        // Remove all sections that match a pattern
        for (String chars : XSS_STRINGS) {
            if (value.indexOf(chars) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查request.getParameterMap() 是否有异常攻击
     *
     * @param parameterMap
     * @return true可能含，false未检测出XSS
     */
    public static boolean hasXSS(Map<String, Object> parameterMap) {
        Set<Map.Entry<String, Object>> entrySet = parameterMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                String[] values = (String[]) value;
                for (int i = 0; i < values.length; i++) {
                    if (hasXSS(values[i])) {
                        log.warn("found xss request:" + key + "=" + values[i]);
                        return true;
                    }
                    if (strictXSScheck(key, values[i])) {
                        log.warn("found xss request:" + key + "=" + values[i]);
                        return true;
                    }
                }
            } else {
                if (hasXSS(String.valueOf(value))) {
                    log.warn("found xss request:" + key + "=" + value);
                    return true;
                }
                if (strictXSScheck(key, String.valueOf(value))) {
                    log.warn("found xss request:" + key + "=" + value);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查request.getParameterMap() 是否有异常攻击
     *
     * @param parameterMap
     * @return true可能含，false未检测出XSS
     */
    public static boolean checkAllXSS(Map<String, String[]> parameterMap) {
        Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String key = (String) entry.getKey();
            String[] values = entry.getValue();
            if (values == null) {
                continue;
            }
            for (int i = 0; i < values.length; i++) {
                if (hasXSS(values[i])) {
                    log.warn("found xss request:" + key + "=" + values[i]);
                    return true;
                }
                if (strictXSScheck(key, values[i])) {
                    log.warn("found xss request:" + key + "=" + values[i]);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 清理字符串的xss
     *
     * @param str 源字符串
     * @return 过滤xss关键字后的字符串
     */
    public static String cleanXSS(String str) {
        String cleanStr = cleanXSSForReturn(str);
        return cleanStr;
    }

    /**
     * 检查并清理字符串中的xss, 清理掉输出中的空格，请注意
     *
     * @param str 源字符串
     * @return 清理后的字符串
     */
    private static String cleanXSSForReturn(String str) {
        if (str == null) {
            return null;
        }

        str = removeWriteSpaceAndLower(str);

        // Remove all sections that match a pattern
        for (String xss : XSS_STRINGS) {
            if (str.indexOf(xss) >= 0) {
                str = str.replaceAll(xss, "");
            }
        }
        return str;
    }

    private static String removeWriteSpaceAndLower(String value) {
        return replaceChars(value, CSRF_TAG, "").toLowerCase();
    }

    private static String replaceChars(String str, String searchChars, String replaceChars) {
        if (StringUtils.isBlank(str) || searchChars == null || searchChars.length() == 0) {
            return str;
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        boolean modified = false;
        int replaceCharsLength = replaceChars.length();
        int strLength = str.length();
        StringBuffer buf = new StringBuffer(strLength);
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int index = searchChars.indexOf(ch);
            if (index >= 0) {
                modified = true;
                if (index < replaceCharsLength) {
                    buf.append(replaceChars.charAt(index));
                }
            } else {
                buf.append(ch);
            }
        }
        if (modified) {
            return buf.toString();
        }
        return str;
    }

    /**
     * Part of HTTP content type header.
     */
    private static final String MULTIPART = "multipart/";

    /**
     * 判断是否文件上传request
     *
     * @param request
     * @return
     */
    public static final boolean isMultipartContent(
        HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        if (contentType.toLowerCase().startsWith(MULTIPART)) {
            return true;
        }
        return false;
    }
}
