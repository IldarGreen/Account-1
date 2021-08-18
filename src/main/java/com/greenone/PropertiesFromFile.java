package com.greenone;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFromFile {
	public static void main(String[] args) {
		Prop prop = loadProp();

		System.out.println(prop.getNumberOfAccounts() + ", " + prop.getNumberOfThread());
	}

	public static Prop loadProp() {
		int numberOfAccounts = 1;
		int numberOfThread = 2;

		try (InputStream input = LoadProperties.class.getClassLoader().getResourceAsStream("config.properties")) {

			Properties Properties = new Properties();

			Properties.load(input);

			return new Prop(Integer.parseInt(Properties.getProperty("number.of.accounts")),
					Integer.parseInt(Properties.getProperty("number.of.thread")));


		} catch (IOException e) {
			e.printStackTrace();
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

	public void setNumberOfAccounts(int numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}

	public int getNumberOfThread() {
		return numberOfThread;
	}

	public void setNumberOfThread(int numberOfThread) {
		this.numberOfThread = numberOfThread;
	}
}


