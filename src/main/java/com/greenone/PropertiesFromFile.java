package com.greenone;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFromFile {

	final static Logger logger = Logger.getLogger(PropertiesFromFile.class);

	public static Prop loadProp() {
		int numberOfAccounts = 4;
		int numberOfThread = 2;

		try (InputStream input = PropertiesFromFile.class.getClassLoader().getResourceAsStream("config.properties")) {

			Properties Properties = new Properties();

			Properties.load(input);

			numberOfAccounts = Integer.parseInt(Properties.getProperty("number.of.accounts"));
			numberOfThread = Integer.parseInt(Properties.getProperty("number.of.thread"));

		} catch (Exception e) {
			logger.error("Can't fined config.properties file or load variable values.", e);
		}

		return new Prop(numberOfAccounts, numberOfThread);
	}
}

class Prop {
	private int numberOfAccounts;
	private int numberOfThread;

	public Prop(int numberOfAccounts, int numberOfThread) {
		this.numberOfAccounts = numberOfAccounts;
		this.numberOfThread = numberOfThread;
	}

	public int getNumberOfAccounts() {
		return numberOfAccounts;
	}

	public int getNumberOfThread() {
		return numberOfThread;
	}
}


