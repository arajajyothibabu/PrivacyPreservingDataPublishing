package services;

import controllers.Utils;
import models.AnonymizedData;
import models.RawData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class RawDataService {

    public static boolean insertRawData(RawData rawData) throws Exception{
        boolean isInserted = OracleDAO.insertRawData(rawData);
        return isInserted;
    }

    public static ArrayList<RawData> getRawData() throws Exception {
        ArrayList<RawData> rawDataList = OracleDAO.getRawData();
        return rawDataList;
    }

    public static String getDiseaseCodes() throws Exception {
        String diseaseCodes = OracleDAO.getDiseaseCodes();
        return diseaseCodes;
    }

    public static int[] getMinMaxAges() throws Exception {
        int[] minMaxAge = OracleDAO.getMinMaxAges();
        return minMaxAge;
    }

}
