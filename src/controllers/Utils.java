package controllers;

import models.AnonymizedData;
import models.RawData;

import java.sql.ResultSet;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class Utils {

    public static RawData makeRawData(ResultSet data) throws Exception {
        return new RawData(data.getInt(1), data.getString(2), data.getInt(3),data.getString(4),data.getString(5));
    }

    public static AnonymizedData makeAnonymizedData(ResultSet data) throws Exception {
        return new AnonymizedData(data.getString(1), data.getString(2), data.getString(3),data.getString(4),data.getInt(5));
    }

}
