package services;

import utils.Utils;
import models.AnonymizedData;
import models.RawData;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class OracleDAO {

    Connection connection;
    public OracleDAO(DB db) throws Exception{
        this.connection = db.openConnection();
    }

    public boolean insertRawData(RawData rawData) throws Exception{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO rawdata VALUES(?,?,?,?,?)");
        statement.setInt(1, rawData.getId());
        statement.setString(2, rawData.getSex());
        statement.setInt(3, rawData.getAge());
        statement.setString(4, rawData.getDiseaseCode());
        statement.setString(5,rawData.getClassInfo());
        boolean isInserted = statement.execute();
        return isInserted;
    }

    public boolean insertAnonymizedData(AnonymizedData data) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO anonymous VALUES(?,?,?,?,?)");
        statement.setString(1, data.getAge());
        statement.setString(2, data.getSex());
        statement.setString(3, data.getDiseaseCode());
        statement.setString(4,data.getClassInfo());
        statement.setInt(5, data.getCount());
        boolean isInserted = statement.execute();
        return isInserted;
    }

    public boolean insertAnonymizedData(ArrayList<AnonymizedData> dataList) throws Exception {
        int insertedCount = 0;
        boolean isInserted;
        for(AnonymizedData data : dataList) {
            isInserted = false;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO anonymous VALUES(?,?,?,?,?)");
            statement.setString(1, data.getAge());
            statement.setString(2, data.getSex());
            statement.setString(3, data.getDiseaseCode());
            statement.setString(4, data.getClassInfo());
            statement.setInt(5, data.getCount());
            isInserted = statement.execute();
            if(isInserted)
                insertedCount++;
        }
        return insertedCount == dataList.size();
    }

    public boolean upsertAnonymizedData(AnonymizedData data) throws Exception {
        //check if already exists
        Statement checkingStatement = connection.createStatement();
        ResultSet existedData = checkingStatement.executeQuery("SELECT * FROM anonymous WHERE age = '" + data.getAge() + "' AND sex = '" + data.getSex() + "' AND code = '" + data.getDiseaseCode() + "' AND class = '" + data.getClassInfo() + "'");
        boolean isUpdated = false;
        if(existedData.next()){
            Statement updateStatement = connection.createStatement();
            int updated = updateStatement.executeUpdate("UPDATE anonymous SET count = '" + (existedData.getInt("count") + 1) + "' WHERE WHERE age = '" + data.getAge() + "' AND sex = '" + data.getSex() + "' AND code = '" + data.getDiseaseCode() + "' AND class = '" + data.getClassInfo() + "'");
            if(updated > 0)
                isUpdated = true;
        }else {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO anonymous VALUES(?,?,?,?,?)");
            statement.setString(1, data.getAge());
            statement.setString(2, data.getSex());
            statement.setString(3, data.getDiseaseCode());
            statement.setString(4, data.getClassInfo());
            statement.setInt(5, data.getCount());
            isUpdated = statement.execute();
        }
        return isUpdated;
    }

    public boolean upsertAnonymizedData(ArrayList<AnonymizedData> dataList) throws Exception {
        Statement checkingStatement = connection.createStatement();
        Statement updateStatement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO anonymous VALUES(?,?,?,?,?)");
        ResultSet existedData;
        boolean isUpdated;
        int updatedCount = 0, updated = 0;
        for(AnonymizedData data : dataList) {
            //check if already exists
            isUpdated = false;
            existedData = checkingStatement.executeQuery("SELECT * FROM anonymous WHERE age = '" + data.getAge() + "' AND sex = '" + data.getSex() + "' AND code = '" + data.getDiseaseCode() + "' AND class = '" + data.getClassInfo() + "'");
            if (existedData.next()) {
                updated = updateStatement.executeUpdate("UPDATE anonymous SET count = '" + (existedData.getInt("count") + 1) + "' WHERE age = '" + data.getAge() + "' AND sex = '" + data.getSex() + "' AND code = '" + data.getDiseaseCode() + "' AND class = '" + data.getClassInfo() + "'");
                if (updated > 0)
                    isUpdated = true;
            } else {
                statement.setString(1, data.getAge());
                statement.setString(2, data.getSex());
                statement.setString(3, data.getDiseaseCode());
                statement.setString(4, data.getClassInfo());
                statement.setInt(5, data.getCount());
                isUpdated = statement.execute();
            }
            if(isUpdated)
                updatedCount++;
        }
        return updatedCount == dataList.size();
    }

    public ArrayList<RawData> getRawData() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet dataFromDB = statement.executeQuery("SELECT * from rawdata");
        ArrayList<RawData> rawDataList = new ArrayList();
        while(dataFromDB.next()){
            rawDataList.add(Utils.makeRawData(dataFromDB));
        }
        return rawDataList;
    }

    public ArrayList<AnonymizedData> getAnonymizedData() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet dataFromDB = statement.executeQuery("SELECT * from anonymous");
        ArrayList<AnonymizedData> anonymousDataList = new ArrayList();
        while(dataFromDB.next()){
            anonymousDataList.add(Utils.makeAnonymizedData(dataFromDB));
        }
        return anonymousDataList;
    }

    public String getDiseaseCodes() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet codeFromDB = statement.executeQuery("SELECT DISTINCT(code) from rawdata");
        StringBuilder diseaseCodes = new StringBuilder("");
        while(codeFromDB.next()){
            diseaseCodes.append(codeFromDB.getString(1) + ",");
        }
        return diseaseCodes.toString();
    }

    public int[] getMinMaxAges() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet ageFromDB = statement.executeQuery("SELECT MIN(age), MAX(age) from rawdata");
        int[] minMaxAge = new int[2];
        if(ageFromDB.next()){
            minMaxAge[0] = ageFromDB.getInt(1);
            minMaxAge[1] = ageFromDB.getInt(2);
        }
        return minMaxAge;
    }

    public boolean emptyAnonymizedData() throws Exception{
        Statement statement = connection.createStatement();
        boolean isTruncated = statement.execute("TRUNCATE TABLE anonymous");
        return isTruncated;
    }

    public boolean emptyRawData() throws Exception{
        Statement statement = connection.createStatement();
        boolean isTruncated = statement.execute("TRUNCATE TABLE rawdata");
        return isTruncated;
    }

}
