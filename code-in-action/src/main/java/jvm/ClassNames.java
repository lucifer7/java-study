package jvm;

import org.apache.commons.lang.time.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Usage: <b> Class and array class names </b>
 * Name:            the name you'd use to dynamically load the class with, like Class.forName
 * Canonical name:  used by import statement and uniquely identifies the class, toString()
 * Simple name:     not guaranteed to be unique
 *
 * Name vs. Canonical Name:
 * 1. Inner class
 * 2. Anonymous class
 * 3. Array
 *
 * @author Jingyi.Yang
 *         Date 2016/11/22
 **/
public class ClassNames {
    public static void main(String[] args) {
        System.out.println(DateUtils.round(new Date(), Calendar.DATE));

        //primitive
        System.out.println(int.class.getName());
        System.out.println(int.class.getCanonicalName());
        System.out.println(int.class.getSimpleName());

        System.out.println();

//class
        System.out.println(String.class.getName());
        System.out.println(String.class.getCanonicalName());
        System.out.println(String.class.getSimpleName());

        System.out.println();

//inner class
        System.out.println(HashMap.SimpleEntry.class.getName());
        System.out.println(HashMap.SimpleEntry.class.getCanonicalName());
        System.out.println(HashMap.SimpleEntry.class.getSimpleName());

        System.out.println();

//anonymous inner class
        System.out.println(new Serializable() {
        }.getClass().getName());
        System.out.println(new Serializable() {
        }.getClass().getCanonicalName());
        System.out.println(new Serializable() {
        }.getClass().getSimpleName());

        {
            //primitive Array
            int demo[] = new int[5];
            Class<? extends int[]> clzz = demo.getClass();
            System.out.println(clzz.getName());
            System.out.println(clzz.getCanonicalName());
            System.out.println(clzz.getSimpleName());
        }

        System.out.println();

        {
            //Object Array
            Integer demo[] = new Integer[5];
            Class<? extends Integer[]> clzz = demo.getClass();
            System.out.println(clzz.getName());
            System.out.println(clzz.getCanonicalName());
            System.out.println(clzz.getSimpleName());
        }
    }
}
