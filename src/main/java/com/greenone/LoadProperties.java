package com.greenone;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {



	public static void main(String[] args) {
		int numberOfAccounts;
		int numberOfThread;



//		try (InputStream input = new FileInputStream("D:\\java\\projects\\Новая папка\\Account-1\\src\\main\\resources\\config.properties")) {
//
//			Properties prop = new Properties();
//
//			prop.load(input);
//		-------------------------------------
		try (InputStream input = LoadProperties.class.getClassLoader().getResourceAsStream("config.properties")) {

			Properties prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}

			prop.load(input);

			numberOfAccounts = Integer.parseInt(prop.getProperty("number.of.accounts"));
			numberOfThread = Integer.parseInt(prop.getProperty("number.of.thread"));

			System.out.println(numberOfAccounts);
			System.out.println(numberOfThread);



		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
