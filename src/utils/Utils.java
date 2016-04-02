package utils;

import models.AnonymizedData;
import models.RawData;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public static String concatStrings(Set<String> setOfStrings){
        StringBuilder concatedString = new StringBuilder("");
        int size = setOfStrings.size();
        for(String string : setOfStrings){
            size--;
            concatedString.append(string);
            if(size > 0)
                concatedString.append(",");
        }
        return concatedString.toString();
    }

    public static ArrayList<String> getUniqueList(ArrayList<String> list){
        ArrayList<String> uniqueList = new ArrayList();
        SortedSet<String> set = new TreeSet<String>(list);
        for(String uniqueValue : set){
            uniqueList.add(uniqueValue);
        }
        return uniqueList;
    }

}
