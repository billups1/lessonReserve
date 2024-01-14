package hs.lessonReserve.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomFormatter {

    public static String makePrice(int price) {
        DecimalFormat dFComma = new DecimalFormat("#,###");
        String stringPrice = dFComma.format(price);

        return stringPrice;
    }

    public static String make_yyyyMMdd(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
