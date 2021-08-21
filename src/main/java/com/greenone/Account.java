package com.greenone;

import java.util.concurrent.locks.ReentrantLock;

public class Account extends ReentrantLock {
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
		counter2++;
	}

	public void withdraw(int amount) {
		money -= amount;
		counter1++;
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

	@Override
	public String toString() {
		return  ID + " : " + money;
	}

	public static void transfer(Account acc1, Account acc2, int amount) {

		acc1.withdraw(amount);
		System.out.println("списание " + acc1.money + " - " + amount);

		acc2.deposit(amount);
		System.out.println("депозит " + acc2.money + " - " + amount);


//		System.out.println("оплата не прошла-----------" + acc1.money + " - " + amount);
		counter3++;

	}
}
