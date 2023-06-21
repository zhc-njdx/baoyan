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


public class Utility
{
    public static final String DATETIME_PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_PATTERN_ISO = "yyyyMMdd'T'HHmmss+08";
    public static final String DATETIME_PATTERN_TIGHT = "yyyyMMddHHmmss";
    public static final String DATETIME_PATTERN_SIMPLE = "MM-dd HH:mm";
    public static final String DATE_PATTERN_NORMAL = "yyyy-MM-dd";
    public static final String DATE_PATTERN_TIGHT = "yyyyMMdd";
    public static final String TIME_PATTERN_NORMAL = "HH:mm:ss";
    public static final String TIME_PATTERN_HOURMIN = "HH:mm";
    public static final String DATE_PATTERN_DATE = "MM-dd";

    /*
    LocalDateTime/LocalDate 和 String 之间的相互转换
     */
    
    
    /**
     * 将时间日期LocalDateTime转换为字符串
     * @param source 时间日期对象 LocalDateTime
     * @param pattern 模式 String
     * @return 对应字符串
     */
    public static String localDateTimeToString(LocalDateTime source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return source.format(formatter);
    }
    
    /**
     * 将日期LocalDate转换为字符串
     * @param source LocalDate
     * @param pattern String
     * @return 对应字符串
     */
    public static String localDateToString(LocalDate source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return source.format(formatter);
    }

    /**
     * 将字符串日期时间转换为时间日期
     * @param source String 待解析的时间字符串
     * @param pattern String 需要解析成的字符串格式
     * @return LocalDateTime类型
     */
    public static LocalDateTime stringToLocalDateTime(String source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(source, formatter);
    }

    /**
     * 将字符串日期转换为日期
     * @param source String 待解析的时间字符串
     * @param pattern String 需要解析成的字符串格式
     * @return LocalDate类型
     */
    public static LocalDate stringToLocalDate(String source, String pattern)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(source, formatter);
    }
    
    /**
     * 时间转LocalDateTime
     * @param date Date类型的时间
     * @return LocalDateTime类型的时间
     */
    public static LocalDateTime dateToLocalDateTime(Date date)
    {
        if(date == null)
            return null;
        
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        return LocalDateTime.ofInstant(instant, zone);
    }
    
    /**
     * 将开始结束日期转换为日期区间字符串（LocalDate类型）
     * @param start 开始日期
     * @param end 结束日期
     * @return start ~ end 格式的字符串
     */
    public static String localDateRangeToString(LocalDate start, LocalDate end)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_NORMAL);
        return start.format(formatter)+" ~ "+end.format(formatter);
    }
    
    /**
     * 获取随机串
     * @param len 长度
     * @return 指定长度的字符串
     */
    public static String getRandomStr(int len)
    {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
     * 获取异或累加值
     * @param src 待计算的byte数组
     * @return 结果
     */
    public static byte getXorAccumulation(byte[] src)
    {
        if(src == null || src.length == 0)
            return 0x00;
        
        byte xor = 0;
        for (byte b : src) {
            xor ^= b;
        }

        return xor;
    }
    
    /**
     * 生成上传文件名
     * @return yyyyMMddHHmmss格式的时间字符串+长度为6的随机字符串
     */
    public static String genSerialFileName()
    {
        String timeStr = localDateTimeToString(LocalDateTime.now(), DATETIME_PATTERN_TIGHT);
        return timeStr + getRandomStr(6);
    }
    
    /**
     * 获取当月最大日期
     * @return 当前月份的最大日期
     */
    public static int getMaximumDateOfCurrMonth()
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
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length == 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    /**
     * 将字节数组转换为十六进制可读串（在每个16进制数之间增加一个空格）
     * @param src byte[] data
     * @return 十六进制可读串
     */
    public static String bytesToReadableHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length == 0) {
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
     * 将int数值转换成十六进制字符串
     * @param src int类型
     * @return 十六进制字符串
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
     * 将16进制数转换为十进制数
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

    /**
     * 获取指定区间的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 区间内的随机数
     */
    public static int getRandomInteger(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * 获取随机浮点数
     * @param base 指定小数点前的数值
     * @return 随机浮点数
     */
    public static float getRandomFloat(int base)
    {
        Random rand = new Random();
        return base + rand.nextFloat();
    }
    
    /**
     * 经纬度校验(只校验正数 0-90.000000 0-180.000000 范围内)
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     * @return  是否满足经纬度要求
     */
    public static boolean checkItude(String longitude,String latitude){
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        longitude = longitude.trim();
        latitude = latitude.trim();
        return longitude.matches(reglo) && latitude.matches(regla);
    }
}
