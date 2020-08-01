package com.example.stocklist.Common;

import java.text.DecimalFormat;

public class Utils {
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String Convertdouble (Double digit){
        String formated_double= "$ "+String.valueOf(df.format(digit));
        return formated_double;
    }
}
