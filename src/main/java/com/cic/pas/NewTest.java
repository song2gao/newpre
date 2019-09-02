package com.cic.pas;

import com.cic.pas.common.util.CRC16M;

public class NewTest {
    public static void main(String[] args) {
        int a = 20000;
        double result = (a - 5530) / ((27648 - 5530) / 100) - 20;
        double result2=a/((27648 - 5530) / 100)-5530/((27648 - 5530) / 100)-20;
        System.out.println(result);
        System.out.println(((27648 - 5530) / 100));
    }
}
