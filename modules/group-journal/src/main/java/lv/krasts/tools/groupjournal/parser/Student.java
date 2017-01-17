package lv.krasts.tools.groupjournal.parser;

public class Student {

    private final String fullName;

    private final String personalId;

    private final int orderNumber;

    public Student(int orderNumber, String fullName, String personalId) {
	this.orderNumber = orderNumber;
	this.fullName = fullName;
	this.personalId = personalId;
    }

    public String getFullName() {
	return fullName;
    }

    public String getPersonalId() {
	return personalId;
    }

    public int getOrderNumber() {
	return orderNumber;
    }

}
