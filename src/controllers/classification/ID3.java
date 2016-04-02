package controllers.classification;

import models.*;
import utils.AttributeType;
import java.util.*;

/**
 * Created by Araja Jyothi Babu on 01-Apr-16.
 */
public class ID3 {

    private static int size = 0;
    private static double mainEntropy = 0;
    private static ArrayList<AnonymizedData> data;
    private static ArrayList<String> classes;

    public ID3(ArrayList<AnonymizedData> data) {
        this.data = data;
        this.size = data.size();
        this.classes = getClasses();
        mainEntropy = getMainEntropy();
    }

    private static double log2(double b){
        return Math.log(b)/Math.log(2);
    }

    private static ArrayList<String> getClasses(){
        SortedSet<String> uniqueClasses = new TreeSet<String>();
        for(AnonymizedData row : data){
            uniqueClasses.add(row.getClassInfo());
        }
        ArrayList<String> classes = new ArrayList();
        for(String uniqueClass : uniqueClasses){
            classes.add(uniqueClass);
        }
        return classes;
    }

    private static double getMainEntropy(){
        int size = data.size();
        ArrayList<Double> probabilityList = new ArrayList();
        double frequency = 0;
        for(String classInfo : classes){
            frequency = Collections.frequency(classes, classInfo);
            probabilityList.add(frequency/size);
        }
        return entropy(probabilityList);
    }

    private static double entropy(ArrayList<Double> p){
        double sum = 0;
        for(double i : p){
            sum -= i * log2(i);
        }
        return sum;
    }

    private static double probabilityOf(String model, String classInfo, ArrayList<AnonymizedSubData> subData) throws Exception{
        int count = 0;
        double frequency = 0;
        for(AnonymizedSubData eachModel : subData ){
            if(eachModel.model.equals(model)){
                count++;
                if(eachModel.classInfo.equals(classInfo))
                    frequency++;
            }
        }
        return frequency/count;
    }

    private static ArrayList<String> getUniqueList(ArrayList<String> list){
        ArrayList<String> uniqueList = new ArrayList();
        SortedSet<String> set = new TreeSet<String>(list);
        for(String uniqueValue : set){
            uniqueList.add(uniqueValue);
        }
        return uniqueList;
    }

    /*private static ArrayList<String> getUniqueList(ArrayList<AnonymizedSubData> subData){
        ArrayList<String> models = new ArrayList();
        for(AnonymizedSubData eachModel : subData ){
            models.add(eachModel.model);
        }
        SortedSet<String> set = new TreeSet<String>(models);
        ArrayList<String> uniqueList = new ArrayList();
        for(String uniqueValue : set){
            uniqueList.add(uniqueValue);
        }
        return uniqueList;
    }*/

    private static double informationGain(ArrayList<AnonymizedSubData> subData) throws Exception{
        int S = subData.size(); //size of DataSet
        ArrayList<Double> probabilityList = new ArrayList();
        ArrayList<String> models = new ArrayList();
        for(AnonymizedSubData eachModel : subData ){
            models.add(eachModel.model);
        }
        ArrayList<String> uniqueModels = getUniqueList(models);
        double infoGain = mainEntropy;
        int s = 0;
        for(String uniqueModel : uniqueModels){
            for(String uniqueClass : classes){
                probabilityList.add(probabilityOf(uniqueModel, uniqueClass, subData));
            }
            s = Collections.frequency(models, uniqueModel);
            infoGain -= (Math.abs(s)/S) * entropy(probabilityList);
            probabilityList.clear();
        }
        return infoGain;
    }

    public static void learnData(ArrayList<AnonymizedData> data) throws Exception{
        ArrayList<AnonymizedSubData> ageModels = new ArrayList();
        ArrayList<AnonymizedSubData> sexModels = new ArrayList();
        ArrayList<AnonymizedSubData> codeModels = new ArrayList();
        for(AnonymizedData row : data){
            ageModels.add(new AnonymizedSubData(row.getAge(), row.getClassInfo()));
            sexModels.add(new AnonymizedSubData(row.getSex(), row.getClassInfo()));
            codeModels.add(new AnonymizedSubData(row.getDiseaseCode(), row.getClassInfo()));
        }
        SortedMap<AttributeType, Double> informationGains = new TreeMap<AttributeType, Double>();
        informationGains.put(AttributeType.AGE, informationGain(ageModels));
        informationGains.put(AttributeType.SEX, informationGain(sexModels));
        informationGains.put(AttributeType.CODE, informationGain(codeModels));
        AttributeType root = informationGains.firstKey();
    }

}
