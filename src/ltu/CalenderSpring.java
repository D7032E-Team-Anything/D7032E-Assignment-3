package ltu;

import java.util.Calendar;
import java.util.Date;

public class CalenderSpring implements ICalendar
{
	@Override
	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.JANUARY, 01);
		return cal.getTime();
	}
}