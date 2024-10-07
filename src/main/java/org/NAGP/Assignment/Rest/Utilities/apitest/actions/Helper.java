package org.NAGP.Assignment.Rest.Utilities.apitest.actions;


import java.io.*;
import java.util.*;




public class Helper {
	String  path;

	public String loadProperties(String property) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(path);
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return prop.getProperty(property);
	}

	public  Helper set_path(String path2) {
		path = path2;
		return this;
	}
	


}