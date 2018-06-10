package com.ninos.a_unprotected;

import java.util.Random;

/*
 * reading from and writing to the value is not protected
 * by any synchronisation mechanism
 */
public class SharedResource {
	private int value = 0;

	// only used to print the value in main
	public int getValue() {
		return value;
	}

	public void addOne() {
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
