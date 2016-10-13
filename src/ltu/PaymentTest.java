package ltu;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import static ltu.CalendarFactory.getCalendar;

import java.io.*;

public class PaymentTest {
	
private static PaymentImpl p = null;    
    
    @BeforeClass
    public static void init() throws IOException {
        p = new PaymentImpl(getCalendar("ltu.CalenderSpring")); 
    }
    
    
    ////////////////////////////// AGE /////////////////////////////
    // Age 19 gets nothing
    @Test
    public void test1()  
    {
    	int loan = -1;
    	try{
            loan = p.getMonthlyAmount(TestHelper.getSSN(19,0,0,5555), 0, 100, 100);
            assertTrue(loan == Consts.ZERO);
    	}catch(AssertionError err){
    		System.out.println("Test 1, correct : 0, got : "+ loan);
    	}
    }
    // Age 20 gets loan + subs
    @Test
    public void test2() 
    {
    	int loan = -1;
    	try{
            loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), 0 , 100, 100);
            assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    		
    	}catch(AssertionError err){
    		System.out.println("Test 2, correct : 9904, got : "+ loan);
    	}

    }
    // Age 46 gets loan + subs
    @Test
    public void test3()
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(46,0,0,5555), 0 , 100, 100);
            assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    		
    	}catch(AssertionError err){
    		System.out.println("Test 3, correct : 9904, got : "+ loan);
    	}
        
    }
    // Age 47 gets subs only
    @Test
    public void test4() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(47,0,0,5555), 0 , 100, 100);
            assertTrue(loan == Consts.FULL_SUBSIDY);    		
    	}catch(AssertionError err){
    		System.out.println("Test 4, correct : 2816, got : "+ loan);
    	}
            }
    // Age 56 gets subs only
    @Test
    public void test5() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(56,0,0,5555), 0 , 100, 100);
            assertTrue(loan == Consts.FULL_SUBSIDY);
    	}catch(AssertionError err){
    		System.out.println("Test 5, correct : 2816, got : "+ loan);
    	}
    }
    // Age 57 gets nothing
    @Test
    public void test6() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(57,0,0,5555), 0 , 100, 100);
            assertTrue(loan == Consts.ZERO);
    	}catch(AssertionError err){
    		System.out.println("Test 6, correct : 0, got : "+ loan);
    	}
        
    }
    
    
    //////////////////////////////// INCOME ///////////////////////////////////
    // Full time max income + 1 and study rate 101 gets nothing
    @Test
    public void test7() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555),Consts.FULLTIME_MAX_INCOME +1 , 101, 100);
    	    assertTrue(loan == Consts.ZERO);
    	}catch(AssertionError err){
    		System.out.println("Test 7, correct : 0, got : "+ loan);
    	}
       
    }
    // Full time max income +1 and study rate 100 gets nothing
    @Test
    public void test8() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), Consts.FULLTIME_MAX_INCOME +1 , 100, 100);
            assertTrue(loan == Consts.ZERO);
    	}catch(AssertionError err){
    		System.out.println("Test 8, correct : 0, got : "+ loan);
    	}
        
    }
    // Full time max income and study rate 100 gets loan + subs
    @Test
    public void test9() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), Consts.FULLTIME_MAX_INCOME , 100, 100);
            assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    	}catch(AssertionError err){
            System.out.println("Test 9, correct : 9904, got : "+ loan);
    	}
        
    }
    // Part time max income + 1 and study rate 100 gets nothing
    @Test
    public void test10() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555),Consts.PARTTIME_MAX_INCOME +1 , 100, 100);
            assertTrue(loan == Consts.ZERO);
    	}catch(AssertionError err){
    		System.out.println("Test 10, correct : 0, got : "+ loan);
    	}
        
    }
    // Part time max income and study rate 99 gets half loan + half subs
    @Test
    public void test11() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), Consts.PARTTIME_MAX_INCOME, 99, 100);
            assertTrue(loan == Consts.HALF_LOAN + Consts.HALF_SUBSIDY);
    	}catch(AssertionError err){
    		System.out.println("Test 11, correct : 4960, got : "+ loan);
    	}
        
    }
    
    
    ////////////////////////////// STUDY RATE ////////////////////////////////////////
    // Study rate = 100 gets loan + subs 
    @Test
    public void test12() 
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555),0 , 100, 100);
            assertTrue(loan == Consts.FULL_LOAN + Consts.FULL_SUBSIDY);
    	}catch(AssertionError err){
    		System.out.println("Test 12, correct : 9904, got : "+ loan);
    	}
    }
    // Study rate < 100 gets half loan + half subs
    @Test
    public void test13()
    {
    	int loan = -1;
    	try{
    		loan = p.getMonthlyAmount(TestHelper.getSSN(20,0,0,5555), 0 , 99, 100);
            assertTrue(loan == Consts.HALF_LOAN + Consts.HALF_SUBSIDY);
    		
    	}catch(AssertionError err){
    		System.out.println("Test 13, correct : 4960, got : "+ loan);
    	}
    }	
}