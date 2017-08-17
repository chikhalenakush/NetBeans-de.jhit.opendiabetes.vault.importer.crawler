package de.jhit.opendiabetes.vault.importer.crawler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CrawlerClass {

    public static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public void generateDocument(Map<String, String> loginCookies, String startDate, String endDate, String UserworkingDirecotry,
            Logger logger, String configFilePath) {

        // this function checks if login is correct and entered dates are
        // correct, it download CSV
        logger.info("Inside class CrawlerClass");
        try {
            Connection.Response ReportDocument = Jsoup
                    .connect("https://carelink.minimed.eu/patient/main/selectCSV.do?t=11?t=11?t=11?t=11").timeout(60000)
                    /*.ignoreContentType(false).userAgent(UserAgent)*/.cookies(loginCookies)
                    /*.header("Content-Type", "text/csv; charset=UTF-8")
					.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,;q=0.8")
					.header("Content-length", "101").data("org.apache.struts.taglib.html.TOKEN", "53f325519c62adcec1a3128908017474")*/
                    .data("report", "11").data("listSeparator", ",")
                    //.data("customerID","50577452") // customer Id can be
                    // optional.
                    .data("datePicker2", startDate) // start date
                    .data("datePicker1", endDate) // End date
                    /*.header("X-Requested-With", "XMLHttpRequest")*/.method(Connection.Method.GET).execute();

            String userHome = UserworkingDirecotry;
            String outputFolder = userHome + File.separator + "careLink-Export";

            PrintWriter pw1 = new PrintWriter(new File(outputFolder + (new Date().getTime()) + ".csv"));
            pw1.write(ReportDocument.body());
            pw1.close();
            System.out.println("Export Sucessfull!");
            System.out.println("File will be saved to location " + userHome + " with name: " + "\"careLink-Export"
                    + (new Date().getTime()) + ".csv\"");
            logger.info("Export Sucessfull!");

        } catch (IOException e) {
            logger.info("There is an issue Downloading File. Please try again after some time!!");
            System.out.println(
                    "There is an issue Downloading File. Please try checking output path or try again after some time!!");

        }

    }

}
