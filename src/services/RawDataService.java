package services;

import models.RawData;

import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class RawDataService {

    OracleDAO dao;

    public RawDataService(OracleDAO dao) throws Exception {
        this.dao = dao;
    }
    
    public boolean insertRawData(RawData rawData) throws Exception{
        boolean isInserted = dao.insertRawData(rawData);
        return isInserted;
    }

    public ArrayList<RawData> getRawData() throws Exception {
        ArrayList<RawData> rawDataList = dao.getRawData();
        return rawDataList;
    }

    public String getDiseaseCodes() throws Exception {
        String diseaseCodes = dao.getDiseaseCodes();
        return diseaseCodes;
    }

    public int[] getMinMaxAges() throws Exception {
        int[] minMaxAge = dao.getMinMaxAges();
        return minMaxAge;
    }

}
