package models;

/**
 * Created by Araja Jyothi Babu on 02-Apr-16.
 */
public class SexModel implements SubModel{

    public String model;
    public String classInfo;

    public SexModel(String model, String classInfo) {
        this.model = model;
        this.classInfo = classInfo;
    }

    public SexModel() {
    }

    public String getSex() {
        return model;
    }

    public void setSex(String model) {
        this.model = model;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }
}
