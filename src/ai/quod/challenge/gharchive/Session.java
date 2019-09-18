package ai.quod.challenge.gharchive;
import ai.quod.challenge.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class Session {
    private static final String GH_ARCHIVE_URL = "https://data.gharchive.org/%s.json.gz";
    // Eg: "https://data.gharchive.org/2015-01-01-15.json.gz"

    public void fetchArchiveData(String startDateUTC, String endDateUTC) {

        List<String> queryDates = Utils.getQueryStrings(startDateUTC, endDateUTC);

        for (String queryDate : queryDates) {
            try {
                String urlStr = String.format(GH_ARCHIVE_URL, queryDate);
                URL url = new URL(urlStr);
                System.out.print("\n");
                System.out.print(urlStr);
                File download = new File(".");
//                FileUtils.copyURLToFile(url, download);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


    }
}
