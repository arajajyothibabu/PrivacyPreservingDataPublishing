package models;

/**
 * Created by Araja Jyothi Babu on 02-Apr-16.
 */
public class AnonymizedSubData {

    public String model;
    public String classInfo;

    public AnonymizedSubData(String model, String classInfo) {
        this.model = model;
        this.classInfo = classInfo;
    }

    public AnonymizedSubData() {
    }

    public String getCode() {
        return model;
    }

    public void setCode(String model) {
        this.model = model;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

}
