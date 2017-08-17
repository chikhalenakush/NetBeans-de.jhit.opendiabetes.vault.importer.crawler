package de.jhit.opendiabetes.vault.importer.crawler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class CheckDatesClass {

    public static String DATEFORMAT;
    Date StiatcValidStartDate;
    public Date FormatedEnteredStartDate;
    public Date FormatedEnteredEndDate;
    String DateFormatErrorMessage;
    String TodaysDateErrorMessage;

    public CheckDatesClass(String lang) throws ParseException {
        // TODO Auto-generated constructor stub

        if (lang == "de") {
            DATEFORMAT = "dd.MM.yyyy";
            StiatcValidStartDate = new SimpleDateFormat(DATEFORMAT).parse("01.01.1998");
            DateFormatErrorMessage = "Date should be in Format of DD/MM/YYYY  Example: 13.03.2017";
            TodaysDateErrorMessage = "You can only enter Start date between 01/01/1998 and Today's Date!!";
        } else if (lang == "en") {
            DATEFORMAT = "dd/MM/yyyy";
            StiatcValidStartDate = new SimpleDateFormat(DATEFORMAT).parse("01/01/1998");
            DateFormatErrorMessage = "Date should be in Format of DD/MM/YYYY  Example: 13/03/2017";
            TodaysDateErrorMessage = "You can only enter Start date between 01/01/1998 and Today's Date!!";
        }

    }

    /**
     * **********************************
     * 1. Date format should be DD/MM/YYYY 2. Start date and end date should not
     * be before 01/01/1998 3. End date should not be greater than start date 4.
     * Start date and end date shall not be greater than Today's date. 5. Start
     * date and end date should be valid
     */
    // This class and it's function satisfies above conditions for dates
    public Boolean getStratDate(String fromdate, Logger logger) throws ParseException {

        logger.info("Inside Class CheckDatesClass, Method getStratDate");
        SimpleDateFormat FormatTodayDate = new SimpleDateFormat(DATEFORMAT); // to format today's date as DD/MM/YYYY
        FormatTodayDate.setLenient(false);
        Date Todaydate = new Date();
        String todayDate = FormatTodayDate.format(Todaydate);

        //	Date StiatcValidStartDate = new SimpleDateFormat(DATEFORMAT).parse("01/01/1998");  
        /*
				 * ********
				 *  In carelink website this date is the starting date to download report
				 * **********
         */
        try {

            FormatedEnteredStartDate = new SimpleDateFormat(DATEFORMAT).parse(fromdate);
        } catch (Exception e) {
            logger.info("Start Date is not in correct format \n" + DateFormatErrorMessage);
            System.out.println(
                    "Start Date is not in correct format \n" + DateFormatErrorMessage);
            return false;
        }
        try {
            DateFormat ValidStartdate = new SimpleDateFormat(DATEFORMAT); // To validate start date
            ValidStartdate.setLenient(false);
            ValidStartdate.parse(fromdate);
            if (FormatedEnteredStartDate.before(StiatcValidStartDate)
                    || FormatedEnteredStartDate.after(new SimpleDateFormat(DATEFORMAT).parse(todayDate))) {
                System.out.println(TodaysDateErrorMessage);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.info("Start Date is not Valid");
            System.out.println("Start Date is not Valid");
            return false;

        }

    }

    public boolean getEndDate(String startDate, String EndDate, Logger logger) throws ParseException {

        // TODO Auto-generated method stub
        logger.info("Inside Class CheckDatesClass, Method getEndDate");
        Date Todaydate = new Date();
        SimpleDateFormat FormatTodayDate = new SimpleDateFormat(DATEFORMAT);
        String todayDate = FormatTodayDate.format(Todaydate);

        //	Date StiatcValidStartDate = new SimpleDateFormat(DATEFORMAT).parse("01/01/1998");
        FormatedEnteredStartDate = new SimpleDateFormat(DATEFORMAT).parse(startDate);

        try {
            FormatedEnteredEndDate = new SimpleDateFormat(DATEFORMAT).parse(EndDate);
        } catch (Exception e) {
            logger.info("End Date is not in correct format \n" + DateFormatErrorMessage);
            System.out.println(
                    "End Date is not in correct format \n" + DateFormatErrorMessage);
            return false;
        }
        try {
            DateFormat ValidEndDate = new SimpleDateFormat(DATEFORMAT); // To validate End date
            ValidEndDate.setLenient(false);
            ValidEndDate.parse(EndDate);
            if (FormatedEnteredEndDate.after(new SimpleDateFormat(DATEFORMAT).parse(todayDate))
                    || FormatedEnteredEndDate.before(StiatcValidStartDate)) {
                System.out.println(TodaysDateErrorMessage);
                return false;
            }
            if (FormatedEnteredEndDate.before(FormatedEnteredStartDate)) {
                System.out.println("End Date cannot be earlier than Start date");
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.info("End Date is not Valid");
            System.out.println("End Date is not Valid");
            return false;
        }
    }

}
