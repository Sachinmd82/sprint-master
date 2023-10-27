package com.example.SprintMaster.Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpringUtility {
	
	public static Timestamp getCurrentTimestamp() {
		Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("IST"));
        String formatted = format.format(date);
        return Timestamp.valueOf(formatted);
	}

	public static final String ASSIGNED			="Assigned";
	public static final String BREAK_START		="Break-Start";
	public static final String BREAK_END 		="Break-End";
	public static final String DONE 			="Done";
}
