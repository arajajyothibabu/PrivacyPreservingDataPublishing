import controllers.PPDP;
import controllers.classification.ID3;
import models.AnonymizedData;
import models.RawData;
import services.AnonymizedDataService;
import services.RawDataService;

import java.util.ArrayList;

/**
 * Created by Araja Jyothi Babu on 28-Mar-16.
 */
public class Main {

    public static void main(String args[])  throws Exception {

        System.out.println("Application Started..!");
        PPDP ppdp = new PPDP(new RawDataService(), new AnonymizedDataService()); //runtime dependency injection
        ArrayList<AnonymizedData> anonymizedDataList = ppdp.generateAnonymizedData();
        ID3 id3 = new ID3(anonymizedDataList);
        id3.processData();
    }

}