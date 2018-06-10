package com.ninos.g_read_write_lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/*
 * reading from and writing to the value are protected by a read and a write lock
 *
 * see https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html
 */
public class SharedResource {
	private int value = 0;

	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	private ReadLock readLock = reentrantReadWriteLock.readLock();
	private WriteLock writeLock = reentrantReadWriteLock.writeLock();

	public int getValue() {
		readLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " value is: " + value);
			return value;
		} finally {
			readLock.unlock();
		}
	}

	public void addOne() {

		writeLock.lock();

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
			writeLock.unlock();
		}
	}
}
