package com.core;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Amit on 28-11-2015.
 */
public class Framework {

    public static Framework framework;
    public WebDriver webDriver;
    private Properties propArray;
    public UIOperator UI;
    public static boolean isTestPassorFail = true;
    String strCurrentWinfowHandle="";
    public String strCurrentScenarioName;

    /* Private Constructor fro Framework */

    private Framework() throws Exception    {

        //Load Configuration from Properties File
        if (!LoadConfig())   {

        throw new Exception(" Unable to load Configuration File ");
        }
    }

    /* Create instance of a Framework*/

    public static Framework CreateInstance(String strCurrentScenarioName) throws Exception {
        if (Framework.framework == null){

            Framework.framework = new Framework();

            //Load webdriver based onConfiguration provided
             if ( ! WebdriverWrapper.LoadWebDriver())
             {
                 throw new Exception("Unable to Load webdriver from Resources");
             }

            //Create UI Operator
            framework.strCurrentScenarioName = strCurrentScenarioName;
            framework.UI = new UIOperator();
        }
        return Framework.framework;
    }

    /* Load Config :- function to load configuration from properties
     Returns true if configuartion loaded successfully
     */

    public boolean LoadConfig(){

        propArray= new Properties();
        String strConfigPath = System.getProperty("user.dir")+"\\Configuration\\Environment.properties";
        System.out.println("Picked up Configuration path as "+ strConfigPath);

        try{
            FileInputStream oFis = new FileInputStream(strConfigPath);
            propArray.load(oFis);
            }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /* GetProperty :- To get value of property using property name
    @param strPropertyname :- property name for value which to invoke
    @return :- Property value
     */

    public String GetProperty(String strPropertyName){

        return propArray.getProperty(strPropertyName);
    }

}
