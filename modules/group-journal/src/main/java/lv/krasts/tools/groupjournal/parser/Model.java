package lv.krasts.tools.groupjournal.parser;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class Model {

	private List<Student> students = Lists.newArrayList();

	private List<Lecture> lectures = Lists.newArrayList();

	private final String groupId;

	private final String rulesTeacher;

	private final String technicalTeacher;

	private final Date startDate;

	private final Date endDate;

	private final String type;

	private final String roomId;

	public Model(String groupId, String rulesTeacher, String technicalTeacher,
			Date startDate, Date endDate, String type, String roomId) {
		this.groupId = groupId;
		this.rulesTeacher = rulesTeacher;
		this.technicalTeacher = technicalTeacher;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.roomId = roomId;
	}

	public List<Student> getStudents() {
		return students;
	}

	public String getLectureDaysOfWeek() {
		Set<String> lectureDaysOfWeek = new LinkedHashSet<String>();
		for (Lecture lecture : lectures) {
			lectureDaysOfWeek.add(lecture.getDayOfWeek());
		}
		return StringUtils.join(lectureDaysOfWeek, ", ");
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getRulesTeacher() {
		return rulesTeacher;
	}

	public String getTechnicalTeacher() {
		return technicalTeacher;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getType() {
		return type;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

}
