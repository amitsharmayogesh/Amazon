package com.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Amit on 28-11-2015.
 */
public class UIOperator {

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    //This will click the Elements
    public boolean Click (String strButtonName) throws Exception{
        try{
            WebElement element = GetObjectFromRepository(strButtonName);
            element.click();
            Logger.WriteLog("Clicked on Element : "+ strButtonName,true,false,true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.WriteLog("Unable to Find Element : "+strButtonName,false,true,true);
            return false;
        }
    }

    //This will select the Checkbox
    public boolean SelectCheckBox(String strButtonName, boolean binSelect) throws Exception{
        try{
            WebElement element = GetObjectFromRepository(strButtonName);

            if(element.isSelected() && binSelect == false){
                element.click();
                Logger.WriteLog("Checkbox CHECK OFF on element : "+strButtonName,true,false,true);
            }
            if(!element.isSelected() && binSelect == true){
                element.click();
                Logger.WriteLog("Checkbox CHECK ON on element : "+ strButtonName,true,false,true);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.WriteLog("Unable to Select Element : "+ strButtonName,false,true,true);
            return false;
        }
    }

    //This method will check the element is there or not
    public boolean ControlExists(String strControlName) throws Exception{
        try{
            WebElement element=  GetObjectFromRepository(strControlName);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //This will perform EnterText
    public boolean EnterText(String strEditName,String value) throws Exception{
        try{
            WebElement element = GetObjectFromRepository(strEditName);
            element.sendKeys(value);
            Logger.WriteLog("Entered Value in " + value +" in Text Box : "+strEditName,true,false,true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Logger.WriteLog("Unable to find element " + strEditName, false, true, true);
            return false;
        }
    }

    //This will get the Element's value from Object repository
    public WebElement GetObjectFromRepository(String strControlName) throws Exception{

        WebElement oElement = null;
        try{
            String objectRepositoryPath= System.getProperty("user.dir")+"\\ObjectRepository\\Object_Repository.csv";

            String line="";
            String actualLine = "";

            BufferedReader br = new BufferedReader(new FileReader(objectRepositoryPath));
            while ((line = br.readLine()) != null){
                if (line.startsWith(strControlName + ",")){
                    actualLine = line;
                }
            }
            br.close();

            String[] strArray = actualLine.split(",");
            String strId = "", strName ="", strClassName = "",strXPath = "", strLinkText="", strPartialLinkText="",strTagName="",strCSSSelector="";

            try{
                strId = strArray[2];
                strName = strArray[3];
                strClassName = strArray[4];
                strLinkText=strArray[5];
                strXPath = strArray[9];
                strCSSSelector = strArray[8];
                strTagName=strArray[7];
                strPartialLinkText=strArray[6];
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }

            if (!strId.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.id(strId));
            }
            else if (!strName.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.name(strName));
            }else if (!strLinkText.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.linkText(strLinkText));
            }else if (!strXPath.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.xpath(strXPath));
            }else if (!strCSSSelector.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.cssSelector(strCSSSelector));
            }else if (!strTagName.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.tagName(strTagName));
            }else if (!strPartialLinkText.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.partialLinkText(strPartialLinkText));
            }else if (!strClassName.isEmpty()){
                oElement = Framework.framework.webDriver.findElement(By.className(strClassName));
            }
            //rs.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return oElement;
    }

    //This will perform TAB operation
    public void tab() throws Exception{
        try{
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    //This will click enter
    public void Enter() throws Exception{
        try{
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    //This will clickon Down Arrow
    public void DownArrow() throws Exception{
        try{
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_DOWN);
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    //This will Hower on the DropDown Elements
    public void actionHower(String linkText) throws Exception{
        WebElement element = Framework.framework.webDriver.findElement(By.linkText(linkText));
        Actions action = new Actions(Framework.framework.webDriver);
        action.click(element).build().perform();;
    }

    //Ths will click on the hovering elements
    public void actionClick(String name) throws Exception{
        WebElement element = Framework.framework.webDriver.findElement(By.linkText(name));
        Actions action = new Actions(Framework.framework.webDriver);
        action.click(element).build().perform();;
    }

    //This will check whether the text is present or not on the PAGE
    public boolean isTextPresent(String textToBeVerified) throws Exception{
        try{
            if(Framework.framework.webDriver.findElement(By.xpath("//*[contains(.," + textToBeVerified + "')]")) != null){
                Logger.WriteLog("Text is Present : " + textToBeVerified ,true,false,true);
                return true;
            }
        }catch (Exception e){
            Logger.WriteLog("Text is not Present : "+textToBeVerified,false,false,true);
            return false;
        }
        return false;
    }

    //This will verify whether the element is selected or not
    public boolean isElementSelected(String elementToBeVerified) throws  Exception{
        WebElement element = GetObjectFromRepository(elementToBeVerified);
        if (element.isSelected()){
            return true;
        }else {
            return false;
        }
    }

    //This will verify whether the element is enalbled or not
    public boolean isElementEnabled(String elementToBeEnabled) throws  Exception{
        WebElement element = GetObjectFromRepository(elementToBeEnabled);
        if (element.isEnabled()== false){
            return true;
        }else {
            return false;
        }
    }

    //This will verify whether the element is displayed or not
    public boolean isElementDisplayed(String elementToBeDisplayed) throws  Exception{
        WebElement element = GetObjectFromRepository(elementToBeDisplayed);
        if (element.isDisplayed()){
            Logger.WriteLog("Element is Displayed : " + elementToBeDisplayed,true,false,true);
            return true;
        }else {
            Logger.WriteLog("Element is not  Displayed : " + elementToBeDisplayed,false,false,true);
            return false;
        }
    }

    public void Escape() throws AWTException{
        Robot robot = new Robot();
        try{
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
