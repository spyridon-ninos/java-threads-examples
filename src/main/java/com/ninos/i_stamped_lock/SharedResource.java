package com.ninos.i_stamped_lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.concurrent.locks.StampedLock;

/*
 * reading from and writing to the value are protected by a read and a write lock
 *
 * see https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html
 */
public class SharedResource {
	private int value = 0;

	private StampedLock stampedLock = new StampedLock();

	public int getValue() {
		long stamp = stampedLock.readLock();
		try {
			System.out.println(Thread.currentThread().getName() + " value is: " + value);
			return value;
		} finally {
			stampedLock.unlock(stamp);
		}
	}

	public void addOne() {

		long stamped = stampedLock.writeLock();

		try {
			try {
				long timeout = 1000L;
				System.out.println(Thread.currentThread().getName() + " old value: " + value + " sleeping for: " + timeout + " ms");
				Thread.sleep(timeout);
				value++;
				System.out.println(Thread.currentThread().getName() + " new value: " + value );
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		} finally {
			stampedLock.unlock(stamped);
		}
	}
}
