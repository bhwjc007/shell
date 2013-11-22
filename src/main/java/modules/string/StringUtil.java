package shell.framework.util;

import java.io.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.math.BigDecimal;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static Logger log = Logger.getLogger(StringUtil.class);

    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
    private static final char AMP_ENCODE[] = "&amp;".toCharArray();
    private static final char LT_ENCODE[] = "&lt;".toCharArray();
    private static final char GT_ENCODE[] = "&gt;".toCharArray();
    private static MessageDigest digest = null;
    private static final int fillchar = 61;
    private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static Random randGen = new Random();
    private static char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char zeroArray[] = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();

    static private StringUtil instance;

    public StringUtil() {}


    /**
     * 创建单实例，同步实现
     *
     * @return  字符串工具单实例
     */
    public static synchronized StringUtil getInstance() {
        if (instance == null) {
            instance = new StringUtil();
        }
        return instance;
    }


    /**
     * 分割字符串，按照指定的分割字符串 源字符串:source 分割字符:sign
     *
     * @param source  源字符串
     * @param sign    分割符
     * @return  枚举类型
     */
    public static Enumeration split(String source, String sign) {
        Vector ar = new Vector();
        int start = 0;
        String st = source;
        while (true) {
            int ipos = st.indexOf(sign);
            if (ipos < 0) {
                ar.addElement(st.substring(0));
                break;
            } else {
                ar.addElement(st.substring(0, ipos));
                st = st.substring(ipos + 1);
            }
        }
        return ar.elements();
    }


    /**
     * if the string be in source string
     *
     * @param source source string
     * @param desc   the string
     * @return       boolean
     */
    public static boolean inStr(String source, String desc) {
        if(source==null || source.trim().length()==0 || desc==null || desc.trim().length()==0){
          return false;
        }
        if (source.indexOf(desc) >= 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * building the new string by decoding the specified string using charset of 'GB2312'
     *
     * @param source    specified string
     * @param charset   supported charset
     * @return          the new string
     */
    public static String decodeGB(String source, String charset) {
        if(source==null || charset==null){
            return null;
        }
        try {
            return (new String(source.getBytes(charset), "GB2312"));
        } catch (Exception E) {
            return source;
        }
    }


    /**
     *
     *
     * @param source
     * @param charset
     * @return
     */
    public static String encodeGB(String source, String charset) {
        try {
            return (new String(source.getBytes("GB2312"), charset));
        } catch (Exception e) {
            return source;
        }
    }


    /**
     * building the new string by decoding the specified string using charset of "GBK"
     *
     * @param source    specified string
     * @return          the new string
     */
    public static String decodeGB(String source) {
        if(source==null){
            return source;
        }
        try {
            return new String(source.getBytes("ISO8859-1"), "GBK");
        } catch (Exception E) {
            return source;
        }
    }


    public static String encodeGB(String source) {
        try {
            return (new String(source.getBytes("GBK"), "ISO8859-1"));
        } catch (Exception E) {
            return source;
        }
    }


    /**
     * replace char str1 by using string str2 in the source string
     *
     * @param source  string
     * @param str1    be replaced char
     * @param str2    use string
     * @return        string be replaced
     */
    public static String replace(String source, char str1, String str2) {
        if (source == null) {
            return source;
        }
        String desc = "";
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == str1) {
                desc = desc + str2;
            } else {
                desc = desc + String.valueOf(source.charAt(i));
            }
        }
        return desc;
    }


    /**
     * replace string str1 by using string str2 in the source string
     *
     * @param source   string
     * @param str1     be replaced string
     * @param str2     use string
     * @return         string be replaced
     */
    public static String replace(String source, String str1, String str2) {
        if (source == null) {
            return source;
        }
        String desc = "";
        int i = 0;
        while (i < source.length()) {
            if (source.startsWith(str1, i)) {
                desc = desc + str2;
                i = i + str1.length();
            } else {
                desc = desc + String.valueOf(source.charAt(i));
                i++;
            }
        }
        return desc;
    }


    /**
     * if the string is null or ""
     *
     * @param s  string
     * @return   boolean
     */
    public static boolean isNullStr(String s) {
        return s == null || s.trim().length() <= 0;
    }


    /**
     * trim space character on both sides of the object.tostring
     *
     * @param inObj  object
     */
    public static String killNull(Object inObj) {
        if (inObj == null) {
            return "";
        }
        return inObj.toString().trim();
    }


    /**
     * trim space character on both sides of the object.tostring
     *
     * @param inObj   sourceObject
     * @param toStr   string
     *
     * @return string  if inObj is null,the default string - 'toStr'
     */
    public static String killNull(Object inObj, String toStr) {
        if (inObj == null) {
            return toStr;
        }
        return inObj.toString().trim();
    }


    /**
     * ���ַ�ı����GB2312ת��ΪISO8859.
     *
     * @param gStr �����GB2312�����ַ�.
     * @return ת�����ISO8859�����ַ�.
     */
    public static String gbToIso(String gStr) {
        try {
            if (gStr == null)
                return null;
            return (new String(gStr.getBytes("GB2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            return gStr;
        }
    }

    /**
     * ���ַ�ı����ISO8859ת��ΪGB2312.
     *
     * @param iStr �����ISO8859�����ַ�.
     * @return ת�����GB2312�����ַ�.
     */
    public static String isoToGb(String iStr) {
        try {
            if (iStr == null)
                return null;
            return (new String(iStr.getBytes("ISO8859-1"), "GB2312"));
        } catch (UnsupportedEncodingException e) {
            return iStr;
        }
    }

    /**
     * �˷�����ɣ���һ���ַ���ĳ�ַָ��ֳ��ַ�����
     *
     * @param sourceString :Դ�ַ�
     * @param strInterval  :���
     */
    public static Vector stringToVecor(String sourceString, String strInterval) {
        Vector returnStr = new Vector();
        if (sourceString == "" || strInterval == "") {
            returnStr.addElement(sourceString);
            return returnStr;
        }
        int found_str = sourceString.indexOf(strInterval);
        while (found_str >= 0) {
            returnStr.addElement(sourceString.substring(0, found_str));
            sourceString = sourceString.substring(found_str + strInterval.length());
            found_str = sourceString.indexOf(strInterval);
        }
        if (found_str < 0)
            returnStr.addElement(sourceString);
        return returnStr;
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password  Password or other credentials to use in authenticating
     *                  this username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

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

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if (((int) encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }


    /**
     * Encode a string using Base64 encoding. Used when storing passwords
     * as cookies.
     * <p/>
     * This is weak encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     *
     * @param str
     * @return String
     */
    public static String encodeString(String str) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return new String(encoder.encodeBuffer(str.getBytes())).trim();
    }


    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try {
            return new String(dec.decodeBuffer(str));
        } catch (IOException io) {
            throw new RuntimeException(io.getMessage(), io.getCause());
        }
    }


    /**
     * replace string by ignore case.
     * @param line       string
     * @param oldString  the old string
     * @param newString  the new string
     * @return           string
     */
    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null)
            return null;
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else {
            return line;
        }
    }


    /**
     * replace the string by ignore case. and record replace count by count[].
     * @param line       string
     * @param oldString  old string
     * @param newString  new string
     * @param count      replace count
     * @return           the replaced string
     */
    public static final String replaceIgnoreCase(String line, String oldString, String newString, int count[]) {
        if (line == null)
            return null;
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }


    public static final String replace(String line, String oldString, String newString, int count[]) {
        if (line == null)
            return null;
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }


    /**
     * strip the string of '<br>'
     * @param in   the string
     * @return     the string excluding '<br>'
     */
    public static final String stripTags(String in) {
        if (in == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i + 3 < len && input[i + 1] == 'b' && input[i + 2] == 'r' && input[i + 3] == '>') {
                        i += 3;
                    } else {
                        if (i > last)
                            out.append(input, last, i - last);
                        last = i + 1;
                    }
                } else if (ch == '>')
                    last = i + 1;
        }

        if (last == 0)
            return in;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }


    /**
     * escape the html tags. like '<...>'
     * @param in
     * @return
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '>') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(GT_ENCODE);
                }
        }

        if (last == 0)
            return in;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }


    /**
     * generate a hash value of the specific string
     * @param data
     * @return
     */
    public static final synchronized String hash(String data) {
        try {
            if (digest == null)
                try {
                    digest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
                }
            digest.update(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return encodeHex(digest.digest());
    }



    public static final String encodeHex(byte bytes[]) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }

        return buf.toString();
    }

    public static final byte[] decodeHex(String hex) {
        char chars[] = hex.toCharArray();
        byte bytes[] = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2) {
            int newByte = 0;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = (byte) newByte;
            byteCount++;
        }

        return bytes;
    }

    private static final byte hexCharToByte(char ch) {
        switch (ch) {
            case 48: // '0'
                return 0;

            case 49: // '1'
                return 1;

            case 50: // '2'
                return 2;

            case 51: // '3'
                return 3;

            case 52: // '4'
                return 4;

            case 53: // '5'
                return 5;

            case 54: // '6'
                return 6;

            case 55: // '7'
                return 7;

            case 56: // '8'
                return 8;

            case 57: // '9'
                return 9;

            case 97: // 'a'
                return 10;

            case 98: // 'b'
                return 11;

            case 99: // 'c'
                return 12;

            case 100: // 'd'
                return 13;

            case 101: // 'e'
                return 14;

            case 102: // 'f'
                return 15;

            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 65: // 'A'
            case 66: // 'B'
            case 67: // 'C'
            case 68: // 'D'
            case 69: // 'E'
            case 70: // 'F'
            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                return 0;
        }
    }

    public static String encodeBase64(String data) {
        byte bytes[] = (byte[]) null;
        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return encodeBase64(bytes);
    }

    public static String encodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
        for (int i = 0; i < len; i++) {
            int c = data[i] >> 2 & 0x3f;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 0x3f;
            if (++i < len)
                c |= data[i] >> 4 & 0xf;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if (i < len) {
                c = data[i] << 2 & 0x3f;
                if (++i < len)
                    c |= data[i] >> 6 & 0x3;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                i++;
                ret.append('=');
            }
            if (i < len) {
                c = data[i] & 0x3f;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                ret.append('=');
            }
        }

        return ret.toString();
    }

    public static String decodeBase64(String data) {
        byte bytes[] = (byte[]) null;
        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
        }
        return decodeBase64(bytes);
    }

    public static String decodeBase64(byte data[]) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for (int i = 0; i < len; i++) {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            i++;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 0x3;
            ret.append((char) c);
            if (++i < len) {
                c = data[i];
                if (61 == c)
                    break;
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c);
                c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
                ret.append((char) c1);
            }
            if (++i >= len)
                continue;
            c1 = data[i];
            if (61 == c1)
                break;
            c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c1);
            c = c << 6 & 0xc0 | c1;
            ret.append((char) c);
        }

        return ret.toString();
    }

    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0)
            return new String[0];
        ArrayList wordList = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        int start = 0;
        for (int end = boundary.next(); end != -1; end = boundary.next()) {
            String tmp = text.substring(start, end).trim();
            tmp = replace(tmp, "+", "");
            tmp = replace(tmp, "/", "");
            tmp = replace(tmp, "\\", "");
            tmp = replace(tmp, "#", "");
            tmp = replace(tmp, "*", "");
            tmp = replace(tmp, ")", "");
            tmp = replace(tmp, "(", "");
            tmp = replace(tmp, "&", "");
            if (tmp.length() > 0)
                wordList.add(tmp);
            start = end;
        }

        return (String[]) wordList.toArray(new String[wordList.size()]);
    }


    /**
     * generate the random string to specific length
     * @param length   specific string length
     * @return         random string
     */
    public static final String randomString(int length) {
        if (length < 1)
            return null;
        char randBuffer[] = new char[length];
        for (int i = 0; i < randBuffer.length; i++)
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];

        return new String(randBuffer);
    }

    public static final String chopAtWord(String string, int length) {
        if (string == null || string.length() == 0)
            return string;
        char charArray[] = string.toCharArray();
        int sLength = string.length();
        if (length < sLength)
            sLength = length;
        for (int i = 0; i < sLength - 1; i++) {
            if (charArray[i] == '\r' && charArray[i + 1] == '\n')
                return string.substring(0, i + 1);
            if (charArray[i] == '\n')
                return string.substring(0, i);
        }

        if (charArray[sLength - 1] == '\n')
            return string.substring(0, sLength - 1);
        if (string.length() <= length)
            return string;
        for (int i = length - 1; i > 0; i--)
            if (charArray[i] == ' ')
                return string.substring(0, i).trim();

        return string.substring(0, length);
    }

    public static String chopAtWordsAround(String input, String wordList[], int numChars) {
        if (input == null || "".equals(input.trim()) || wordList == null || wordList.length == 0 || numChars == 0)
            return null;
        String lc = input.toLowerCase();
        for (int i = 0; i < wordList.length; i++) {
            int pos = lc.indexOf(wordList[i]);
            if (pos > -1) {
                int beginIdx = pos - numChars;
                if (beginIdx < 0)
                    beginIdx = 0;
                int endIdx = pos + numChars;
                if (endIdx > input.length() - 1)
                    endIdx = input.length() - 1;
                char chars[];
                for (chars = input.toCharArray(); beginIdx > 0 && chars[beginIdx] != ' ' && chars[beginIdx] != '\n' && chars[beginIdx] != '\r'; beginIdx--) ;
                for (; endIdx < input.length() && chars[endIdx] != ' ' && chars[endIdx] != '\n' && chars[endIdx] != '\r'; endIdx++) ;
                return input.substring(beginIdx, endIdx);
            }
        }

        return input.substring(0, input.length() >= 200 ? 200 : input.length());
    }

    /**
     * ��������ַ���ÿ����ʾ width���ַ����С�
     * @param input
     * @param width
     * @param locale
     * @return
     */
    public static String wordWrap(String input, int width, Locale locale) {
        if (input == null)
            return "";
        if (width < 5)
            return input;
        if (width >= input.length())
            return input;
        StringBuffer buf = new StringBuffer(input);
        boolean endOfLine = false;
        int lineStart = 0;
        for (int i = 0; i < buf.length(); i++) {
            if (buf.charAt(i) == '\n') {
                lineStart = i + 1;
                endOfLine = true;
            }
            if (i > (lineStart + width) - 1)
                if (!endOfLine) {
                    int limit = i - lineStart - 1;
                    BreakIterator breaks = BreakIterator.getLineInstance(locale);
                    breaks.setText(buf.substring(lineStart, i));
                    int end = breaks.last();
                    if (end == limit + 1 && !Character.isWhitespace(buf.charAt(lineStart + end)))
                        end = breaks.preceding(end - 1);
                    if (end != -1 && end == limit + 1) {
                        buf.replace(lineStart + end, lineStart + end + 1, "\n");
                        lineStart += end;
                    } else if (end != -1 && end != 0) {
                        buf.insert(lineStart + end, '\n');
                        lineStart = lineStart + end + 1;
                    } else {
                        buf.insert(i, '\n');
                        lineStart = i + 1;
                    }
                } else {
                    buf.insert(i, '\n');
                    lineStart = i + 1;
                    endOfLine = false;
                }
        }

        return buf.toString();
    }

    public static final String highlightWords(String string, String words[], String startHighlight, String endHighlight) {
        if (string == null || words == null || startHighlight == null || endHighlight == null)
            return null;
        StringBuffer regexp = new StringBuffer();
        for (int x = 0; x < words.length; x++) {
            regexp.append(words[x]);
            if (x != words.length - 1)
                regexp.append("|");
        }

        regexp.insert(0, "s/\\b(");
        regexp.append(")\\b/");
        regexp.append(startHighlight);
        regexp.append("$1");
        regexp.append(endHighlight);
        regexp.append("/igm");
        return null;
    }

    public static final String escapeForXML(String string) {
        if (string == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>')
                if (ch == '<') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '&') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(AMP_ENCODE);
                } else if (ch == '"') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                } else if (ch != '\n' && ch != '\r' && ch != '\t' && ch < ' ') {
                    if (i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                }
        }

        if (last == 0)
            return string;
        if (i > last)
            out.append(input, last, i - last);
        return out.toString();
    }

    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }

    public static final String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        } else {
            StringBuffer buf = new StringBuffer(length);
            buf.append(zeroArray, 0, length - string.length()).append(string);
            return buf.toString();
        }
    }

    public static final String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }

    /**
     * ����ļ�����չ���ж��������mime����
     *
     * @deprecated
     */
    public static String getMimeType(String strExt) {
        strExt = strExt.toLowerCase();
        String strMimeType = "application/octet-stream"; //ȱʡ����Ϊ�����������жϵ�mime����
        //application ypes
        if ("htm".equals(strExt) || "html".equals(strExt)) {
            strMimeType = "text/html";
        } else if ("txt".equals(strExt)) {
            strMimeType = "text/plain";
        } else if ("rtf ".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("pdf".equals(strExt)) {
            strMimeType = "application/pdf";
        } else if ("doc".equals(strExt)) {
            strMimeType = "application/msword";
        } else if ("ppt".equals(strExt) || "ppz".equals(strExt) ||
                "pps".equals(strExt) || "pot".equals(strExt)) {
            strMimeType = "application/mspowerpoint ";
        }
        //Archive/Compressed Archives

        else if ("gtar".equals(strExt)) {
            strMimeType = "application/x-gtar";
        } else if ("tar".equals(strExt)) {
            strMimeType = "application/x-tar";
        } else if ("zip".equals(strExt)) {
            strMimeType = "application/zip";
        }

        //Image types
        else if ("gif".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("png".equals(strExt)) {
            strMimeType = "application/rtf";
        } else if ("jpeg".equals(strExt) || "jpg".equals(strExt) ||
                "jpe".equals(strExt)) {
            strMimeType = "image/jpeg";
        } else if ("tiff".equals(strExt) || "tif".equals(strExt)) {
            strMimeType = "application/tiff";
        }
        return strMimeType;
    }


    /**
     * To show all the session attribute names.the result will be output to console.

     * @param session HttpSession
     */
    public static void showAllSessionNames(HttpSession session) {
        java.util.Enumeration attrs = session.getAttributeNames();
        log.debug("----------------sessions begin------------------------");
        while (attrs.hasMoreElements()) {
            String temp = attrs.nextElement().toString();
            log.debug("attribute:" + temp + "\t value:" + session.getAttribute(temp));
        }
        log.debug("-----------------sessions end-------------------------");
    }


    /**
     * To show all the parameters attribute names and values
     * by the specified charset of gbk.the result will be output to console.
     *
     * @param request HttpServletRequest
     */
    public static void showAllParameters(HttpServletRequest request) {
        java.util.Enumeration names = request.getParameterNames();
        log.debug("----------------parameters  begin-----------------------");
        while (names.hasMoreElements()) {
            String temp = names.nextElement().toString();
            log.debug("param name:" + temp + "\t value:" + decodeGB(request.getParameter(temp)));
        }
        log.debug("-----------------parameters end-------------------------");
    }

    /**
     * ����ָ���ķָ�����ַ�ת��ΪList
     * @param str
     * @param split
     * @return
     */
    public static List stringToList(String str, String split) {
        List r = new ArrayList();
        if ((str != null) && (!str.trim().equals(""))) {
            str = leftTrim(str, split);
            int pre = 0;
            int index = str.indexOf(split, pre);
            while (index >= 0) {
                String freg = str.substring(pre, index);
                if (freg != null && !freg.trim().equals("")) {
                    r.add(freg);
                }
                pre = index + split.length();
                index = str.indexOf(split, pre);
            }
            String left = str.substring(pre);
            if (left != null && !left.trim().equals("")) {
                r.add(left);
            }
        }
        return r;
    }


    /**
     * trim the string s2 in the string s2
     * the s2 is on the end or start local of the s1
     * @param s1
     * @param s2
     * @return
     */
    private static String trim(String s1, String s2) {
        s1 = leftTrim(s1, s2);
        return rightTrim(s1, s2);
    }

    private static String leftTrim(String s1, String s2) {
        while (s1.startsWith(s2)) {
            s1 = s1.substring(s2.length());
        }
        return s1;
    }

    private static String rightTrim(String s1, String s2) {
        while (s1.endsWith(s2)) {
            s1 = s1.substring(0, s1.length() - s2.length());
        }
        return s1;
    }
    /**
     * list to string by adding the link
     * @param list
     * @param link
     * @return
     */
    public static String listToString(List list, String link) {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            sb.append(iterator.next()).append(link);
        }
        String rt = sb.toString();
        if (rt.length() > link.length()) {
            rt = rt.substring(0, rt.length() - link.length());
        }
        return rt;
    }

    /**
     * ���ַ��ʾ�����֣�����ָ����С���λ���������롣
     *
     * @param numberStr
     * @param decimalIndex С����ڼ�λ
     * @return
     */
    public static String round(String numberStr, int decimalIndex) {
        //�ж��Ƿ�Ϊ���֡�

        //��ȡ�ַ�
        String desStr = numberStr.substring(0, (numberStr.lastIndexOf(".") + 1) + (decimalIndex + 1));
        //��������
        BigDecimal deSource = new BigDecimal(desStr);
        String newStr = String.valueOf(deSource.setScale(decimalIndex, BigDecimal.ROUND_HALF_UP).doubleValue());

        return new String(newStr);
    }



    /**
     * test
     * @param args
     */
    public static void main(String args[]){
        System.out.println(StringUtil.hash("guoqian"));

        System.out.println(StringUtil.trim("yangchangming","guoqian"));

    }

}