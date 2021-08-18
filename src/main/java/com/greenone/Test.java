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

		for (int i = 0; i < 30; i++)
			executorService.submit(new Work(i, accounts.get(random.nextInt(prop.getNumberOfAccounts())),
												accounts.get(random.nextInt(prop.getNumberOfAccounts()))));

		executorService.shutdown();

		executorService.awaitTermination(5, TimeUnit.MINUTES);

		int sum = 0;
		for (Account account: accounts) {
			System.out.println(account.getID() + " = " + account.getMoney());
			sum += account.getMoney();

		}

		System.out.println("Sum = " + sum);
	}
}

class Work implements Runnable {
	private int id;
	private Account acc1;
	private Account acc2;

	public Work(int id, Account acc1, Account acc2) {
		this.id = id;
		this.acc1 = acc1;
		this.acc2 = acc2;
	}

	@Override
	public void run() {
		Random random = new Random();

		try {
			java.lang.Thread.sleep(100 + random.nextInt(100)); //Поток спит от 1000 до 2000мс

			for (int i = 0; i < 10; i++) {
				Account.transfer(acc1, acc2, random.nextInt(10000));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Transfer " + id + " completed");
	}
}