package com.greenone;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
	}
}

class Transfer implements Runnable {
	private int id;

	public Transfer(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000 + (int) (Math.random() * 1000)); //Поток спит от 1000 до 2000мс
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();
	}
}