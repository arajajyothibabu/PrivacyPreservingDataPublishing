package models;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class AnonymizedData {

    private String age; //just range of two values (15- 60)
    private String sex;
    private String diseaseCode;
    private String classInfo;
    private int count;

    public AnonymizedData() {
    }

    public AnonymizedData(String age, String sex, String diseaseCode, String classInfo, int count) {
        this.age = age;
        this.sex = sex;
        this.diseaseCode = diseaseCode;
        this.classInfo = classInfo;
        this.count = count;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }

    public void setAge(int[] age){
        this.age = makeAge(age);
    }

    public static String makeAge(int[] age) {
        return "[" + age[0] + "-" + age[1] + ")";
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

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
