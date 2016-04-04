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
    private static ArrayList<ArrayList<AnonymizedSubData>> attributes; // 0 --> ageAttribute, 1 --> sexAttribute, 2 --> codeAttribute

    public ID3(ArrayList<AnonymizedData> data) {
        this.data = data;
        this.size = data.size();
        this.classes = getClasses();
        this.attributes = getAttributes();
        this.mainEntropy = getMainEntropy();
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
            if(i > 0) //to handle log2(0) = infinity condition
                sum -= i * log2(i);
        }
        return sum;
    }

    private static double probabilityOf(String attribute, String classInfo, ArrayList<AnonymizedSubData> subData) throws Exception{
        int count = 0;
        double frequency = 0;
        for(AnonymizedSubData eachAttribute : subData ){
            if(eachAttribute.model.equals(attribute)){
                count++;
                if(eachAttribute.classInfo.equals(classInfo))
                    frequency++;
            }
        }
        return frequency/count;
    }

    private static ArrayList<String> getUniqueList(ArrayList<AnonymizedSubData> subData){
        ArrayList<String> attributes = getAttributeList(subData);
        SortedSet<String> set = new TreeSet<String>(attributes);
        ArrayList<String> uniqueList = new ArrayList();
        for(String uniqueValue : set){
            uniqueList.add(uniqueValue);
        }
        return uniqueList;
    }

    private static ArrayList<String> getAttributeList(ArrayList<AnonymizedSubData> subData){
        ArrayList<String> attributes = new ArrayList();
        for(AnonymizedSubData eachAttribute : subData ){
            attributes.add(eachAttribute.model);
        }
        return attributes;
    }

    private static double informationGain(ArrayList<AnonymizedSubData> subData) throws Exception{
        int S = subData.size(); //size of DataSet
        ArrayList<Double> probabilityList = new ArrayList();
        ArrayList<String> uniqueAttributes = getUniqueList(subData);
        ArrayList<String> attributes = getAttributeList(subData);
        double infoGain = mainEntropy;
        double s = 0;
        for(String uniqueAttribute : uniqueAttributes){
            for(String uniqueClass : classes){
                probabilityList.add(probabilityOf(uniqueAttribute, uniqueClass, subData));
            }
            s = Collections.frequency(attributes, uniqueAttribute);
            infoGain -= (Math.abs(s)/S) * entropy(probabilityList);
            probabilityList.clear();
        }
        return infoGain;
    }

    private static ArrayList<ArrayList<AnonymizedSubData>> getAttributes(){
        ArrayList<AnonymizedSubData> ageAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> sexAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> codeAttributes = new ArrayList();
        for(AnonymizedData row : data){
            ageAttributes.add(new AnonymizedSubData(row.getAge(), row.getClassInfo()));
            sexAttributes.add(new AnonymizedSubData(row.getSex(), row.getClassInfo()));
            codeAttributes.add(new AnonymizedSubData(row.getDiseaseCode(), row.getClassInfo()));
        }
        ArrayList<ArrayList<AnonymizedSubData>> attributes = new ArrayList();
        attributes.add(ageAttributes);
        attributes.add(sexAttributes);
        attributes.add(codeAttributes);
        return attributes;
    }

    private static ArrayList<AnonymizedSubData> getChildNodes(AttributeType type){
        ArrayList<AnonymizedSubData> childNodes = new ArrayList();
        switch (type){
            case AGE: childNodes = attributes.get(0); break;
            case SEX: childNodes = attributes.get(1); break;
            case CODE: childNodes = attributes.get(2); break;
            default:
        }
        return childNodes;
    }

    public void processData() throws Exception{
        SortedMap<AttributeType, Double> informationGains = new TreeMap<AttributeType, Double>();
        informationGains.put(AttributeType.AGE, informationGain(attributes.get(0)));
        informationGains.put(AttributeType.SEX, informationGain(attributes.get(1)));
        informationGains.put(AttributeType.CODE, informationGain(attributes.get(2)));
        AttributeType rootType = informationGains.firstKey();
        /*for(Map.Entry<AttributeType, Double>key : informationGains.entrySet()){
            System.out.print(key.getKey() + "-----" + key.getValue());
        }*/
        Node root = new Node(rootType.toString());
        ArrayList<Node> children = new ArrayList();
        ArrayList<AnonymizedSubData> childNodes = getChildNodes(rootType);
        ArrayList<String> uniqueAttributes = getUniqueList(childNodes);
        for(String attribute : uniqueAttributes){
            children.add(new Node(attribute));
        }
        root.setChildren(children);
        System.out.print(root);
    }

}
