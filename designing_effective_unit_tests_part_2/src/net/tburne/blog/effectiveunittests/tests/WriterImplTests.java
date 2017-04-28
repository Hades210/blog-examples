package net.tburne.blog.effectiveunittests.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import net.tburne.blog.effectiveunittests.code.updated.impl.WriterImpl;
import net.tburne.blog.effectiveunittests.code.updated.types.IDestinationRecord;
import net.tburne.blog.effectiveunittests.tests.helpers.BooleanValue;
import net.tburne.blog.effectiveunittests.tests.helpers.FailingOutputStream;

import static org.junit.Assert.*;

public class WriterImplTests {

	/**
	 * Basic success case
	 * @throws IOException
	 */
	@Test
	public void testSuccess() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try(WriterImpl impl = new WriterImpl(output)){
			IDestinationRecord destination = create("John Smith", 10);
			impl.write(destination);
		}
		assertEquals("John Smith,10", new String(output.toByteArray()));
	}
	
	/**
	 * Functional coverage
	 * @throws IOException
	 */
	@Test
	public void testMultipleRecords() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try(WriterImpl impl = new WriterImpl(output)){
			{
				IDestinationRecord destination = create("John Smith", 10);
				impl.write(destination);
				
			}
			{
				IDestinationRecord destination = create("Jane Smith", 12);
				impl.write(destination);
			}

		}
		assertEquals("John Smith,10\nJane Smith,12", new String(output.toByteArray()));
	}
	
	/**
	 * Functional coverage
	 * @throws IOException
	 */
	@Test
	public void testMultipleRecordsRepeated() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try(WriterImpl impl = new WriterImpl(output)){
			{
				IDestinationRecord destination = create("John Smith", 10);
				impl.write(destination);
				
			}
			{
				IDestinationRecord destination = create("Jane Smith", 12);
				impl.write(destination);
			}
			{
				IDestinationRecord destination = create("John Smith", 10);
				impl.write(destination);
				
			}
			{
				IDestinationRecord destination = create("Jane Smith", 12);
				impl.write(destination);
			}
		}
		assertEquals("John Smith,10\nJane Smith,12\nJohn Smith,10\nJane Smith,12", new String(output.toByteArray()));
	}
	
	/**
	 * Functional coverage
	 * @throws IOException
	 */
	@Test
	public void testOutputStreamClosed() throws IOException {
		BooleanValue closed = new BooleanValue();
		ByteArrayOutputStream output = new ByteArrayOutputStream(){
			
			@Override
			public void close() throws IOException {
				closed.Value = true;
				super.close();
			}
			
		};
		try(WriterImpl impl = new WriterImpl(output)){
			IDestinationRecord destination = create("John Smith", 10);
			impl.write(destination);
		}
		assertTrue(closed.Value);
	}
	
	/**
	 * Boundary case
	 * @throws IOException
	 */
	@Test
	public void testNullRecord() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try(WriterImpl impl = new WriterImpl(output)){
			IDestinationRecord destination = null;
			impl.write(destination);
		}
		assertEquals("", new String(output.toByteArray()));
	}

	/**
	 * Boundary case
	 * @throws IOException
	 */
	@Test
	public void testNullName() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		NullPointerException ex = null;
		try(WriterImpl impl = new WriterImpl(output)){
			{
				IDestinationRecord destination = create(null, 10);
				impl.write(destination);	
			}
		}catch(NullPointerException e){
			ex = e;
		}
		assertNotNull(ex);
	}
	
	/**
	 * Boundary cases
	 * @throws IOException
	 */
	@Test
	public void testAges() throws IOException {
		{
			int age = -1;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
		{
			int age = 0;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
		{
			int age = 1;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
		{
			int age = Integer.MAX_VALUE;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
		{
			int age = Integer.MIN_VALUE;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
	}
	
	
	/**
	 * Exception
	 */
	@Test
	public void testIOExceptions() {
		{
			FailingOutputStream output = new FailingOutputStream("");
			IOException ex = null;
			try(WriterImpl impl = new WriterImpl(output)){
				{
					IDestinationRecord destination = create("John Smith", 10);
					impl.write(destination);	
				}
			}catch(IOException e){
				ex = e;
			}
			assertNotNull(ex);
		}
		{
			FailingOutputStream output = new FailingOutputStream("John");
			IOException ex = null;
			try(WriterImpl impl = new WriterImpl(output)){
				{
					IDestinationRecord destination = create("John Smith", 10);
					impl.write(destination);	
				}
			}catch(IOException e){
				ex = e;
			}
			assertNotNull(ex);
		}
		{
			FailingOutputStream output = new FailingOutputStream("John Smith,10");
			IOException ex = null;
			try(WriterImpl impl = new WriterImpl(output)){
				{
					IDestinationRecord destination = create("John Smith", 10);
					impl.write(destination);	
					destination = create("Jane Smith", 12);
					impl.write(destination);	
				}
			}catch(IOException e){
				ex = e;
			}
			assertNotNull(ex);
		}
	}
	
	/**
	 * Partial programmatic state coverage 
	 * @throws IOException
	 */
	@Test
	public void testAllAges() throws IOException {
		for(int i = -1000; i <= 1000; i++){
			int age = i;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try(WriterImpl impl = new WriterImpl(output)){
				IDestinationRecord destination = create("John Smith", age);
				impl.write(destination);
			}
			assertEquals("John Smith," + age, new String(output.toByteArray()));
		}
	}
	
	private IDestinationRecord create(String name, int age){
		return new IDestinationRecord() {
			
			@Override
			public String name() {
				return name;
			}

			@Override
			public int age() {
				return age;
			}
		};
	}
	
	
}
