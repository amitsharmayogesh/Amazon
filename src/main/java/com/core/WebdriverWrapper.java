package com.core;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Amit on 28-11-2015.
 */
public class WebdriverWrapper {

    public static boolean LoadWebDriver(){

        try{
            if (Framework.framework.GetProperty("BROWSERTYPE").toUpperCase().equals("CHROME"))
            {
                // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                java.lang.String strChromeDriverPath= System.getProperty("user.dir")+"\\Resources\\chromedriver.exe";
                System.setProperty("webdriver.chrome.driver",strChromeDriverPath);
                Framework.framework.webDriver = (WebDriver) new ChromeDriver();
            }
            else if(Framework.framework.GetProperty("BROWSERTYPE").toUpperCase().equals("IE")){

                // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                java.lang.String strIEDriverpath = System.getProperty("user.dir")+"\\Resources\\IEDriverServer.exe";
                Framework.framework.webDriver = (WebDriver) new InternetExplorerDriver();
            }else{
                // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                Framework.framework.webDriver = (WebDriver) new FirefoxDriver();
            }

            //Set Defaults for Webdriver
            SetPageLoadTimeout(Long.parseLong(Framework.framework.GetProperty("PAGE_LOAD_TIMEOUT")));
            SetImplicitTimeout(Long.parseLong(Framework.framework.GetProperty("IMPLICIT_TIMEOUT")));
            SetScriptTimeout(Long.parseLong(Framework.framework.GetProperty("SCRIPT_TIMEOUT")));
            return true;
        }catch (Exception e){
           System.out.println(e.getMessage());
            return  false;
        }
    }

    public static void SetPageLoadTimeout(long intValue){

        Framework.framework.webDriver.manage().timeouts().pageLoadTimeout(intValue, TimeUnit.SECONDS);
    }

    public static void SetImplicitTimeout(long intValue){

        Framework.framework.webDriver.manage().timeouts().implicitlyWait(intValue, TimeUnit.SECONDS);
    }

    public static void SetScriptTimeout(long intValue){

        Framework.framework.webDriver.manage().timeouts().setScriptTimeout(intValue, TimeUnit.SECONDS);
    }

    public static java.lang.String CaptureScreenShot(){

        //Create a file in Logs folder with current date if not exists
        DateFormat format = new SimpleDateFormat("dd_MM_YY");
        Date dateObj = new Date();
        java.lang.String strDirName = format.format(dateObj);
        File file = new File(System.getProperty("user.dir") + "\\Logs\\" + strDirName);

        if(!file.exists()){
            file.mkdir();
        }
        format = new SimpleDateFormat("HH_mm_ss");
        dateObj= new Date();
        java.lang.String strFileName = format.format(dateObj)+".png";
        java.lang.String strCompleteFileName = System.getProperty("user.dir")+"\\Logs\\" + strDirName + "\\" + strFileName;

        File scrFile = ((TakesScreenshot) Framework.framework.webDriver).getScreenshotAs(OutputType.FILE);

        try{
            FileUtils.copyFile(scrFile,new File(strCompleteFileName));
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
            strCompleteFileName = "";
        }
        java.lang.String strWebLink = "<a href=\""+ strCompleteFileName + "\" target=\"_blank\"> Click here for  SNAPSHOT </a>";
        return strWebLink;
    }


}
