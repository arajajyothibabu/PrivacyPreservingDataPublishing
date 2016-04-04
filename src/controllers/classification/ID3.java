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
    private static Map<AttributeType, ArrayList<AnonymizedSubData>> attributes; // 0 --> ageAttribute, 1 --> sexAttribute, 2 --> codeAttribute
    private static ArrayList<AttributeType> traversableAttributes;

    public ID3(ArrayList<AnonymizedData> data) {
        this.data = data;
        this.size = data.size();
        this.classes = getClasses();
        this.attributes = getAttributes();
        this.mainEntropy = getMainEntropy();
        this.traversableAttributes = getTraversableAttributes();
    }

    private static ArrayList<AttributeType> getTraversableAttributes(){
        ArrayList<AttributeType> traversableAttributes = new ArrayList();
        traversableAttributes.add(AttributeType.AGE);
        traversableAttributes.add(AttributeType.SEX);
        traversableAttributes.add(AttributeType.CODE);
        return traversableAttributes;
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

    private static SortedSet<String> classesForAttribute(String attribute, AttributeType type) throws Exception{
        ArrayList<AnonymizedSubData> subData = getChildNodes(type);
        SortedSet<String> uniqueClasses = new TreeSet<String>();
        for(AnonymizedSubData eachAttribute : subData ){
            if(eachAttribute.model.equals(attribute))
                uniqueClasses.add(eachAttribute.classInfo);
        }
        return uniqueClasses;
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

    private static Map<AttributeType, ArrayList<AnonymizedSubData>> getAttributes(){
        ArrayList<AnonymizedSubData> ageAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> sexAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> codeAttributes = new ArrayList();
        for(AnonymizedData row : data){
            ageAttributes.add(new AnonymizedSubData(row.getAge(), row.getClassInfo()));
            sexAttributes.add(new AnonymizedSubData(row.getSex(), row.getClassInfo()));
            codeAttributes.add(new AnonymizedSubData(row.getDiseaseCode(), row.getClassInfo()));
        }
        Map<AttributeType, ArrayList<AnonymizedSubData>> attributes = new HashMap<AttributeType, ArrayList<AnonymizedSubData>>();
        attributes.put(AttributeType.AGE, ageAttributes);
        attributes.put(AttributeType.SEX, sexAttributes);
        attributes.put(AttributeType.CODE, codeAttributes);
        return attributes;
    }

    private static Map<AttributeType, ArrayList<AnonymizedSubData>> getAttributes(AttributeType type, String unClassifiedAttribute, ArrayList<AttributeType> traversableAttributes){
        ArrayList<AnonymizedSubData> ageAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> sexAttributes = new ArrayList();
        ArrayList<AnonymizedSubData> codeAttributes = new ArrayList();
        for(AnonymizedData row : data){
            if(type.equals(AttributeType.AGE) && row.getAge().equals(unClassifiedAttribute) ||
                    type.equals(AttributeType.SEX) && row.getSex().equals(unClassifiedAttribute) ||
                    type.equals(AttributeType.CODE) && row.getDiseaseCode().equals(unClassifiedAttribute)) {
                if (traversableAttributes.contains(AttributeType.AGE))
                    ageAttributes.add(new AnonymizedSubData(row.getAge(), row.getClassInfo()));
                if (traversableAttributes.contains(AttributeType.SEX))
                    sexAttributes.add(new AnonymizedSubData(row.getSex(), row.getClassInfo()));
                if (traversableAttributes.contains(AttributeType.CODE))
                    codeAttributes.add(new AnonymizedSubData(row.getDiseaseCode(), row.getClassInfo()));
            }
        }
        Map<AttributeType, ArrayList<AnonymizedSubData>> attributes = new HashMap<AttributeType, ArrayList<AnonymizedSubData>>();
        if (traversableAttributes.contains(AttributeType.AGE))
            attributes.put(AttributeType.AGE, ageAttributes);
        if (traversableAttributes.contains(AttributeType.SEX))
            attributes.put(AttributeType.SEX, sexAttributes);
        if (traversableAttributes.contains(AttributeType.CODE))
            attributes.put(AttributeType.CODE, codeAttributes);
        return attributes;
    }

    private static ArrayList<AnonymizedSubData> getChildNodes(AttributeType type){
        ArrayList<AnonymizedSubData> childNodes = new ArrayList();
        /*for(Map.Entry<AttributeType, ArrayList<AnonymizedSubData>>key : attributes.entrySet()){
            System.out.println(key.getKey() + "-----" + key.getValue().size());
        }*/
        switch (type){
            case AGE: childNodes = attributes.get(AttributeType.AGE); break;
            case SEX: childNodes = attributes.get(AttributeType.SEX); break;
            case CODE: childNodes = attributes.get(AttributeType.CODE); break;
            default:
        }
        return childNodes;
    }

    private static Node classificationTree(Map<AttributeType, ArrayList<AnonymizedSubData>> attributes, ArrayList<AttributeType> traversableAttributes) throws Exception{
        SortedMap<AttributeType, Double> informationGains = new TreeMap<AttributeType, Double>();
        if(traversableAttributes.contains(AttributeType.AGE))
            informationGains.put(AttributeType.AGE, informationGain(attributes.get(AttributeType.AGE)));
        if(traversableAttributes.contains(AttributeType.SEX))
            informationGains.put(AttributeType.SEX, informationGain(attributes.get(AttributeType.SEX)));
        if(traversableAttributes.contains(AttributeType.CODE))
            informationGains.put(AttributeType.CODE, informationGain(attributes.get(AttributeType.CODE)));
        if(!traversableAttributes.isEmpty()) {
            traversableAttributes.remove(informationGains.firstKey());
            AttributeType rootType = informationGains.firstKey();
            Node root = new Node(rootType.toString());
            ArrayList<Node> children = new ArrayList();
            ArrayList<String> childAttributes = getUniqueList(getChildNodes(rootType));
            SortedSet<String> uniqueClassesForAttribute;
            for (String attribute : childAttributes) {
                uniqueClassesForAttribute = classesForAttribute(attribute, rootType);
                children.add(new Node(attribute));
                if (uniqueClassesForAttribute.size() == 1) {
                    children.get(children.size() - 1).children.add(new Node(uniqueClassesForAttribute.first()));
                } else {
                    attributes = getAttributes(rootType, attribute, traversableAttributes); //updating attributes
                    //System.out.println("attributes ==" + attributes.size() + "-----travesrr" + traversableAttributes.size() );
                    children.get(children.size() - 1).children.add(classificationTree(attributes, traversableAttributes));
                }
            }
            root.setChildren(children);
            return root;
        }else{
            return new Node("END");
        }
    }

    private static void printTree(Node root) throws Exception {
       if(root != null) {
            ArrayList<Node> children;
            String hasChildren = root.children.size() > 1 ? " **" : root.children.size() == 1 ? " *" : "";
            System.out.println("\n " + root.name + hasChildren);
            for (Node child : root.children)
                printTree(child);
        }
    }

    public void processData() throws Exception{
        /*for(Map.Entry<AttributeType, Double>key : informationGains.entrySet()){
            System.out.print(key.getKey() + "-----" + key.getValue());
        }*/
        printTree(classificationTree(attributes, traversableAttributes));
    }

}
