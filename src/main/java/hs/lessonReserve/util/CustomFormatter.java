package hs.lessonReserve.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class CustomFormatter {

    public static String makePrice(int price) {
        DecimalFormat dFComma = new DecimalFormat("#,###");
        String stringPrice = dFComma.format(price);

        return stringPrice;
    }

}
