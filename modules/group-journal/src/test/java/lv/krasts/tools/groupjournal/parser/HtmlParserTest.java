package lv.krasts.tools.groupjournal.parser;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlParserTest {

    @Test
    public void parse() throws IOException {
	InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("csdd_journal_example.html");
	String html = IOUtils.toString(htmlStream, "utf-8");
	Model model = new HtmlParser().parseCsddGroup(html);

	assertEquals("3997-1915", model.getGroupId());
	assertEquals("JURIJS ASTAFJEVS", model.getRulesTeacher());
	assertEquals("JURIJS ASTAFJEVS", model.getTechnicalTeacher());
	assertEquals("06.12.2016", format(model.getStartDate()));
	assertEquals("05.01.2017", format(model.getEndDate()));
	assertEquals("B/C", model.getType());
	assertEquals("2", model.getRoomId());
	assertEquals("Otrdiena, Ceturtdiena", model.getLectureDaysOfWeek());

	List<Student> students = model.getStudents();
	assertEquals(15, students.size());
	Student student = students.iterator().next();
	assertEquals(1, student.getOrderNumber());
	assertEquals("EDGARS ALDIŅŠ", student.getFullName());
	assertEquals("18069411854", student.getPersonalId());

	List<Lecture> lectures = model.getLectures();
	assertEquals(10, lectures.size());
	Lecture lecture = lectures.iterator().next();
	assertEquals("06.12.2016", format(lecture.getDate()));
	assertEquals("Otrdiena", lecture.getDayOfWeek());
	assertEquals("1 2 3 4 5 ", lecture.getHours());
	assertEquals("17:30 - 21:35", lecture.getTimeRange());
    }

    private String format(Date date) {
	return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

}
