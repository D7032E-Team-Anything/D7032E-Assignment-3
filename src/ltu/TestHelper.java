package ltu;

import static ltu.CalendarFactory.getCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestHelper {
	
    public static Calendar getSpringCal(){
    	
    	Calendar cal = Calendar.getInstance();
        cal.setTime(getCalendar("ltu.CalenderSpring").getDate());
        return cal;
    }
    
    public static String getSSN(int years, int months, int days, int extra4N){
    	DateFormat format = new SimpleDateFormat("yyyyMMdd");
    	
    	Calendar mCalender = getSpringCal();
    	
    	mCalender.add(Calendar.YEAR, -years);
    	mCalender.add(Calendar.MONTH, -months);
    	mCalender.add(Calendar.DATE, -days);
    	
    	String birthDate = format.format(mCalender.getTime());
    	
    	return birthDate + "-" + String.valueOf(extra4N);
    }

}
