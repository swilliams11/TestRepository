import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class DjiaLookup {

	private static String fileName = "../algs4/data/djia.csv";
	//private static BinarySearchST<String, Integer> months = new BinarySearchST<>(12);
	private static BinarySearchST<Date, Double> dja = new BinarySearchST<>();
	private static String [] fields;
	//private static String [] monthFields;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private SimpleDateFormat sdfm = new SimpleDateFormat("MM/dd/yyyy");
	private SimpleDateFormat sdfmdy = new SimpleDateFormat("MM-dd-yyyy");
	//private Calendar cal = Calendar.getInstance();
	private Scanner scannerSysIn = new Scanner(System.in);
	private Date dt, max, min, dtFound;
	private Double valFound;
	
	
	public static void main(String[] args) {
		DjiaLookup djia = new DjiaLookup();
		djia.init();
		djia.printDirections();
		djia.getFileName();
		djia.readFile();
		djia.getDjia();
		djia.cleanUp();
	}
	
	public void init(){
		sdfmdy.setLenient(false);
	}
	
	public void printDirections(){
		System.out.println("Get the Dow Jones Industrial Average Adjusted Closing Values\n"
				+ "Input name of a data file whose input lines are of the form\n" 
				+ "date, open, hi, lo, closing, volume, adjusted closing\n\n"
				+ "Then enter any date to get the adjusted closing value or done to exit");
	}
	
	public void getFileName(){
		String fname = "";
		System.out.println("Input file (default is ../algs4/data/djia.csv):");
		fname = scannerSysIn.nextLine();
		if(!fname.equals(""))
			fileName = fname;		
	}
	
	public void readFile(){		
		//DjiaLookup.class.getResourceAsStream(fileName)
		Scanner s = openInput(fileName);
		while(s.hasNextLine()){
			readLine(s.nextLine());			
		}
		s.close();
	}
	
	/*
	 * Open the file to read
	 */
	public static Scanner openInput(String fname){
	    Scanner infile = null;
	    try {
	      infile = new Scanner(new File(fname));
	    } catch(FileNotFoundException e) {
	      System.out.printf("Cannot open file '%s' for input\n", fname);
	      System.exit(0);
	    }
	    return infile;
	  }
	
	/*
	 * Parse the line and adds it to the BinarySearchST.
	 */
	public void readLine(String line){
		fields = line.split("\\s*,\\s*");
		Date key = parseDate(fields[0]);
		if(key != null){
			Double val = parseAverage(fields[6]);		
			dja.put(key, val);
		}
	}
	
	/*
	 * Converts the String date to a java.util.Date and adds the month and month number
	 * to the BinarySearchST.  
	 */
	public Date parseDate(String date) {		
		try{
			Date d2 = sdf.parse(date);
			//String month = parseMonth(date);
			//cal.setTime(d2);			
			//months.put(month, new Integer(cal.get(Calendar.MONTH) + 1));			
			return d2;
		} catch(ParseException e){
			System.out.println("Invalid Date Format or No Such Date : " + date + ". Message: " + e.getMessage() );
			return null;
		}
	}
	
	/*public String parseMonth(String date){
		monthFields = date.split("-");
		return monthFields[1];
	}*/
	
	public Double parseAverage(String avg){
		return new Double(avg);
	}
	
	public void getDjia(){
		initMaxMin();
		String date = null;
		while ((date = displayPrompt() ) != null && !date.equals("done")){
			try{
				dt = sdfmdy.parse(date);
				searchDjia(dt);
			}catch(ParseException e){
				if(date.contains("/")){
					System.out.println("Invalid Date Format: " + date );
				} else {
					System.out.println("Invalid Date Format or No Such Date : " + date );
				}				
			}
		}
	}
	
	/*
	 * Set the max and min dates of the BinarySearchST for easy comparison.
	 */
	public void initMaxMin(){
		max = dja.max();
		min = dja.min();
	}
	
	public String displayPrompt(){
		System.out.println("\n\nEnter a date (mm-dd-yyyy):");		
		return scannerSysIn.nextLine(); 
	}
	
	/*
	 * search for the Djia within the BinarySearchST
	 */
	public void searchDjia(Date dt){
		//compare date entered to max()
		if(dt.compareTo(max) == 1){
			valFound = dja.get(max);
			printNoDataFound(dt, max);
			printKeyVal(max, valFound);
			return;
		}
			
		//compare date entered to min()
		if(dt.compareTo(min) == -1){
			valFound = dja.get(min);
			printNoDataFound(dt, min);
			printKeyVal(min, valFound);
			return;
		}
		
		valFound = dja.get(dt);
		//confirm date return is not null
		if(valFound != null){
			printKeyVal(dt, valFound);
		} else {
			//get the ceiling of the date
			dtFound = dja.ceiling(dt);
			valFound = dja.get(dtFound);
			printNoDataFound(dt, dtFound);
			printKeyVal(dtFound, valFound);
		}
	}
	
	public void printNoDataFound(Date invalid, Date valid){
		if(valid.equals(max)){
			System.out.printf("\nNo data for date: %s. Last recorded date is %s",  sdfm.format(invalid), sdfm.format(valid));
		} else {
			System.out.printf("\nNo data for date: %s. Next date is %s",  sdfm.format(invalid), sdfm.format(valid));
		}		
	}
	
	public void printKeyVal(Date key, Double val){
		//System.out.printf("\nDate: %s, Closing DJIA: %.2f", sdfm.format(key), val.doubleValue());
		System.out.printf("\nDate: %1$tm/%1$td/%1$ty , Closing DJIA: %2$f.2", key, val.doubleValue());
	}
	
	public void cleanUp(){
		scannerSysIn.close();
	}
	
	public int bstCount(){
		return dja.size();
	}
	/*public BinarySearchST<String, Integer> getMonths(){
		return months;
	}*/

}
