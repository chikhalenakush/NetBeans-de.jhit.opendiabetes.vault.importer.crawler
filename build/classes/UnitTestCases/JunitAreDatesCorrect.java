package UnitTestCases;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(Parameterized.class)

public class JunitAreDatesCorrect {
	public static String dateFormat = "dd/MM/yyyy";
	public Date formatedStartDate;
	public Date formatedEndDate;
	Boolean Result = false;
	public String fromDate,todate;
	Boolean expected = false;
	  
	
	 @Parameterized.Parameters
     public static Collection Dates() {
        return Arrays.asList(new Object[][] {
           { "15/05/2015", "20/05/2016", true},
           { "18-05/2015", "15/05/2015",false},
           { "15-05/2015", "20/05/2016",false},
           { "01/01/1985", "20/05/2016",false},
           { "15/05/2015", "20/05/2018",false},
           { "30/05/2017", "30/05/2017",true},
           { "30/05/2017", "32/05/2017",false},
           { "30/05/2015", "29/02/2017",false}
        });
     }
	
	 public JunitAreDatesCorrect(String startdate, 
		        String enddate,Boolean expected) {
		       System.out.println("TestJunit-> startdate:"+startdate+" enddate:"+enddate);
		        this.fromDate = startdate;
		        this.todate = enddate;
		        this.expected =expected;
		     }
	 

	@Test
	public void test() throws IOException, ParseException {

	System.out.println("Expected result is " + expected);
		
	Result = (getStratDate(fromDate) && getEndDate(fromDate,todate)); 
		
				assertEquals(expected, Result);	
	
		}	

	private boolean getEndDate(String fromDate,String todate) throws ParseException {
	
	
				Date todaydate = new Date();

				SimpleDateFormat formatTodayDate = new SimpleDateFormat(dateFormat);
				String todayDate = formatTodayDate.format(todaydate);
				
				Date validStardDate = new SimpleDateFormat(dateFormat).parse("01/01/1998");
				formatedStartDate = new SimpleDateFormat(dateFormat).parse(fromDate);

				try {
					formatedEndDate = new SimpleDateFormat(dateFormat).parse(todate);
				} catch (Exception e) {
	
					//System.out.println("End Date is not in correct format \n Date should be in Format of DD/MM/YYYY  Example: 13/03/2017");
					return false;
				}
				try {
					DateFormat validEndDate = new SimpleDateFormat(dateFormat); // To validate End date
					validEndDate.setLenient(false);
					validEndDate.parse(todate);
					if (formatedEndDate.after(new SimpleDateFormat(dateFormat).parse(todayDate))
							|| formatedEndDate.before(validStardDate)) {
						//System.out.println("You can only enter End date between 01/01/1998 and Today's Date!! ");
						return false;
					}
					if (formatedEndDate.before(formatedStartDate)) {
						//System.out.println("End Date cannot be earlier than Start date");
						return false;
					}
					return true;
				} catch (Exception e) {
	
				//	System.out.println("End Date is not Valid");
					return false;
				}

	}

	private boolean getStratDate(String fromDate) throws ParseException {
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
						SimpleDateFormat formatTodayDate = new SimpleDateFormat(dateFormat); // to format today's date as DD/MM/YYYY
						formatTodayDate.setLenient(false);
						Date todaydate = new Date();
						String todayDate = formatTodayDate.format(todaydate);
						
						

						Date validStardDate = new SimpleDateFormat(dateFormat).parse("01/01/1998"); 
						// To validate Beginning of Date

						try {
							
							formatedStartDate = new SimpleDateFormat(dateFormat).parse(fromDate);
						} catch (Exception e) {
				
							//System.out.println("Start Date is not in correct format \n Date should be in Format of DD/MM/YYYY  Example: 13/03/2017");
							return false;
						}
						try {
							DateFormat validStartdate = new SimpleDateFormat(dateFormat); // To validate start date
							validStartdate.setLenient(false);
							validStartdate.parse(fromDate);
							if (formatedStartDate.before(validStardDate)
									|| formatedStartDate.after(new SimpleDateFormat(dateFormat).parse(todayDate))) {
								//System.out.println("You can only enter Start date between 01/01/1998 and Today's Date!!");
								return false;
							}
							return true;
						} catch (Exception e) {
				
							//System.out.println("Start Date is not Valid");
							return false;

						}
				

	}
	}

