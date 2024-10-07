package org.NAGP.Assignment.Rest.Booker.Hooks;

import org.NAGP.Assignment.Rest.Booker.ExcelFileIO;
import org.NAGP.Assignment.Rest.Booker.Utils.Logs.Log;
import org.NAGP.Assignment.Rest.POJO.Request.Login;
import org.NAGP.Assignment.Rest.Utilities.apitest.actions.Helper;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class hooks
{
    protected Login login;
    protected static Helper helper;
    static protected Properties properties=new Properties();
    static FileInputStream fis = null;
    public static ExcelFileIO reader = null;


    static {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String strProjectLoc = System.getProperty("user.dir");
            reader = new ExcelFileIO( STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources\{File.separator}testData\{File.separator}BookingData.xlsx");
        }
        catch(Exception e) {
            Log.error(e.getMessage());
        }
    }

    public static void setProperties() throws IOException {
        String strProjectLoc = System.getProperty("user.dir");
        FileInputStream fileInputStream= new FileInputStream(STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources\{File.separator}properties\{File.separator}execution.properties");
        helper=new Helper();
        helper.set_path(STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources\{File.separator}properties\{File.separator}execution.properties");
        properties.load(fileInputStream);
    }

    @BeforeMethod
    public void beforeMethod() throws IOException {
         login = new Login();
    }


}
