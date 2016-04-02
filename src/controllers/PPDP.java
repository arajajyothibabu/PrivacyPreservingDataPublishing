package controllers;

import models.AnonymizedData;
import models.RawData;
import services.AnonymizedDataService;
import services.RawDataService;
import utils.Utils;
import java.util.*;

/**
 * Created by Araja Jyothi Babu on 29-Mar-16.
 */
public class PPDP {

    private static int ageRangeSize = 5;
    private static int diseaseCodeRangeSize = 2;

    public static HashMap<String, String> anonymousDiseaseCode;

    public PPDP() throws Exception {
        this.anonymousDiseaseCode = getAnonymousDiseaseCodes(sortedUniqueDiseaseCodes(getDiseaseCodes()));
    }

    public static int[] roundedMinMaxAge(int[] minMaxAgeFromDB){
        int[] minMax_Age = new int[2];
        minMax_Age[0] = minMaxAgeFromDB[0] - minMaxAgeFromDB[0] % 10;
        minMax_Age[1] = minMaxAgeFromDB[1] + 10 - minMaxAgeFromDB[1] % 10;
        return minMax_Age;
    }

    public static String ageToRange(int age){
        int mod = age % ageRangeSize;
        int min = mod == 0? ageRangeSize : mod;
        return "[" + (age - min + 1) + "-" + (age + ageRangeSize - min) + "]";
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

    public static HashMap<String, String> getAnonymousDiseaseCodes(ArrayList<String> sortedDiseaseCodes) throws Exception {
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

    public static String getDiseaseCodes() throws Exception{
        String diseaseCodes = RawDataService.getDiseaseCodes();
        return diseaseCodes;
    }

    public static String generateAnonymizedDiseaseCode(String diseaseCodes) throws Exception{
        String[] splittedDiseaseCodes = diseaseCodes.split(",");
        SortedSet<String> uniqueDiseaseCodes = new TreeSet<String>();
        for(String string : splittedDiseaseCodes){
            uniqueDiseaseCodes.add(anonymousDiseaseCode.get(string));
        }
        return Utils.concatStrings(uniqueDiseaseCodes);
    }

    public static AnonymizedData anonymizedDataFromRawData(RawData data) throws Exception {
        return new AnonymizedData(ageToRange(data.getAge()), data.getSex(),
                generateAnonymizedDiseaseCode(data.getDiseaseCode()),
                data.getClassInfo(), 1);
    }

    public void generateAnonymizedData() throws Exception{
        ArrayList<RawData> rawDataList = RawDataService.getRawData();
        ArrayList<AnonymizedData> anonymizedDataList = new ArrayList();
        for(RawData data : rawDataList){
            anonymizedDataList.add(anonymizedDataFromRawData(data));
        }
        AnonymizedDataService.upsertAnonymizedData(anonymizedDataList);
    }

}
