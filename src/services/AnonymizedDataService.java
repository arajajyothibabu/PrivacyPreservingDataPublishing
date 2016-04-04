package services;

import models.AnonymizedData;

import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class AnonymizedDataService {

    OracleDAO dao;

    public AnonymizedDataService(OracleDAO dao) throws Exception {
        this.dao = dao;
    }

    public boolean insertAnonymizedData(AnonymizedData data) throws Exception {
        boolean isInserted = dao.insertAnonymizedData(data);
        return isInserted;
    }

    public boolean upsertAnonymizedData(AnonymizedData data) throws Exception {
        boolean isUpdated = dao.upsertAnonymizedData(data);
        return isUpdated;
    }

    public boolean upsertAnonymizedData(ArrayList<AnonymizedData> dataList) throws Exception {
        boolean isUpdated = dao.upsertAnonymizedData(dataList);
        return isUpdated;
    }

    public ArrayList<AnonymizedData> getAnonymizedData() throws Exception {
        ArrayList<AnonymizedData> anonymousDataList = dao.getAnonymizedData();
        return anonymousDataList;
    }

    public boolean emptyData() throws Exception {
        boolean isTruncated = dao.emptyAnonymizedData();
        return isTruncated;
    }

}
