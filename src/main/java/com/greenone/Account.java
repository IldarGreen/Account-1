package com.greenone;

public class Account {
	private String ID;
	private int money;

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

	public static void transfer(Account acc1, Account acc2, int amount) {
		if ((acc1.money - amount) < 0)
			return;
		acc1.withdraw(amount);
		acc2.deposit(amount);
	}

	@Override
	public String toString() {
		return  ID + " : " + money;
	}
}
