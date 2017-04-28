package net.tburne.blog.effectiveunittests.tests;

import java.time.LocalDate;
import java.time.Period;

import org.junit.Test;

import net.tburne.blog.effectiveunittests.code.updated.impl.TransformerImpl;
import net.tburne.blog.effectiveunittests.code.updated.types.IDestinationRecord;
import net.tburne.blog.effectiveunittests.code.updated.types.ISourceRecord;

import static org.junit.Assert.*;

public class TransformerImplTests {

	/**
	 * Basic success case
	 */
	@Test
	public void testSuccess(){
		LocalDate asAt = LocalDate.of(2017, 01, 02);
		ISourceRecord source = create("John Smith", 1980, 01, 03);
		TransformerImpl impl = new TransformerImpl(asAt);
		IDestinationRecord destination = impl.transform(source);
		assertNotNull(destination);
		assertEquals(36, destination.age());
		assertEquals("John Smith", destination.name());
	}
	
	/**
	 * Boundary case
	 */
	@Test
	public void testNullRecordName(){
		LocalDate asAt = LocalDate.of(2017, 01, 02);
		ISourceRecord source = create(null, 1980, 01, 03);
		TransformerImpl impl = new TransformerImpl(asAt);
		IDestinationRecord record = impl.transform(source);
		assertNotNull(record);
		assertEquals(36, record.age());
		assertNull(record.name());
	}
	
	/**
	 * Boundary case
	 */
	@Test
	public void testNegativeYears(){
		{
			LocalDate asAt = LocalDate.of(2016, 01, 01);
			ISourceRecord source = create("John Smith", 2017, 01, 01);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(-1, record.age());
		}
		{
			LocalDate asAt = LocalDate.of(2016, 01, 02);
			ISourceRecord source = create("John Smith", 2017, 01, 01);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(0, record.age());
		}
	}
	
	/**
	 * Boundary case
	 */
	@Test
	public void testZeroYears(){
		{
			LocalDate asAt = LocalDate.of(2017, 01, 01);
			ISourceRecord source = create("John Smith", 2017, 01, 01);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(0, record.age());
		}
		{
			LocalDate asAt = LocalDate.of(2017, 12, 31);
			ISourceRecord source = create("John Smith", 2017, 01, 01);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(0, record.age());
		}
		{
			LocalDate asAt = LocalDate.of(2018, 01, 01);
			ISourceRecord source = create("John Smith", 2017, 01, 01);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(1, record.age());
		}
	}
	
	/**
	 * Boundary case
	 */
	@Test
	public void testLeapYear(){
		{
			LocalDate asAt = LocalDate.of(2017, 02, 27);
			ISourceRecord source = create("John Smith", 2012, 02, 29);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(4, record.age());
		}
		{
			LocalDate asAt = LocalDate.of(2017, 02, 28);
			ISourceRecord source = create("John Smith", 2012, 02, 27);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(5, record.age());
		}
		{
			LocalDate asAt = LocalDate.of(2017, 03, 01);
			ISourceRecord source = create("John Smith", 2012, 02, 27);
			TransformerImpl impl = new TransformerImpl(asAt);
			IDestinationRecord record = impl.transform(source);
			assertNotNull(record);
			assertEquals(5, record.age());
		}
	}
	
	
	/**
	 * Exception
	 */
	@Test
	public void testNullRecordDate(){
		LocalDate asAt = LocalDate.of(2017, 01, 02);
		ISourceRecord source = create("John Smith", null);
		TransformerImpl impl = new TransformerImpl(asAt);
		NullPointerException ex = null;
		try{
			impl.transform(source);
		}catch(NullPointerException e){
			ex = e;
		}
		assertNotNull(ex);
	}
	
	/**
	 * Exception
	 */
	@Test
	public void testPassNull(){
		LocalDate asAt = LocalDate.of(2017, 01, 02);
		ISourceRecord source = null;
		TransformerImpl impl = new TransformerImpl(asAt);
		IDestinationRecord result = impl.transform(source);
		assertNull(result);
	}
	
	/**
	 * Exception
	 */
	@Test
	public void testPassNullAsAt(){
		NullPointerException ex = null;
		try{
			new TransformerImpl(null);
		}catch(NullPointerException e){
			ex = e;
		}
		assertNotNull(ex);
	}
	
	/**
	 * Exception
	 */
	@Test
	public void testNullRecordDateAndName(){
		LocalDate asAt = LocalDate.of(2017, 01, 02);
		ISourceRecord source = create(null, null);
		TransformerImpl impl = new TransformerImpl(asAt);
		NullPointerException ex = null;
		try{
			impl.transform(source);
		}catch(NullPointerException e){
			ex = e;
		}
		assertNotNull(ex);
	}

	/**
	 * Full programmatic state coverage
	 * Implies that the operating parameters of the function are 1/1/0000 to 01/01/2200
	 */
	@Test
	public void testAgeForAllDates(){
		{
			LocalDate asAt = LocalDate.of(2017, 01, 02);
			TransformerImpl impl = new TransformerImpl(asAt);
			LocalDate from = LocalDate.of(0, 1, 1);
			LocalDate to = LocalDate.of(2200, 01, 01);
			while (from.isBefore(to)) {
				Period period = Period.between(from, asAt);
				ISourceRecord source = create("John Smith", from);			
				IDestinationRecord destination = impl.transform(source);
				assertNotNull(destination);
				assertEquals(period.getYears(), destination.age());
				assertEquals("John Smith", destination.name());
				from = from.plusDays(1L);
			}	
		}
		{
			LocalDate asAt = LocalDate.of(2016, 02, 29);
			TransformerImpl impl = new TransformerImpl(asAt);
			LocalDate from = LocalDate.of(0, 1, 1);
			LocalDate to = LocalDate.of(2200, 01, 01);
			while (from.isBefore(to)) {
				Period period = Period.between(from, asAt);
				ISourceRecord source = create("John Smith", from);			
				IDestinationRecord destination = impl.transform(source);
				assertNotNull(destination);
				assertEquals(period.getYears(), destination.age());
				assertEquals("John Smith", destination.name());
				from = from.plusDays(1L);
			}	
		}
	}

	private ISourceRecord create(String name, int year, int month, int day){
		return create(name, LocalDate.of(year, month, day));
	}
	
	private ISourceRecord create(String name, LocalDate date){
		return new ISourceRecord() {
			
			@Override
			public String name() {
				return name;
			}
			
			@Override
			public LocalDate date() {
				return date;
			}
		};
	}
	
}
