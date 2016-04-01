package models;

/**
 * Created by Araja Jyothi Babu on 02-Apr-16.
 */
public class AgeModel implements SubModel{

    public String model;
    public String classInfo;

    public AgeModel(String model, String classInfo) {
        this.model = model;
        this.classInfo = classInfo;
    }

    public AgeModel() {
    }

    public String getAge() {
        return model;
    }

    public void setAge(String model) {
        this.model = model;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }
}
