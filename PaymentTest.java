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

    private static PaymentImpl p = null;

    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar());
    }


    /**
    Person under 20 should not get a lone
    **/
    @Test
    public void ageTest() throws IOException {
        int loan = p.getMonthlyAmount("19700615-5441", 0, 100, 100);
        assertTrue(loan == 2816+7088);
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

        int loan = p.getMonthlyAmount("19650615-5441", 0, 100, 100);
        assertTrue(loan == 2816);

        loan = p.getMonthlyAmount("19650615-5441", 0, 70, 100);
        assertTrue(loan == 1396);

        loan = p.getMonthlyAmount("19650615-5441", 128780, 100, 100);
        assertTrue(loan == 0);

        loan = p.getMonthlyAmount("19650615-5441", 128780, 80, 100);
        assertTrue(loan == 0);

    }






}
