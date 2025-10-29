package com.example.crewstation.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

@Slf4j
public class PriceUtils {

    public static String formatMoney(int price){
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        String format = df.format(price);
        return format;
    }
    
}
