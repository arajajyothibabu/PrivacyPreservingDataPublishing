package controllers;

import java.util.*;

/**
 * Created by Araja Jyothi Babu on 29-Mar-16.
 */
public class PPDP {

    private static int ageRangeSize = 5;
    private static int diseaseCodeRangeSize = 2;

    public HashMap<String, String> anonymousDiseaseCode;

    public PPDP(String diseaseCodes) throws Exception {
        this.anonymousDiseaseCode = anonymousDiseaseCodes(sortedUniqueDiseaseCodes(diseaseCodes));
    }

    public static int[] roundedMinMaxAge(int[] minMaxAgeFromDB){
        int[] minMax_Age = new int[2];
        minMax_Age[0] = minMaxAgeFromDB[0] - minMaxAgeFromDB[0] % 10;
        minMax_Age[1] = minMaxAgeFromDB[1] + 10 - minMaxAgeFromDB[1] % 10;
        return minMax_Age;
    }

    public static String ageToRange(int age){
        int min = age % ageRangeSize == 0? ageRangeSize : age % ageRangeSize;
        return "[" + (age - min + 1) + "-" + (age + age % ageRangeSize) + ")";
    }

    public static ArrayList<String> sortedUniqueDiseaseCodes(String diseaseCodes) throws Exception {
        String[] redundantCodes = diseaseCodes.split(",");
        Set<String> uniqueCodes = new HashSet<String>();
        for(String code : redundantCodes){
            uniqueCodes.add(code);
        }
        TreeSet<String> sortedUniqueCodes = new TreeSet<String>(uniqueCodes);
        ArrayList<String> sortedList = new ArrayList();
        for(String code : sortedUniqueCodes)
            sortedList.add(code);
        return sortedList;
    }

    public static HashMap<String, String> anonymousDiseaseCodes(ArrayList<String> sortedDiseaseCodes) throws Exception {
        HashMap<String, String> encodedDiseaseCodes = new HashMap<String, String>();
        int i = 0, k = 0;
        while(i < sortedDiseaseCodes.size()){
            k++;
            for(int j = 0; j < diseaseCodeRangeSize; j++){
                encodedDiseaseCodes.put(sortedDiseaseCodes.get(i++), k + "*");
            }
        }
        return encodedDiseaseCodes;
    }


}
