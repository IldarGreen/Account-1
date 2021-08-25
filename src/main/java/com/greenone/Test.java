package com.greenone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import static com.greenone.PropertiesFromFile.loadProp;
import static com.greenone.PropertiesFromFile.logger;

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

		//Раздаем задачи для executorService
		for (int i = 0; i < 30; i++) {
			while (true) {
				a1 = accounts.get(random.nextInt(prop.getNumberOfAccounts()));
				a2 = accounts.get(random.nextInt(prop.getNumberOfAccounts()));

				//Учитывая рандомный выбор аккаунтов, проверяем что не выбран один и тот же	аккаунт
				if (a1 != a2) {
					executorService.submit(new Transaction(i, a1, a2));
					break;
				}
			}
		}

		executorService.shutdown();

		executorService.awaitTermination(10000, TimeUnit.SECONDS);

		//Выводим общую информацию после всех транзакций
		logger.info("Number of successful transaction: " + Account.getCounter1());

		int sum = 0;
		for (Account account : accounts) {
			logger.info(account.getID() + " = " + account.getMoney());
			sum += account.getMoney();
		}

		logger.info("Sum for all accounts  = " + sum);
	}
}

class Transaction implements Runnable {
	private final int id;
	private Account acc1;
	private Account acc2;

	final static Logger logger = Logger.getLogger(Test.class);

	public Transaction(int id, Account acc1, Account acc2) {
		this.id = id;
		this.acc1 = acc1;
		this.acc2 = acc2;
	}

	@Override
	public void run() {
		Random random = new Random();
		int amount;

		while (true) {
			amount = random.nextInt(10000);

			try {
				if (acc1.getMoney() == 0) {
					logger.info("Can't make withdraw from " + acc1.getID() + ", the balance is zero");
					break;
				}
				if (acc1.getMoney() > amount) {
					Account.transfer(acc1, acc2, amount);
					logger.info("Transfer " + id + " from " + acc1.getID() + " to " + acc2.getID()
							+ " completed. Result: " + acc1.getID() + " = " + acc1.getMoney()
							+ "; " + acc2.getID() + " = " + acc2.getMoney());
				} else {
					logger.info("Transfer from " + acc1.getID() + " to " + acc2.getID() +
							" not completed. Reason: not enough balance to withdraw");
				}
			} finally {
				break;
			}
		}

		try {
			java.lang.Thread.sleep(1000 + random.nextInt(1000)); //Поток спит от 1000 до 2000мс
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Something go wrong: ", e);
		}
	}
}