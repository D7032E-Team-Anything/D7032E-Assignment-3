package ltu;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import static ltu.CalendarFactory.getCalendar;
import java.io.*;

public class PaymentTest {


    private final int FULL_LOAN         = 7088;
    private final int HALF_LOAN         = 3564;
    private final int ZERO_LOAN         = 0;
    private final int FULL_SUBSIDY      = 2816;
    private final int HALF_SUBSIDY      = 1396;
    private final int ZERO_SUBSIDY      = 0;
    private final int FULLTIME_INCOME   = 85813;
    private final int PARTTIME_INCOME   = 128722;
    private final int ZERO              =0;

    private static PaymentImpl p = null;

    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring"));
    }


    /**
    No loan for student under 20
    **/
    @Test
    public void ageTest() throws IOException {
        int loan = p.getMonthlyAmount("19990815-5441", 0, 100, 100);
        assertTrue(loan == 0);
    }


    /**
     47 to 56 only subsidiary loan
    **/
    @Test
    public void ageOnlySubsidiary() throws IOException {
        int loan = p.getMonthlyAmount("19640815-5441", 0, 100, 100);
        assertTrue(loan == FULL_SUBSIDY);
    }

    /**
     Above 56 no loans
    **/
    @Test
    public void ageNoLoan() throws IOException {
        int loan = p.getMonthlyAmount("19580815-5441", 0, 100, 100);
        assertTrue(loan == 0);
    }

    /**
    The student must be studying at least half time to receive any subsidiary.
    */
    @Test
    public void studyPaceHalfReciveSubsidiary() throws IOException {
        int loan = p.getMonthlyAmount("19790815-5441", 0, 49, 100);
        assertTrue(loan == ZERO);
    }
    /**
    * Payment date
    */
    @Test
    public void paymentDate() throws IOException {

        int loan = p.getMonthlyAmount("19790815-5441", 0, 49, 100);
        //p.getNextPaymentDay().;
       // assertTrue(loan == ZERO);
    }

    /**
        Checks security number
    **/
    @Test(expected=IllegalArgumentException.class)
    public void noSecurityNumberTest() throws IOException {
      int loan = p.getMonthlyAmount(null, -1, -1, -1);
    }

    @Test
    public void subsidyTest() throws IOException {

        int loan = p.getMonthlyAmount("19640815-5441", 0, 100, 100);
        assertTrue(loan == 2816);

        loan = p.getMonthlyAmount("19640815-5441", 0, 70, 100);
        assertTrue(loan == 1396);

        loan = p.getMonthlyAmount("19640815-5441", 128780, 100, 100);
        assertTrue(loan == 0);

        loan = p.getMonthlyAmount("19640815-5441", 128780, 80, 100);
        assertTrue(loan == 0);
    }

    /*
    [ID: 301] A student who is studying full time or more is permitted to earn a maximum of 85 813SEK per year in order to receive any subsidiary or student loans.
    */
    @Test
    public void fulltimeIncomeTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", FULLTIME_INCOME+1, 100, 100);
        assertTrue(loan == 0);

    }

    /*
    [ID: 302] A student who is studying less than full time is allowed to earn a maximum of 128 722SEK per year in order to receive any subsidiary or student loans.
    */
    @Test
    public void parttimeIncomeTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", PARTTIME_INCOME+1, 50, 100);
        assertTrue(loan == 0);

    }

    /*
    [ID: 401] A student must have completed at least 50% of previous studies in order to receive any subsidiary or student loans.
    */
    @Test
    public void studiesCompletionTest() throws IOException {
        int loan = p.getMonthlyAmount("19890815-5441", 0, 100, 49);
        assertTrue(loan == 0);
    }




}
