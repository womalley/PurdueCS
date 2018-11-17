import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeachingAssistantStudent extends Student {

	private int hours; // total hours of TA
	protected HashMap<String, Integer> courseHour = new HashMap<String, Integer>();

	public TeachingAssistantStudent(String name, String address) {
		super(name, address);
		this.hours = 0;
	}

	// It adds a course into the HashMap with the key of course and hours as the
	// value.
	// This method throws ArrayElementException when the course that is being
	// added to the HashMap already exists in it.
	public void addCourseHour(String course, int hours)
			throws ArrayElementException {

		Integer exists = courseHour.get(course);

		if (exists != null) {
			throw new ArrayElementException("Course Already in list! ");
		}
		else {
			this.hours = this.hours + hours;
			courseHour.put(course, hours); //put will be used in the print (key, value)
		}
	}

	// This method first should print the courses along with the grades that the
	// TA is taking as a student and then print out the courses with the
	// corresponding hours which the TA is assisting in each line.
	// Use Iterator to iterate the HashMap.
	@Override
	public void printCourses() {

		Iterator<Map.Entry<String, Integer>>iterator = courseHour.entrySet().iterator();

		System.out.println("CourseName\t\t\t\tCourseGrade ");
		System.out.println("CourseAssisting\t\t\t\tHours ");

		while (iterator.hasNext()) {
			Map.Entry<String, Integer>entry = iterator.next();

			System.out.println(entry.getKey() + "\t\t\t\t" + entry.getValue());
		}

	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return super.toString() + " Also serving as Teaching Assistant.";
	}

}