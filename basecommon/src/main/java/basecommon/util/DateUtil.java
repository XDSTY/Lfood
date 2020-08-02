package basecommon.util;

import org.apache.commons.lang.time.FastDateFormat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author 张富华
 * @date 2020/6/16 15:09
 */
public class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String DATE_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<HashMap<String, SimpleDateFormat>> CUSTOMER_MAP_THREAD = new
            ThreadLocal<>();

    private static FastDateFormat dateFormat = FastDateFormat.getInstance(DEFAULT_PATTERN);

    private static FastDateFormat dateTimeFormat = FastDateFormat.getInstance(DATE_TIME_PATTERN);

    private static FastDateFormat dateSecondFormat = FastDateFormat.getInstance(DATE_SECOND_PATTERN);

    public static String date2String(Date pUtilDate, String pFormat) {
        String result = "";
        if (pUtilDate != null) {
            SimpleDateFormat sdf = getSimpleDateFormat(pFormat);
            result = sdf.format(pUtilDate);
        }
        return result;
    }

    public static String date2String(
            Date pUtilDate) {
        return dateFormat.format(pUtilDate);
    }


    public static String dateTimeToString(Date pUtilDate) {
        if (pUtilDate == null) {
            return "";
        }
        return dateTimeFormat.format(pUtilDate);
    }

    public static String date2SecondString(
            Date pUtilDate) {
        return dateSecondFormat.format(pUtilDate);
    }

    public static Date dateString2Date(String dateStr) {
        return dateString2Date(dateStr, DEFAULT_PATTERN);
    }

    public static Date dateString2MinDate(String dateStr) {
        return dateString2Date(dateStr, DATE_TIME_PATTERN);
    }

    public static Date dateString2SecondDate(String dateStr) {
        return dateString2Date(dateStr, DATE_SECOND_PATTERN);
    }

    public static Date dateString2Date(String dateStr, String partner) {

        try {
            SimpleDateFormat formatter = getSimpleDateFormat(partner);
            ParsePosition pos = new ParsePosition(0);
            return formatter.parse(dateStr, pos);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat;
        HashMap<String, SimpleDateFormat> simpleDateFormatMap = CUSTOMER_MAP_THREAD.get();
        if (simpleDateFormatMap != null && simpleDateFormatMap.containsKey(pattern)) {
            simpleDateFormat = simpleDateFormatMap.get(pattern);
        } else {
            simpleDateFormat = new SimpleDateFormat(pattern);
            if (simpleDateFormatMap == null) {
                simpleDateFormatMap = new HashMap<>();
            }
            simpleDateFormatMap.put(pattern, simpleDateFormat);
            CUSTOMER_MAP_THREAD.set(simpleDateFormatMap);
        }

        return simpleDateFormat;
    }

    public static int getMonthOfDate(Date pDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(pDate);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getMonthOfDate(String date) {
        return getMonthOfDate(dateString2Date(date));
    }

    public static int getDayOfDate(Date pDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(pDate);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static List<String> getDatePeriodDay(String pDate, int count)
            throws ParseException {
        List<String> v = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(pDate));
        v.add(sdf.format(calendar.getTime()));

        for (int i = 0; i < count - 1; i++) {
            calendar.add(Calendar.DATE, 1);
            v.add(dateFormat.format(calendar.getTime()));
        }

        return v;
    }
}
