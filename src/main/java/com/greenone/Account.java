package com.greenone;

public class Account {
	private String ID;
	private int money;

	private static int counter1;
	private static int counter2;
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

	public static int getCounter1() {
		return counter1;
	}

	public static int getCounter2() {
		return counter2;
	}

	public static int getCounter3() {
		return counter3;
	}

	public static void transfer(Account acc1, Account acc2, int amount) {

		if ((acc1.money - amount) < 0) {
			System.out.println("оплата не прошла-----------");
			return;
		}

		synchronized (acc1) {
			synchronized (acc2) {
				acc1.withdraw(amount);
				counter1++;
				acc2.deposit(amount);
				counter2++;
			}
		}

		counter3++;
	}

	@Override
	public String toString() {
		return  ID + " : " + money;
	}
}
