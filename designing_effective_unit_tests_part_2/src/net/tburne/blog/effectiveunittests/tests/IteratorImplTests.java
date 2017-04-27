package net.tburne.blog.effectiveunittests.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import net.tburne.blog.effectiveunittests.code.updated.impl.IteratorImpl;
import net.tburne.blog.effectiveunittests.tests.helpers.FailingInputStream;

public class IteratorImplTests {

	/**
	 * Basic success case
	 * @throws IOException
	 */
	@Test
	public void testSuccess() throws IOException {
		try(ByteArrayInputStream stream = new ByteArrayInputStream("aaaa\nbbbb\ncccc".getBytes())){
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertArrayEquals("aaaa\nbbbb\ncccc".split("[\n]"), actual.toArray(new String[0]));
			}	
		}
	}

	/**
	 * Exception
	 * These are suppressed (questionable, but part of the existing implementation)
	 * @throws IOException
	 */
	@Test
	public void testUnderlyingStreamIOException_NoChars() throws IOException {
		try(InputStream stream = new FailingInputStream("aaaa\nbbbb\ncccc", ""))
		{
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertEquals(0, actual.toArray(new String[0]).length);
			}	
		}
	}
	
	/**
	 * Exception
	 * These are suppressed (questionable, but part of the existing implementation)
	 * @throws IOException
	 */
	@Test
	public void testUnderlyingStreamIOException_1Char() throws IOException {
		try(InputStream stream = new FailingInputStream("aaaa\nbbbb\ncccc", "a"))
		{
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertArrayEquals("a".split("[\n]"), actual.toArray(new String[0]));
			}	
		}
	}
	
	/**
	 * Exception
	 * These are suppressed (questionable, but part of the existing implementation)
	 * @throws IOException
	 */
	@Test
	public void testUnderlyingStreamIOException_8Char() throws IOException {
		try(InputStream stream = new FailingInputStream("aaaa\nbbbb\ncccc", "aaaa\nbbb"))
		{
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertArrayEquals("aaaa\nbbb".split("[\n]"), actual.toArray(new String[0]));
			}	
		}
	}
	
	/**
	 * Exception
	 * These are suppressed (questionable, but part of the existing implementation)
	 * @throws IOException
	 */
	@Test
	public void testUnderlyingStreamIOException_AllChar() throws IOException {
		try(InputStream stream = new FailingInputStream("aaaa\nbbbb\ncccc", "aaaa\nbbbb\ncccc"))
		{
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertArrayEquals("aaaa\nbbbb\ncccc".split("[\n]"), actual.toArray(new String[0]));
			}	
		}
	}
	
	/**
	 * Boundary case
	 * Returns 0-length array
	 * @throws IOException
	 */
	@Test
	public void testEmptyStream() throws IOException {
		try(ByteArrayInputStream stream = new ByteArrayInputStream("".getBytes())){
			try(IteratorImpl impl = new IteratorImpl(stream)){
				List<String> actual = new ArrayList<String>();
				for(String s : impl){
					actual.add(s);
				}	
				assertEquals(0, actual.toArray(new String[0]).length);
			}	
		}
	}
	
}
