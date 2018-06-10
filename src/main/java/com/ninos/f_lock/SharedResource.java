package com.ninos.f_lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * reading from and writing to the value are protected by a lock
 */
public class SharedResource {
	private int value = 0;

	// only used to print the value in main
	public int getValue() {
		return value;
	}

	private Lock lock = new ReentrantLock();

	public void addOne() {
		lock.lock();

		try {

			int oldValue = value;

			try {
				long timeout = Integer.toUnsignedLong(new Random().nextInt(100));
				System.out.println(Thread.currentThread().getName() + " old value: " + value + " sleeping for: " + timeout + " ms");
				Thread.sleep(timeout);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			value = oldValue + 1;

		} finally {
			lock.unlock();
		}
	}
}
