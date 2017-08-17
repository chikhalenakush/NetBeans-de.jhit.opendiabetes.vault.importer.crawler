/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jhit.opendiabetes.vault.importer.crawler;

import java.awt.AWTException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.cli.ParseException;

/**
 *
 * @author AChikhale
 */
public class DeJhitOpendiabetesVaultImporterCrawler {

    String flag, opt;

    public DeJhitOpendiabetesVaultImporterCrawler(String flag, String opt) {
        this.flag = flag;
        this.opt = opt;
    }

    public static void main(String args[]) throws SecurityException, AWTException, InterruptedException, IOException,
            ParseException, java.text.ParseException, GeneralSecurityException {

        // This is the main entry point of program.
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        SimpleDateFormat FORMATS = new SimpleDateFormat("dd-mm-HHMMSS");

        String PathForLogFile = System.getProperty("user.dir");
        System.out.println("Log will be saved at location " + PathForLogFile);
        fh = new FileHandler(PathForLogFile + "/CommandLine_" + FORMATS.format(Calendar.getInstance().getTime()) + ".log");
        logger.addHandler(fh);
        fh.setFormatter(new MyCustomFormatterForLogger());
        logger.setUseParentHandlers(false);
        logger.info("Command Line application started");
        logger.info("Log is saved at location " + System.getProperty("user.home") + "under name CommandLine");
        FlagArgumentsClass Flagargument = new FlagArgumentsClass(); // this class is called to get all the different arguments and it's values
        Flagargument.RunDifferentArguments(args, logger); // This function will run program depending on flag/argument choosen

    }

}
