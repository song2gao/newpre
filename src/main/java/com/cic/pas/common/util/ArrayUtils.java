package com.cic.pas.common.util;

import java.util.Arrays;

public class ArrayUtils {
    public static boolean inArray(String[] array,String s){
        return Arrays.asList(array).contains(s);
    }
}
