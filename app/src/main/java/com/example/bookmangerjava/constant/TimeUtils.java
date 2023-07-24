package com.example.bookmangerjava.constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
    public static String getTimeAgo(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        cal.setTimeZone(tz);
        cal.setTimeInMillis(time);
        Calendar now = Calendar.getInstance(tz);

        // Kiểm tra nếu ngày được chọn là ngày hiện tại
        if (now.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
            // Xử lý logic khi ngày là ngày hiện tại
            long diff = now.getTimeInMillis() - cal.getTimeInMillis();
            int hours = (int) (diff / (60 * 60 * 1000));
            if (hours > 0) {
                return hours + " giờ trước";
            } else {
                int minutes = (int) (diff / (60 * 1000));
                if (minutes > 0) {
                    return minutes + " phút trước";
                } else {
                    return "vừa xong";
                }
            }
        } else if (now.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR) == 1) {
            return "Hôm qua";
        } else if (time > now.getTimeInMillis()) {
            // Xử lý ngày trong tương lai
            long diff = time - now.getTimeInMillis();
            int days = (int) (diff / (24 * 60 * 60 * 1000));
            if (days == 1) {
                return "Ngày mai";
            } else {
                return days + " ngày sau";
            }
        } else {
            // Định dạng ngày theo định dạng bạn muốn
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
            dateFormat.setTimeZone(tz);
            return dateFormat.format(cal.getTime());
        }
    }
}