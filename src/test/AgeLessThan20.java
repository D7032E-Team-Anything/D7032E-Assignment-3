package test;

import static org.junit.Assert.*;
import static ltu.CalendarFactory.getCalendar;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import ltu.Consts;
import ltu.TestHelper;
import ltu.PaymentImpl;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

@RunWith(Theories.class)
public class AgeLessThan20 {
	
    private static PaymentImpl p = null;    
    
    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring")); 
    }
    
    @DataPoints
    public static String[] getAgeData(){
    	
    	List<String> mList = new LinkedList<String>();
    	String temp;
    	for(int i = 0 ; i < 20 ; i++){
    		temp = TestHelper.getSSN(i, 0, 0, 1111);
    		mList.add(temp);
    	}
    	String[] mArray = new String[mList.size()];
    	mArray = mList.toArray(mArray);
    	return mArray;
    }
    
    /**
    [ID: 101] The student must be at least 20 years old to receive subsidiary and student loans.
    **/
    @Theory
    public void ageTest(String age) throws IOException {
        int loan = p.getMonthlyAmount( age , 0, 100, 100);
        assertTrue(loan == Consts.ZERO);
    }
    
}
