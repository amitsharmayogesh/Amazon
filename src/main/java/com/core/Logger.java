package com.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amit on 28-11-2015.
 */
public class Logger {

    public static String strLogPath;
    public static boolean isInitiated = false;
    private static List<String> logList = null;

    public static void Initialize(String strFeatureName, String strScenarioName) throws IOException {
        if (!isInitiated) {
            DateFormat format = new SimpleDateFormat("dd_HH_mm_ss");
            Date dateObj = new Date();
            String strFieName = "TestResult" + "_" + format.format(dateObj) + ".html";
            strLogPath = System.getProperty("user.dir") + "\\Logs\\" + strFieName;
            File file = new File(System.getProperty("user.dir" + "\\resources\\html_template.txt"));
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String sTemplateText = new String(data, "UTF-8");
            FileWriter logger = new FileWriter(strLogPath, false);
            sTemplateText = sTemplateText.replace("TEMPLATE_SCENARIO_NAME", strScenarioName);
            sTemplateText = sTemplateText.replace("TEMPLATE_FEATURE_NAME", strFeatureName);
            logger.write(sTemplateText);
            logger.close();
            logList = new ArrayList<String>();
            isInitiated = true;
        } else {
            FileWriter logger = new FileWriter(strLogPath, true);
            String completeLog = "<liclass=\"level suite failed open\"><span>Scenario : TEMPLATE_SCENARION_NAME  <!---RESULT_FINAL---->  </span><ul>";
            completeLog = completeLog.replace("TEMPLATE_SCENRIO_NAME", strScenarioName);
            logger.write(completeLog);
            logger.close();
        }
    }

    public static void WriteScenarioFooter(String strScenarioName) {
        try {
            File file = new File(strLogPath);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String completeLog = new String(data, "UTF-8");

            if (Framework.isTestPassorFail) {
                completeLog = completeLog.replace("<!--RESULT_FINAL-->", "<em class=\"status\"><b><font color='green'>PASS</font></b></em>");
            } else {
                completeLog = completeLog.replace("<!--RESULT_FINAL-->", "<em class=\"status\"><b><font color='red'>FAIL</font></b></em>");
            }
            FileWriter logger = new FileWriter(strLogPath, false);
            completeLog = completeLog + "<!--END--> </li>";
            logger.write(completeLog);
            logger.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void WriteFooter() throws IOException {

        //LOgger.WriteLog("",true,false,false)'

        File file = new File(strLogPath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String completeLog = new String(data, "UTF-8");

        if (Framework.isTestPassorFail) {
            completeLog = completeLog.replace("<!--RESULT_FINAL-->", "<em class=\"status\"><b><font color='green'>PASS</font></b></em>");
        } else {
            completeLog = completeLog.replace("<!--RESULT_FINAL-->", "<em class=\"status\"><b><font color='red'>FAIL</font></b></em>");
        }
        FileWriter logger = new FileWriter(strLogPath, false);
        completeLog = completeLog + "</ul></li></div><div id=\"footer\"></div></body></html>";
        logger.write(completeLog);
        logger.close();
    }

    public static void WriteLog(String strLogContent, boolean isPassOrFail, boolean isSnapshot, boolean test) throws Exception {

        WriteLog(strLogContent, isPassOrFail, isSnapshot, test);
    }

    public static void WriteLog(String strLogContent, boolean isPassOrFail, boolean isSnapshot) throws Exception {

        FileWriter logger = null;

        try {
            logger = new FileWriter(strLogPath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isSnapshot) {
            strLogContent = strLogContent + WebdriverWrapper.CaptureScreenShot();
        }

        if (isPassOrFail) {
            String strTempLog = ("<font color ='green'> [Pass] STEP_RESULT </font><br>").replace("STEP_RESULT", strLogContent);
            try {
                logger.write(strTempLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[PASS]: " + strLogContent);
        } else {
            String strTempLog = ("<font color ='red'> [Fail] STEP_RESULT </font><br>").replace("STEP_RESULT", strLogContent);
            try {
                logger.write(strTempLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[FAIL]: " + strLogContent);
        }
        try{
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(!isPassOrFail){
            Framework.isTestPassorFail = false;
            throw new Exception("Scenario is failed. Please check the Logs");
        }
    }
}
