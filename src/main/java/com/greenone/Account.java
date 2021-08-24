package com.greenone;

public class Account {
	private String ID;
	private int money;

	private static int counter3;


	public Account(String ID) {
		this.ID = "Account_" + ID;
		this.money = 10000;
	}

	public void deposit(int amount) {
		money += amount;
	}

	public void withdraw(int amount) {
		money -= amount;
	}

	public int getMoney() {
		return money;
	}

	public String getID() {
		return ID;
	}

	public static int getCounter3() {
		return counter3;
	}

	@Override
	public String toString() {
		return  ID + " : " + money;
	}

	public static void transfer(Account acc1, Account acc2, int amount) {

		acc1.withdraw(amount);
		acc2.deposit(amount);

		counter3++;
	}
}
