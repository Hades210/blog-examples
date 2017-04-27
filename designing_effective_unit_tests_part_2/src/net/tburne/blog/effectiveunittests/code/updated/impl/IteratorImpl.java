package net.tburne.blog.effectiveunittests.code.updated.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;

import net.tburne.blog.effectiveunittests.code.updated.types.IIterator;

public class IteratorImpl implements IIterator, Closeable {

	private Scanner mScanner;
	
	public IteratorImpl(InputStream stream) {
		mScanner = new Scanner(new InputStreamReader(stream));
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			
			@Override
			public String next() {
				return mScanner.nextLine();
			}
			
			@Override
			public boolean hasNext() {
				return mScanner.hasNextLine();
			}
		};
	}

	
	@Override
	public void close() throws IOException {
		mScanner.close();
	}

}
