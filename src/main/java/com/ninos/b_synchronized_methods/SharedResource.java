package com.ninos.b_synchronized_methods;

import java.util.Random;

public class SharedResource {
	private int value = 0;

	// only used to print the value in main
	public int getValue() {
		return value;
	}

	public synchronized void addOne() {
		int oldValue = value;
		try {
			long timeout = Integer.toUnsignedLong(new Random().nextInt(100));
			System.out.println(Thread.currentThread().getName() + " old value: "+ value + " sleeping for: " + timeout + " ms");
			Thread.sleep(timeout);
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		value = oldValue + 1;
	}
}
