package util.reflect;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class StringUtil {

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String encodePassword(String password) {
        //sha is system default encode algorithm for user password.
        return encodePassword(password, "sha");
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     *
     * @param password  Password or other credentials to use in authenticating this
     *                  username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {

        byte[] unencodedPassword = password.getBytes();

        MessageDigest md;
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error("Exception: " + e);
            return password;
        }
        md.reset();
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuilder buf = new StringBuilder();

        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(anEncodedPassword & 0xff, 16));
        }

        return buf.toString();
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static Boolean isContaninsChineseCharacter(String str) {
        if (StringUtils.isNotEmpty(str)) {
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    public static String removeLineFeedAndTrim(String str) {
        if (StringUtils.isNotEmpty(str)) {
            // replace &nbsp(ascii)为160; 成正常空格
            str = str.replaceAll(" ", " ");
            str = str.trim();
            str = str.replaceAll("\r\n", "");
            return str;
        }
        return null;
    }

    public static String upperCaseFirstChar(String src) {
        if (StringUtils.isBlank(src)) return src;
        char[] array = src.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    /**
     * parse string to long
     *
     * @param src
     * @return
     */
    public static Long parseStringToLong(String src) {
        Long desc = null;
        if (!StringUtils.isBlank(src)) {
            try {
                desc = Long.parseLong(src.trim());
            } catch (NumberFormatException e) {
            }
        }
        return desc;
    }

    /**
     * parse string to long
     *
     * @param src
     * @return
     */
    public static Float parseStringToFloat(String src) {
        Float desc = null;
        if (!StringUtils.isBlank(src)) {
            try {
                desc = Float.parseFloat(src);
            } catch (NumberFormatException e) {
            }
        }
        return desc;
    }

    /**
     * parse string to Double
     *
     * @return
     */
    public static Double parseStringToDouble(String src) {
        Double desc = null;
        if (!StringUtils.isBlank(src)) {
            try {
                desc = Double.parseDouble(src);
            } catch (NumberFormatException e) {
            }
        }
        return desc;
    }


    /**
     * parse string to int
     *
     * @param src src string
     * @return
     */
    public static Integer parseStringToInteger(String src) {
        Integer desc = null;
        if (!StringUtils.isBlank(src)) {
            try {
                desc = Integer.parseInt(src);
            } catch (NumberFormatException e) {
            }
        }
        return desc;
    }

    /**
     * Trim leading and trailing whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        return a == b || (a != null && a.equalsIgnoreCase(b));
    }

    public static boolean notEqualForString(String s1, String s2) {
        return StringUtils.isNotBlank(s1) ? !s1.equals(s2) : StringUtils.isNotBlank(s2);
    }

    public static String[] split(String src, char separatorChar) {
        return (src == null ? null : StringUtils.split(src, separatorChar));
    }


    /**
     * Convert a comma separated string to list of long
     * Similar function like #convertStringToLongList, faster and only supports comma
     *
     * @param source
     * @return
     */
    public static List<Long> convertStringToLongList(String source) {
        if (StringUtils.isNotBlank(source)) {
            List<Long> result = Lists.newArrayList();
            for (int i = 0, n = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (',' == c) {
                    result.add((long) n);
                } else {
                    if (' ' != c) {
                        n = n * 10 + (c - '0');
                    }
                }
            }
            return result;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public static String join(Object obj, char c) {
        return join(obj, String.valueOf(c));
    }


    public static String join(Object obj, CharSequence c) {
        if (obj == null)
            return null;
        StringBuilder ret = new StringBuilder();
        if (obj.getClass().isArray()) {
            Object[] array = (Object[]) obj;
            for (int i = 0; i < array.length; i++) {
                Object o = array[i];
                String s = (o == null ? "" : ObjectUtils.toString(o));
                ret.append(s);
                if (i != array.length - 1) {
                    ret.append(c);
                }
            }
        } else if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            if (!CollectionUtils.isEmpty(collection)) {
                for (Iterator iterator = collection.iterator(); ; ) {
                    Object o = iterator.next();
                    String s = (o == null ? "" : ObjectUtils.toString(o));
                    ret.append(s);
                    if (iterator.hasNext()) {
                        ret.append(c);
                    } else {
                        break;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("StringUtils join method only support array or Collection parameter");
        }
        return ret.toString();
    }


    public static boolean isSpecialChar(char c) {
        boolean ret = false;
        int i = (int) c;
        // 0 9 10 13 160
        if (Character.isWhitespace(c) || c == '\n' || c == '\t' || c == '\r' || i == 160) {
            ret = true;
        }
        return ret;
    }


    /**
     * 功能:清除字符串中所有的特殊字符<br/>
     * <ul>
     * <li>特殊字符由isSpecialChar方法决定</li>
     * <li>目前{空格,'\n','\t','\r'}是特殊字符</li>
     * </ul>
     */
    public static String trimAll(String s) {
        if (s == null) {
            return null;
        }
        if (StringUtils.isBlank(s)) {
            return "";
        }
        //special
        StringBuilder sb = new StringBuilder(s);
        int index = 0;
        while (sb.length() > index) {
            if (isSpecialChar(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * Feature: clean all the space and separator(-, _) in string
     * 删除掉字符串中所有的空格，中划线，下划线
     * 注：不检测中文空格等，空格有4种字符
     *
     * @param str
     * @return
     */
    public static String trimAllSpaceAndSeperator(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        char c;
        while (sb.length() > index) {
            c = sb.charAt(index);
            if (Character.isWhitespace(c)
                    || c == '-' || c == '_'
                    ) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * list to string ，speparate： 为分隔符
     *
     * @param source
     * @param speparate
     * @return
     */
    public static StringBuilder listToStringBuilder(List<String> source, String speparate) {
        if (CollectionUtils.isNotEmpty(source)) {
            StringBuilder bu = new StringBuilder();
            for (int i = 0; i < source.size(); i++) {
                if (i == 0) {
                    bu.append(source.get(i));
                } else {
                    bu.append(speparate).append(source.get(i));
                }
            }
            return bu;
        }
        return new StringBuilder();
    }

    public static boolean isBlank(String key, String property, String value, StringBuilder message) {
        if (StringUtils.isBlank(value)) {
            message.append(key).append(property).append(": is null or empty ").append(System.lineSeparator());
            return true;
        }
        return false;
    }


    public static String toCSVString(Object data) {
        String p = "\"";
        if (null == data) {
            return "";
        }
        data = p + data + p;
        return data.toString();
    }

    /**
     * <pre>delete Html tags</pre>
     * <pre>1:start with"<"，end with">"  </pre>
     * <pre>2：start with"&"，end with";" </pre>
     *
     * @param inputString
     * @return String
     */
    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_html;
        Matcher m_html;
        Pattern p_special;
        Matcher m_special;
        try {
            String regEx_html = "<[^>]*>";
            // 定义一些特殊字符的正则表达式 如：&nbsp;&gt;&alt;
            String regEx_special = "\\&[a-zA-Z]{1,10};";

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
            m_special = p_special.matcher(htmlStr);
            htmlStr = m_special.replaceAll(""); // 过滤特殊标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }


    /**
     * 1 允许出现的字符  0-9 + - 空格 ()
     * 2 如果前括号出现，就要有匹配的后括号。且保证()的出现顺序
     * 3 允许为null 或者为空
     */
    public static Boolean isPhoneNumber(String number) {
        //不允许空格
//        String regex = "^((\\([^\\(\\)]+?\\))*|[\\d\\+\\-]*)+$";
        //允许空格
        if (StringUtils.isBlank(number))
            return true;
        else {
            String regex = "^(\\([^()]+\\)|[-+\\d ])+$";
            return Pattern.matches(regex, number);
        }
    }

    /**
     * 1 允许出现的字符  0-9 + - 空格 ()
     * 2 如果前括号出现，就要有匹配的后括号。且保证()的出现顺序
     */
    public static Boolean isNotPhoneNumber(String number) {

        return !isPhoneNumber(number);
    }

    /**
     * 移除字符串中的乱码
     *
     * @param s String
     * @return 处理后的String
     */
    public static String trimMessyCode(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(s);
        int index = 0;
        while (sb.length() > index) {
            char c = sb.charAt(index);
            int ascii = (int) c;
            if (ascii >= 160) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * If first string is empty, then return second as default value
     *
     * @param first
     * @param second
     * @return
     */
    public static String ifEmptyDefault(String first, String second) {
        return StringUtils.isNotBlank(first) ? first : checkNotNull(second);
    }

    /**
     * <pre>source 为null时 返回null</pre>
     * <pre>反之，转为大写</pre>
     *
     * @param source 原字符串
     * @return 处理后字符串
     */
    public static String toUpperCase(String source) {
        if (null == source) {
            return null;
        } else {
            return source.toUpperCase();
        }

    }

}
