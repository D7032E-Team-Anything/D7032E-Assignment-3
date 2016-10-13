package test;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import ltu.Consts;
import ltu.TestHelper;
import ltu.PaymentImpl;

import org.junit.BeforeClass;
import org.junit.Test;

public class Age {
	
private static PaymentImpl p = null;    
    
    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring")); 
    }
    
    // Age 19 gets nothing
    @Test
    public void test1() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(19,0,0,5555), 0, 100, 100);
        assertTrue(loan == Consts.ZERO);
    }
    // Age 20 gets loan + subs
    @Test
    public void test2() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), 0 , 100, 100);
        assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    }
    // Age 46 gets loan + subs
    @Test
    public void test3() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(46,0,0,5555), 0 , 100, 100);
        assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    }
    // Age 47 gets subs only
    @Test
    public void test4() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(47,0,0,5555), 0 , 100, 100);
        assertTrue(loan == Consts.FULL_SUBSIDY);
    }
    
    // Age 56 gets subs only
    @Test
    public void test5() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(56,0,0,5555), 0 , 100, 100);
        assertTrue(loan == Consts.FULL_SUBSIDY);
    }
    // Age 57 gets nothing
    @Test
    public void test6() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(57,0,0,5555), 0 , 100, 100);
        assertTrue(loan == Consts.ZERO);
    }

}
