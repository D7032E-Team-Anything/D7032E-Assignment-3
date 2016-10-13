package test;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ltu.Consts;
import ltu.TestHelper;
import ltu.PaymentImpl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class Age20To47 {
	
    private static PaymentImpl p = null;    
    
    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring")); 
    }
    
    @DataPoints
    public static String[] getAgeData(){
    	
    	List<String> mList = new LinkedList<String>();
    	String temp;
    	for(int i = 20 ; i < 47 ; i++){
    		temp = TestHelper.getSSN(i, 0, 0, 1111);
    		mList.add(temp);
    	}
    	String[] mArray = new String[mList.size()];
    	mArray = mList.toArray(mArray);
    	return mArray;
    }
    
    /**
     * Full study rate and 0 income
    **/
    @Theory
    public void test1(String age) throws IOException {
        int loan = p.getMonthlyAmount( age , 0, 100, 100);
        //System.out.println(loan);
        assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    }
    
    /**
     * Full study rate and FULLTIME_MAX_INCOME
    **/
    @Theory
    public void test2(String age) throws IOException {
        int loan = p.getMonthlyAmount( age , Consts.FULLTIME_MAX_INCOME , 100, 100);
      //System.out.println(loan);
        assertTrue(loan == Consts.FULL_LOAN+ Consts.FULL_SUBSIDY);
    }
    
    /**
     * Full study rate and 0 income
    **/
    @Theory
    public void test3(String age) throws IOException {
        int loan = p.getMonthlyAmount( age , 0, 100, 100);
      //System.out.println(loan);
        assertTrue(loan == Consts.FULL_LOAN+ Consts.FULL_SUBSIDY);
    }
    
    /**
     * Full study rate and 0 income
    **/
    @Theory
    public void test4(String age) throws IOException {
        int loan = p.getMonthlyAmount( age , 0, 100, 100);
      //System.out.println(loan);
        assertTrue(loan == Consts.FULL_LOAN+ Consts.FULL_SUBSIDY);
    }
    /**
     * Full study rate age 47
     * */
    @Test
    public void test5() throws IOException {
        int loan = p.getMonthlyAmount( TestHelper.getSSN(47, 0, 0, 1111) , 0, 100, 100);
        //System.out.println(loan);
        assertTrue(loan == Consts.FULL_SUBSIDY);
    }
    
    /**
     * Less than full time max part income
     * */
    @Test
    public void test6() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(22,0,0,5555), Consts.PARTTIME_MAX_INCOME , 99, 100);
        assertTrue(loan == Consts.HALF_LOAN + Consts.HALF_SUBSIDY);
    }
    
    /**
     * full time max part income
     * */
    @Test
    public void test7() throws IOException 
    {
        int loan = p.getMonthlyAmount(TestHelper.getSSN(22,0,0,5555), Consts.PARTTIME_MAX_INCOME , 100, 100);
        assertTrue(loan == Consts.HALF_LOAN + Consts.HALF_SUBSIDY);
    }
}