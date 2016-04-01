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
public class AnonymizedDataService {

    public static boolean insertAnonymizedData(AnonymizedData data) throws Exception {
        boolean isInserted = OracleDAO.insertAnonymizedData(data);
        return isInserted;
    }

    public static boolean upsertAnonymizedData(AnonymizedData data) throws Exception {
        boolean isUpdated = OracleDAO.upsertAnonymizedData(data);
        return isUpdated;
    }

    public static ArrayList<AnonymizedData> getAnonymizedData() throws Exception {
        ArrayList<AnonymizedData> anonymousDataList = OracleDAO.getAnonymizedData();
        return anonymousDataList;
    }

}
