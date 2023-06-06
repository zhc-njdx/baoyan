package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;


public class Utility
{
    public static final String DATETIMEPATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIMEPATTERN_ISO = "yyyyMMdd'T'HHmmss+08";
    public static final String DATETIMEPATTERN_TIGHT = "yyyyMMddHHmmss";
    public static final String DATETIMEPATTERN_SIMPLE = "MM-dd HH:mm";
    public static final String DATEPATTERN_NORMAL = "yyyy-MM-dd";
    public static final String DATEPATTERN_TIGHT = "yyyyMMdd";
    public static final String TIMEPATTERN_NORMAL = "HH:mm:ss";
    public static final String TIMEPATTERN_HOURMIN = "HH:mm";
    public static final String DATEPATTERN_DATE = "MM-dd";
    
    
    
    /**
     * 将时间日期转换为字符串
     * @param source 时间日期对象
     * @param pattern 模式
     * @return
     */
    public static String localDateTimeToString(LocalDateTime source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return source.format(formatter);
    }
    
    /**
     * 将日期转换为字符串
     * @param source
     * @param pattern
     * @return
     */
    public static String localDateToString(LocalDate source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return source.format(formatter);
    }
    
    /**
     * 时间转LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date)
    {
        if(date == null)
            return null;
        
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        LocalDateTime result = LocalDateTime.ofInstant(instant, zone);
        return result;
    }
    
    /**
     * 将字符串日期时间转换为时间日期
     * @param source
     * @param pattern
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(source, formatter);
    }
    
    /**
     * 将字符串日期转换为日期
     * @param source
     * @param pattern
     * @return
     */
    public static LocalDate stringToLocalDate(String source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(source, formatter);
    }
    
    /**
     * 将开始结束日期转换为日期区间字符串
     * @param start
     * @param end
     * @return
     */
    public static String localDateRangeToString(LocalDate start, LocalDate end)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEPATTERN_NORMAL);
        return start.format(formatter)+" ~ "+end.format(formatter);
    }
    
    /**
     * 获取随机串
     * @param len 长度
     * @return
     */
    public static String getRandomStr(int len)
    {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
     * 获取异或累加值
     * @param src
     * @return
     */
    public static byte getXorAccumulation(byte[] src)
    {
        if(src == null || src.length == 0)
            return 0x00;
        
        byte xor = 0;
        for(int i=0;i<src.length;i++)
        {
            xor ^= src[i];
        }
        return xor;
    }
    
    /**
     * 获取MD5密码（已过时）
     * 使用DigestUtils.md5Hex()替代
     * 需导入包commons-codec
     * @param plainText
     * @return
     */
    @Deprecated
    public static String getMd5(String plainText)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            
            int i;
            
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    /**
     * 生成上传文件名
     * @return
     */
    public static String genSerialFileName()
    {
        String timeStr = localDateTimeToString(LocalDateTime.now(), DATETIMEPATTERN_TIGHT);
        return timeStr + getRandomStr(6);
    }
    
    /**
     * 过滤Emoji表情
     * @param source
     * @return
     */
    public static String filterEmoji(String source)
    {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        }else{
            return source;
        }
    }
    
    /**
     * 获取当月最大日期
     * @return
     */
    public static int getMaximunDateOfCurrMonth()
    {
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    /**
     * 将字节数组转换为十六进制可读串
     * @param src
     * @return
     */
    public static String bytesToReadableHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            if(i < src.length - 1)
                stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
    
    /**
     * 将byte单字节转换成十六进制字符串
     * @param src
     * @return
     */
    public static String byteToHexString(int src) {
        StringBuilder ret = new StringBuilder();
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            ret.append(0);
        }
        ret.append(hv);
        return ret.toString();
    }
    
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * gbk转utf8有损转换
     * @param gbkStr
     * @return
     */
    public static byte[] getUTF8BytesFromGBKString(String gbkStr)
    {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
    
    public static int getRandomInteger(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public static float getRandomFloat(int base)
    {
        Random rand = new Random();
        return base + rand.nextFloat();
    }
    
    /**
     * 经纬度校验(只校验正数 0-90.000000 0-180.000000 范围内)
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     * @return
     */
    public static boolean checkItude(String longitude,String latitude){
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        longitude = longitude.trim();
        latitude = latitude.trim();
        return longitude.matches(reglo)==true?latitude.matches(regla):false;
    }
}
