package models;

/**
 * Created by Araja Jyothi Babu on 02-Apr-16.
 */
public class CodeModel implements SubModel {

    public String model;
    public String classInfo;

    public CodeModel(String model, String classInfo) {
        this.model = model;
        this.classInfo = classInfo;
    }

    public CodeModel() {
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
