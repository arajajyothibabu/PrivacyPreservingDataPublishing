package models;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class AnonymizedData {

    private int[] age; //just range of two values (15- 60)
    private String sex;
    private String diseaseCode;
    private char classInfo;
    private int count;

    public AnonymizedData() {
    }

    public AnonymizedData(int[] age, String sex, String diseaseCode, char classInfo, int count) {
        this.age = age;
        this.sex = sex;
        this.diseaseCode = diseaseCode;
        this.classInfo = classInfo;
        this.count = count;
    }

    public int[] getAge() {
        return age;
    }

    public void setAge(int[] age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public char getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(char classInfo) {
        this.classInfo = classInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
