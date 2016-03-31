package controllers;

import java.io.IOException;

/**
 * Created by Araja Jyothi Babu on 29-Mar-16.
 */
public class PPDP {



    public static int[] roundedMinMaxAge(int[] minMaxAgeFromDB){
        int[] minMax_Age = new int[2];
        minMax_Age[0] = minMaxAgeFromDB[0] - minMaxAgeFromDB[0] % 10;
        minMax_Age[1] = minMaxAgeFromDB[1] + 10 - minMaxAgeFromDB[1] % 10;
        return minMax_Age;
    }

    public static int ageRangeSize(){
        return 5;
    }

    public static String ageToRange(int age, int rangeSize){
        return "[" + (age - age % rangeSize) + "-" + (age + age % rangeSize) + ")";
    }


}
