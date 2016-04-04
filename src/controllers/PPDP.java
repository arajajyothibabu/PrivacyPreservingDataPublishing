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

    private static RawDataService rawDataService;
    private static AnonymizedDataService anonymizedDataService;

    public PPDP(RawDataService rawDataService, AnonymizedDataService anonymizedDataService) throws Exception {
        this.rawDataService = rawDataService;
        this.anonymizedDataService = anonymizedDataService;
        this.anonymousDiseaseCode = getAnonymousDiseaseCodes(sortedUniqueDiseaseCodes(getDiseaseCodes()));
    }

    public PPDP(int ageRangeSize, int diseaseCodeRangeSize, RawDataService rawDataService, AnonymizedDataService anonymizedDataService) throws Exception {
        this.ageRangeSize = ageRangeSize;
        this.diseaseCodeRangeSize = diseaseCodeRangeSize;
        this.rawDataService = rawDataService;
        this.anonymizedDataService = anonymizedDataService;
        this.anonymousDiseaseCode = getAnonymousDiseaseCodes(sortedUniqueDiseaseCodes(getDiseaseCodes()));
    }

    private static int[] roundedMinMaxAge(int[] minMaxAgeFromDB){
        int[] minMax_Age = new int[2];
        minMax_Age[0] = minMaxAgeFromDB[0] - minMaxAgeFromDB[0] % 10;
        minMax_Age[1] = minMaxAgeFromDB[1] + 10 - minMaxAgeFromDB[1] % 10;
        return minMax_Age;
    }

    private static String ageToRange(int age){
        int mod = age % ageRangeSize;
        int min = mod == 0? ageRangeSize : mod;
        return "[" + (age - min + 1) + "-" + (age + ageRangeSize - min) + "]";
    }

    private static ArrayList<String> sortedUniqueDiseaseCodes(String diseaseCodes) throws Exception {
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

    private static HashMap<String, String> getAnonymousDiseaseCodes(ArrayList<String> sortedDiseaseCodes) throws Exception {
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

    private static String getDiseaseCodes() throws Exception{
        String diseaseCodes = rawDataService.getDiseaseCodes();
        return diseaseCodes;
    }

    private static String generateAnonymizedDiseaseCode(String diseaseCodes) throws Exception{
        String[] splittedDiseaseCodes = diseaseCodes.split(",");
        SortedSet<String> uniqueDiseaseCodes = new TreeSet<String>();
        for(String string : splittedDiseaseCodes){
            uniqueDiseaseCodes.add(anonymousDiseaseCode.get(string));
        }
        return Utils.concatStrings(uniqueDiseaseCodes);
    }

    private static AnonymizedData anonymizedDataFromRawData(RawData data) throws Exception {
        return new AnonymizedData(ageToRange(data.getAge()), data.getSex(),
                generateAnonymizedDiseaseCode(data.getDiseaseCode()),
                data.getClassInfo(), 1);
    }

    public ArrayList<AnonymizedData> generateAnonymizedData() throws Exception{
        ArrayList<RawData> rawDataList = rawDataService.getRawData();
        ArrayList<AnonymizedData> anonymizedDataList = new ArrayList();
        for(RawData data : rawDataList){
            anonymizedDataList.add(anonymizedDataFromRawData(data));
        }
        AnonymizedDataService.emptyData(); //emptying table before filling anonymous data
        AnonymizedDataService.upsertAnonymizedData(anonymizedDataList);
        return anonymizedDataList;
    }

}
