package lv.krasts.tools.groupjournal.parser;

import java.util.Date;

public class Lecture {

    private final String dayOfWeek;

    private final Date date;

    private final String timeRange;

    private final int hours;

    public Lecture(String dayOfWeek, Date date, String timeRange, int hours) {
	this.dayOfWeek = dayOfWeek;
	this.date = date;
	this.timeRange = timeRange;
	this.hours = hours;
    }

    public String getDayOfWeek() {
	return dayOfWeek;
    }

    public Date getDate() {
	return date;
    }

    public String getTimeRange() {
	return timeRange;
    }

    public String getHours() {
	StringBuilder sb = new StringBuilder();
	for (int i = 1; i <= hours; i++) {
	    sb.append(i + " ");
	}
	return sb.toString();
    }

}
