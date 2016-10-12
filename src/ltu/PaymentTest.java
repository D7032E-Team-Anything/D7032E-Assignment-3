package ltu;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import static ltu.CalendarFactory.getCalendar;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PaymentTest {


    private static final int FULL_LOAN         = 7088;
    private static final int HALF_LOAN         = 3564;
    private static final int ZERO_LOAN         = 0;
    private static final int FULL_SUBSIDY      = 2816;
    private static final int HALF_SUBSIDY      = 1396;
    private static final int ZERO_SUBSIDY      = 0;
    private static final int FULLTIME_INCOME   = 85813;
    private static final int PARTTIME_INCOME   = 128722;
    private static final int ZERO              = 0;

    private static PaymentImpl p = null;    
    
    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring"));
        
        
    }
    private static Calendar getSpringCal(){
    	
    	Calendar cal = Calendar.getInstance();
        cal.setTime(getCalendar("ltu.CalenderSpring").getDate());
        return cal;
    }
    
    private static String getSSN(int years, int months, int days, int extra4N){
    	DateFormat format = new SimpleDateFormat("yyyyMMdd");
    	
    	Calendar mCalender = getSpringCal();
    	
    	mCalender.add(Calendar.YEAR, -years);
    	mCalender.add(Calendar.MONTH, -months);
    	mCalender.add(Calendar.DATE, -days);
    	
    	String birthDate = format.format(mCalender.getTime());
    	
    	return birthDate + "-" + String.valueOf(extra4N);
    }


    /**
    [ID: 101] The student must be at least 20 years old to receive subsidiary and student loans.
    **/
    @Test
    public void ageTest() throws IOException {
        int loan = p.getMonthlyAmount( getSSN(19,0,0,5555) , 0, 100, 100);
        assertTrue(loan == ZERO_LOAN);
    }


    /**
     [ID: 102] The student may receive subsidiary until the year they turn 56.
    **/
    @Test
    public void ageOnlySubsidiary() throws IOException {
        int loan = p.getMonthlyAmount(getSSN(55,0,0,5555), 0, 100, 100);
        assertTrue(loan == FULL_SUBSIDY);
    }

    /**
     [ID: 102] Above 56 no loans
    **/
    @Test
    public void ageNoLoan() throws IOException {
        int loan = p.getMonthlyAmount(getSSN(57,0,0,5555), 0, 100, 100);
        assertTrue(loan == 0);
    }
    
    /**
     * [ID: 103] The student may not receive any student loans from the year they turn 47.
     */
    public void ageJust() throws IOException {
        int loan = p.getMonthlyAmount(getSSN(47,0,0,5555), 0, 100, 100);
        assertTrue(loan == FULL_SUBSIDY);
    }
    
    /**
    [ID: 201] The student must be studying at least half time to receive any subsidiary.
    */
    @Test
    public void studyPaceHalfReciveSubsidiary() throws IOException {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), 0, 49, 100);
        assertTrue(loan == ZERO); // Not sure about ZERO
    }
    /**
    [ID: 202] A student studying less than full time is entitled to 50% subsidiary.
    */
    @Test
    public void lessThanFullTimeOnlyHalfSub() throws IOException 
    {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), 0, 90, 100);
        assertTrue(loan == HALF_LOAN + HALF_SUBSIDY);
    }
    
    /**
    [ID: 203] A student studying full time is entitled to 100% subsidiary.
    */
    @Test
    public void fullTimeFullSub() throws IOException 
    {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), 0, 100, 100);
        assertTrue(loan == FULL_LOAN + FULL_SUBSIDY);
    }
    
    /**
     * [ID: 301] A student who is studying full time or more is permitted to earn a maximum of 85 813SEK 
     * per year in order to receive any subsidiary or student loans.
     * */
    @Test
    public void studyFullCanEarn85() throws IOException 
    {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), FULLTIME_INCOME , 100, 100);
        assertTrue(loan == FULL_LOAN + FULL_SUBSIDY);
    }
    
    /*
    [ID: 301] A student who is studying full time or more is 
    permitted to earn a maximum of 85 813SEK per year in order to receive any subsidiary or student loans.
    */
    @Test
    public void fulltimeIncomeTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", FULLTIME_INCOME+1, 100, 100);
        assertTrue(loan == 0);

    }
    
    /**
     * 	[ID: 302] A student who is studying less than full time is allowed to earn a 
     *	maximum of 128 722SEK per year in order to receive any subsidiary or student loans.
     * */
    @Test
    public void studyPartCanEarn128() throws IOException 
    {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), PARTTIME_INCOME -1 , 99, 100);
        assertTrue(loan == HALF_LOAN + HALF_SUBSIDY);
    }
    
    /*
    [ID: 302] A student who is studying less than full time is allowed to 
    earn a maximum of 128 722SEK per year in order to receive any subsidiary or student loans.
    */
    @Test
    public void parttimeIncomeTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", PARTTIME_INCOME+1, 50, 100);
        assertTrue(loan == 0);

    }
    
    /**
     * [ID: 401] A student must have completed at least 50% of previous studies in order to 
     * receive any subsidiary or student loans.
     * */
    @Test
    public void completeAtLeast50() throws IOException 
    {
        int loan = p.getMonthlyAmount(getSSN(22,0,0,5555), 0 , 100, 51);
        assertTrue(loan == FULL_LOAN + FULL_SUBSIDY);
    }

    /*
    [ID: 401] A student must have completed at least 50% of previous studies in order to receive any subsidiary or student loans.
    */
    @Test
    public void studiesCompletionTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", 0, 100, 49);
        assertTrue(loan == 0);
    }

    /**
        [ID: 506] Student loans and subsidiary is paid on the last weekday (Monday to Friday) every month.
    **/
    @Test
    public void nextPaymentTest() {
        int amount = p.getMonthlyAmount("19900615-5441", 0, 100, 100);
        String paymentDate = p.getNextPaymentDay();
  
        assertEquals(paymentDate, "20160129");
  
    }

    /**
    [ID: 501] Student loan: 7088 SEK / month
    **/
    /**
    [ID: 502] Subsidiary: 2816 SEK / month
    **/
    @Test
    public void studies100() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", 0, 100, 100);
        assertTrue(loan == FULL_LOAN + FULL_SUBSIDY);
    }
    

    /**
    [ID: 503] Student loan: 3564 SEK / month
    **/

    /**
    [ID: 504] Subsidiary: 1396 SEK / month
    **/
    @Test
    public void studiesless100() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", 0, 70, 100);
        assertTrue(loan == HALF_LOAN + HALF_SUBSIDY);
    }

    /**
    [ID: 505] A person who is entitled to receive a student loan will always receive the full amount.
    **/

    /**
        Checks security number
    **/
    @Test(expected=IllegalArgumentException.class)
    public void noSecurityNumberTest() throws IOException {
      int loan = p.getMonthlyAmount(null, -1, -1, -1);
    }
}