package com.greenone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.greenone.PropertiesFromFile.loadProp;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		Prop prop = loadProp();
		List<Account> accounts = new ArrayList<>();
		Random random = new Random();

		//Создаем аккаунты в количестве полученном из файла
		for (int i = 0; i < prop.getNumberOfAccounts(); i++)
			accounts.add(new Account(Integer.toString(i)));

		//Создаем пул потоков в количестве полученном из файла и раздаем им задачи(трансфер)
		ExecutorService executorService = Executors.newFixedThreadPool(prop.getNumberOfThread());

		Account a1;
		Account a2;
		int amount;

		for (int i = 0; i < 30; i++) {

			while (true) {
				a1 = accounts.get(random.nextInt(prop.getNumberOfAccounts()));
				a2 = accounts.get(random.nextInt(prop.getNumberOfAccounts()));
				if (a1 != a2) {

					break;
				}
			}

			amount = 5000;

//			while (true) {
//				amount = random.nextInt(10000);
//				if ((a1.getMoney() > amount))
//					break;
//			}

			executorService.submit(new Transaction(i, a1,a2, amount));
		}

		executorService.shutdown();

		executorService.awaitTermination(5000, TimeUnit.SECONDS);

		int sum = 0;
		for (Account account: accounts) {
			System.out.println(account.getID() + " = " + account.getMoney());
			sum += account.getMoney();

		}

		System.out.println("Sum = " + sum);
		System.out.println(Account.getCounter1());
		System.out.println(Account.getCounter2());
		System.out.println(Account.getCounter3());
	}
}

class Transaction implements Runnable {
	private int id;
	private Account acc1;
	private Account acc2;
	private int amount;

	public Transaction(int id, Account acc1, Account acc2, int amount) {
		this.id = id;
		this.acc1 = acc1;
		this.acc2 = acc2;
		this.amount = amount;
	}

	private void takeLocks (Account acc1, Account acc2) {
		boolean acc1LockTaken = false;
		boolean acc2LockTaken = false;

		while (true) {
			try {
				acc1LockTaken = acc1.tryLock();
				acc2LockTaken = acc2.tryLock();
			} finally {
				if (acc1LockTaken && acc2LockTaken) {
					return;
				}

				if (acc1LockTaken) {
					acc1.unlock();
				}
				if (acc2LockTaken) {
					acc2.unlock();
				}
			}

			try {
				Thread.sleep(1); //Даем время потокам отдать локи
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run() {
		Random random = new Random();

		takeLocks(acc1, acc2);

		try {
			if (acc1.getMoney() >= amount) {
				Account.transfer(acc1, acc2, amount);
			}
		} finally {
			acc1.unlock();
			acc2.unlock();
		}

		try {
			java.lang.Thread.sleep(1 + random.nextInt(1)); //Поток спит от 1000 до 2000мс
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Transfer " + id + " completed");
	}
}