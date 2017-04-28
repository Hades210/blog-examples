package net.tburne.blog.effectiveunittests.tests;

import org.junit.Test;

import net.tburne.blog.effectiveunittests.code.updated.impl.ParserImpl;
import net.tburne.blog.effectiveunittests.code.updated.types.ISourceRecord;
import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDate;

public class ParserImplTests {

	/**
	 * Basic success case
	 */
	@Test
	public void testSuccess() {
		ParserImpl impl = new ParserImpl();
		ISourceRecord record = impl.parse("John Smith,2017,01,12");
		assertNotNull(record);
		assertEquals("John Smith", record.name());
		assertEquals(LocalDate.of(2017, 01, 12), record.date());
	}

	/**
	 * Boundary case
	 */
	@Test
	public void testNull() {
		ParserImpl impl = new ParserImpl();
		ISourceRecord record = impl.parse(null);
		assertNull(record);
	}

	/**
	 * Boundary case
	 */
	@Test
	public void testEmpty() {
		ParserImpl impl = new ParserImpl();
		ISourceRecord record = impl.parse("");
		assertNull(record);
	}

	/**
	 * Boundary case
	 */
	@Test
	public void testEmptyNameFullRecord() {
		ParserImpl impl = new ParserImpl();
		ISourceRecord record = impl.parse(",2012,01,01");
		assertEquals("", record.name());
	}

	/**
	 * Exception
	 */
	@Test
	public void testInvalidDate() {
		// invalid month
		{
			ParserImpl impl = new ParserImpl();
			DateTimeException ex = null;
			try {
				impl.parse(String.format("John Smith,%s,%s,%s", 2017, 202, 20));
			} catch (DateTimeException e) {
				ex = e;
			}
			assertNotNull(ex);
		}
		// invalid month
		{
			ParserImpl impl = new ParserImpl();
			DateTimeException ex = null;
			try {
				impl.parse(String.format("John Smith,%s,%s,%s", 2017, -2, 20));
			} catch (DateTimeException e) {
				ex = e;
			}
			assertNotNull(ex);
		}
		// invalid day
		{
			ParserImpl impl = new ParserImpl();
			DateTimeException ex = null;
			try {
				impl.parse(String.format("John Smith,%s,%s,%s", 2017, 20, 200));
			} catch (DateTimeException e) {
				ex = e;
			}
			assertNotNull(ex);
		}
		// invalid day
		{
			ParserImpl impl = new ParserImpl();
			DateTimeException ex = null;
			try {
				impl.parse(String.format("John Smith,%s,%s,%s", 2017, 20, -2));
			} catch (DateTimeException e) {
				ex = e;
			}
			assertNotNull(ex);
		}
		// non existent leap day
		{
			ParserImpl impl = new ParserImpl();
			DateTimeException ex = null;
			try {
				impl.parse(String.format("John Smith,%s,%s,%s", 2017, 02, 29));
			} catch (DateTimeException e) {
				ex = e;
			}
			assertNotNull(ex);
		}
	}

	/**
	 * Selective programmatic state coverage & Exception
	 */
	@Test
	public void testInvalidDateComponents() {
		for (String year : new String[] {
			"2012",
			"AAA"
		}) {
			for (String month : new String[] {
				"12",
				"BBB"
			}) {
				for (String day : new String[] {
					"01",
					"CCC"
				}) {
					if ("2012".equals(year) || "12".equals(month) || "01".equals(day)) {
						continue;
					}
					ParserImpl impl = new ParserImpl();
					NumberFormatException ex = null;
					try {
						impl.parse(String.format("John Smith,%s,%s,%s", year, month, day));
					} catch (NumberFormatException e) {
						ex = e;
					}
					assertNotNull(ex);
				}
			}
		}
	}

	/**
	 * Selective programmatic state coverage 
	 */
	@Test
	public void testEmptyDateComponents() {
		for (String year : new String[] {
			"2012",
			""
		}) {
			for (String month : new String[] {
				"12",
				""
			}) {
				for (String day : new String[] {
					"01",
					""
				}) {
					if ("2012".equals(year) || "12".equals(month) || "01".equals(day)) {
						continue;
					}
					ParserImpl impl = new ParserImpl();
					ISourceRecord record = impl.parse(String.format("John Smith,%s,%s,%s", year, month, day));
					assertNull(record);
					record = impl.parse(String.format(",%s,%s,%s", year, month, day));
					assertNull(record);
					record = impl.parse(String.format("%s,%s,%s", year, month, day));
					assertNull(record);
					String recordStr = "John Smith";
					if ("2012".equals(year)) {
						recordStr += ("," + year);
					}
					if ("12".equals(month)) {
						recordStr += ("," + month);
					}
					if ("01".equals(day)) {
						recordStr += ("," + day);
					}
					record = impl.parse(recordStr);
					assertNull(record);
				}
			}
		}
	}

	/**
	 * Full programmatic state coverage
	 * Implies that the operating parameters of the function are 1/1/0000 to 01/01/2200
	 */
	@Test
	public void testDates() {
		{
			ParserImpl impl = new ParserImpl();
			LocalDate from = LocalDate.of(0, 1, 1);
			LocalDate to = LocalDate.of(2200, 01, 01);
			while (from.isBefore(to)) {
				String data = "John Smith," + from.getYear() + "," + from.getMonthValue() + "," + from.getDayOfMonth();
				ISourceRecord record = impl.parse(data);
				assertNotNull(record);
				assertEquals("John Smith", record.name());
				assertEquals(from, record.date());
				from = from.plusDays(1L);
			}
		}
		{
			ParserImpl impl = new ParserImpl();
			LocalDate from = LocalDate.of(0, 1, 1);
			LocalDate to = LocalDate.of(2200, 01, 01);
			while (from.isBefore(to)) {
				String data = "John Smith," + from.getYear() + ",0" + from.getMonthValue() + ",0" + from.getDayOfMonth();
				ISourceRecord record = impl.parse(data);
				assertNotNull(record);
				assertEquals("John Smith", record.name());
				assertEquals(from, record.date());
				from = from.plusDays(1L);
			}
		}
	}

}
