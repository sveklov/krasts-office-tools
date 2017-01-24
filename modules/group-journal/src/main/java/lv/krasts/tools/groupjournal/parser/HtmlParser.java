package lv.krasts.tools.groupjournal.parser;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HtmlParser {

    public Model parseCsddGroup(String html) throws IOException {
        // Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        // Elements newsHeadlines = doc.select("#mp-itn b a");
        Document doc = Jsoup.parse(html);
        Elements doubleTables = doc.select("tbody");
        Element groupTable = doubleTables.get(0);
        Element lecturesTable = doubleTables.get(1);
        Element studentsTable = doubleTables.get(2);

        Model model = parseModel(groupTable, doc);
        model.setLectures(parseLectures(lecturesTable));
        model.setStudents(parseStudents(studentsTable));
        return model;
    }

    private List<Student> parseStudents(Element studentsTable) {
        List<Student> students = Lists.newArrayList();
        Elements rows = studentsTable.select("tr");
        for (Element row : rows) {
            students.add(parseStudent(row));
        }
        return students;
    }

    private Student parseStudent(Element row) {
        Elements cells = row.select("td");
        int i = 2;
        int studentOrderNumber = Integer.parseInt(cells.get(i++).ownText());
        String personalId = cells.get(i++).ownText();
        String firstName = cells.get(i++).ownText();
        String lastName = cells.get(i++).ownText();
        String fullName = firstName + " " + lastName;
        return new Student(studentOrderNumber, fullName, personalId);
    }

    private List<Lecture> parseLectures(Element timeTable) {
        List<Lecture> lectures = Lists.newArrayList();
        Elements rows = timeTable.select("tr");
        for (Element row : rows) {
            lectures.add(parseLecture(row));
        }
        return lectures;
    }

    private Lecture parseLecture(Element row) {
        Elements cells = row.select("td");
        int i = 0;
        String dayOfWeek = cells.get(i++).ownText();
        Element dateCell = cells.get(i++);
        Date date = toDate(dateCell.ownText());
        String timeRange = dateCell.select("span").get(0).ownText();
        int hours = Integer.parseInt(cells.get(i++).ownText());
        return new Lecture(dayOfWeek, date, timeRange, hours);
    }

    private Model parseModel(Element groupTable, Document doc) {
        Elements rows = groupTable.select("tr");
        Iterator<Element> iterator = rows.iterator();
        iterator.next();

        String rulesTeacher = getValue(iterator.next()).replaceAll("\\d", "").replaceAll("[()-]", "");

        // there is no technical teacher for some categories (like "95 kods")
        String technicalTeacherOrStartDate = getValue(iterator.next());
        String technicalTeacher;
        Date startDate;
        if (isDate(technicalTeacherOrStartDate)) {
            technicalTeacher = "";
            startDate = toDate(technicalTeacherOrStartDate);
        } else {
            technicalTeacher = technicalTeacherOrStartDate.replaceAll("\\d", "").replaceAll("[()-]", "");
            startDate = toDate(getValue(iterator.next()));
        }

        Date endDate = toDate(getValue(iterator.next()));
        String type = getValue(iterator.next());
        String roomId = getValue(iterator.next());

        String groupId = doc.select("h4").text().replaceFirst(".+ .+ ", "");
        return new Model(groupId, rulesTeacher, technicalTeacher, startDate, endDate, type, roomId);
    }

    private String getValue(Element row) {
        return row.select("td").get(1).ownText();
    }

    private Date toDate(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            throw Throwables.propagate(e);
        }
    }

    private boolean isDate(String date) {
        try {
            new SimpleDateFormat("dd.MM.yyyy").parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
