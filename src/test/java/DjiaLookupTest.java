import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;


public class DjiaLookupTest {
	DjiaLookup dj = new DjiaLookup();
	
	
	@Before
	public void setUp() throws Exception {
		dj.init();
		dj.readFile();
	}

	@Test
	public void testReadLine() {
		//fail("Not yet implemented");
	}

	
	//@Test
	public void testParseDate() {
		//make sure the month was month number were added to the BST.
		Date d = dj.parseDate("17-Mar-2006");
		Calendar c = Calendar.getInstance();
		c.set(2006, 3, 17);
		assertEquals(c.getTime(), d);
		d = dj.parseDate("28-Feb-2006");
		d = dj.parseDate("28-Jan-2006");
		d = dj.parseDate("01-Jun-2006");
		d = dj.parseDate("28-Dec-2006");
		
	/*	BinarySearchST<String, Integer> bst = dj.getMonths();
		assertEquals(new Integer(3), bst.get("Mar"));
		assertEquals(new Integer(1), bst.get("Jan"));
		assertEquals(new Integer(2), bst.get("Feb"));
		assertEquals(new Integer(6), bst.get("Jun"));
		assertEquals(new Integer(12), bst.get("Dec"));
	*/	
	}

	/*@Test
	public void testParseMonth() {
		String month = dj.parseMonth("17-Mar-2006");
		Assert.assertEquals("Mar", month);
	}*/

	@Test
	public void testParseAverage() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testBSTCount(){
		assertEquals(19449, dj.bstCount());
	}

}
