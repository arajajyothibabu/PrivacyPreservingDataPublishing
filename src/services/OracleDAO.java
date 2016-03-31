package services;

import controllers.Utils;
import models.AnonymizedData;
import models.RawData;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class OracleDAO {

    public static boolean insertRawData(RawData rawData) throws Exception{
        Connection connection = DB.openConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO raw VALUES(?,?,?,?,?)");
        statement.setInt(1, rawData.getId());
        statement.setString(2, rawData.getSex());
        statement.setInt(3, rawData.getAge());
        statement.setString(4, rawData.getDiseaseCode());
        statement.setString(5,rawData.getClassInfo());
        boolean isInserted = statement.execute();
        connection.close();
        return isInserted;
    }

    public static boolean insertAnonymizedData(AnonymizedData data) throws Exception {
        Connection connection = DB.openConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO anonymous VALUES(?,?,?,?,?)");
        statement.setString(1, data.getAge());
        statement.setString(2, data.getSex());
        statement.setString(3, data.getDiseaseCode());
        statement.setString(4,data.getClassInfo());
        statement.setInt(5, data.getCount());
        boolean isInserted = statement.execute();
        connection.close();
        return isInserted;
    }

    public static ArrayList<RawData> getRawData() throws Exception {
        Connection connection = DB.openConnection();
        Statement statement = connection.createStatement();
        ResultSet dataFromDB = statement.executeQuery("SELECT * from raw");
        ArrayList<RawData> rawDataList = new ArrayList();
        while(dataFromDB.next()){
            rawDataList.add(Utils.makeRawData(dataFromDB));
        }
        connection.close();
        return rawDataList;
    }

    public static ArrayList<AnonymizedData> getAnonymizedData() throws Exception {
        Connection connection = DB.openConnection();
        Statement statement = connection.createStatement();
        ResultSet dataFromDB = statement.executeQuery("SELECT * from anonymous");
        ArrayList<AnonymizedData> anonymousDataList = new ArrayList();
        while(dataFromDB.next()){
            anonymousDataList.add(Utils.makeAnonymizedData(dataFromDB));
        }
        connection.close();
        return anonymousDataList;
    }

    public static String getDiseaseCodes() throws Exception {
        Connection connection = DB.openConnection();
        Statement statement = connection.createStatement();
        ResultSet codeFromDB = statement.executeQuery("SELECT DISTINCT(code) from raw");
        StringBuilder diseaseCodes = new StringBuilder("");
        while(codeFromDB.next()){
            diseaseCodes.append(codeFromDB.getString(1) + ",");
        }
        connection.close();
        return diseaseCodes.toString();
    }

    public static int[] getMinMaxAges() throws Exception {
        Connection connection = DB.openConnection();
        Statement statement = connection.createStatement();
        ResultSet ageFromDB = statement.executeQuery("SELECT MIN(age), MAX(age) from raw");
        int[] minMaxAge = new int[2];
        if(ageFromDB.next()){
            minMaxAge[0] = ageFromDB.getInt(1);
            minMaxAge[1] = ageFromDB.getInt(2);
        }
        connection.close();
        return minMaxAge;
    }

}
