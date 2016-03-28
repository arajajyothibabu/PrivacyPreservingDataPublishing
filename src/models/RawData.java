package models;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class RawData {

    private int id;
    private String sex;
    private int age;
    private int[] diseaseCode;
    private char classInfo;

    public RawData() {
    }

    public RawData(int id, String sex, int age, int[] diseaseCode, char classInfo) {
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.diseaseCode = diseaseCode;
        this.classInfo = classInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int[] getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(int[] diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public char getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(char classInfo) {
        this.classInfo = classInfo;
    }

}
