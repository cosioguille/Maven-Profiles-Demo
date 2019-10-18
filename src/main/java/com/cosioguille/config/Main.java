package com.cosioguille.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import java.net.URL;

public class Main {

	public static void main(String[] args) {
		
		Properties prop = loadPropertiesFile("application.properties");
		
		prop.forEach((k, v) -> System.out.println("\t"+k + " : " + v));
		
		checkManifest();
		
	}
	
	public static Properties loadPropertiesFile(String filePath) {
		
		Properties prop = new Properties();
		
		try (InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream(filePath)) {
			prop.load(resourceAsStream);
		} catch (IOException e) {
			System.err.println("Unable to load properties file : " + filePath);
		}
		
		return prop;
	}	
	
	public static void checkManifest() {
		
		Enumeration<URL> resources;
		
		try {
			resources = Main.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
			
			while (resources.hasMoreElements()) {
				 Manifest manifest = new Manifest(resources.nextElement().openStream());
				 for (Iterator it = manifest.getMainAttributes().keySet().iterator(); it.hasNext();) {
				      Attributes.Name attrName = (Attributes.Name) it.next();

				      String attrValue = manifest.getMainAttributes().getValue(attrName);
				      System.out.println(String.format("%s: %s", attrName, attrValue));
				    }
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
}