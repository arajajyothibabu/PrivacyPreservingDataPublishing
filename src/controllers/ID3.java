package controllers;

import models.*;
import utils.AttributeType;
import java.util.*;

/**
 * Created by Araja Jyothi Babu on 01-Apr-16.
 */
public class ID3 {

    public ID3() {
    }

    private static double log2(double b){
        return Math.log(b)/Math.log(2);
    }

    private static double entropy(ArrayList<Double> p){
        double sum = 0;
        for(double i : p){
            sum -= i * log2(i);
        }
        return sum;
    }

    private static SubModel attributeType(AttributeType type, SubModel model){
        switch(type){
            case AGE: return (AgeModel)model;
            case SEX: return (SexModel)model;
            case CODE: return (CodeModel)model;
            default: return (AgeModel)model; //FIXME:default need to handle
        }
    }

    private Map<T, E> getComponentMedicineMap() {
        Map<Component, List<Medicine>> componentMedicineMap = new HashMap<Component, List<Medicine>>();
        for(Medicine medicine : medList){
            if(componentMedicineMap.containsKey(medicine.getComponent())){
                componentMedicineMap.get(medicine.getComponent()).add(medicine);
            }
            else{
                List<Medicine> medicineList = new ArrayList<Medicine>();
                medicineList.add(medicine);
                componentMedicineMap.put(medicine.getComponent(), medicineList);
            }
        }
        return componentMedicineMap;
    }

    private static double infoGain(ArrayList<SubModel> model, AttributeType type){
        int size = model.size();
        ArrayList<Double> probabilityList = new ArrayList();
        switch()
    }

    public static void learnData(ArrayList<AnonymizedData> data){
        ArrayList<AgeModel> ageModels = new ArrayList();
        ArrayList<SexModel> sexModels = new ArrayList();
        ArrayList<CodeModel> codeModels = new ArrayList();
        for(AnonymizedData row : data){
            ageModels.add(new AgeModel(row.getAge(), row.getClassInfo()));
            sexModels.add(new SexModel(row.getSex(), row.getClassInfo()));
            codeModels.add(new CodeModel(row.getDiseaseCode(), row.getClassInfo()));
        }
    }

}
