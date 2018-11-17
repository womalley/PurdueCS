// Define class Teacher, subclass of AcademicPerson
public class Teacher extends AcademicPerson {
	// Instance variables
	private static final int MAX_COURSES = 10; // maximum courses

	// Constructor
	public Teacher(String name, String address) {
		super(name, address);
		courses = new String[MAX_COURSES];
	}

	
	// It adds a course into the list of courses.
	// This method throws ArrayElementException when the course that is being
	// added to the list already exists in it.
	public void addCourse(String course) throws ArrayElementException {

		int i;

		for (i = 0; i < numCourses; i++) {
			if (courses[i].equals(course)) {
				throw new ArrayElementException("Course Already in list!");

			}
		}
		courses[numCourses] = course;
		++numCourses;
	}

	// It removes a course into the list of courses.
	// This method throws ArrayElementException when the course does not exist
	// in the list.
	public void removeCourse(String course) throws ArrayElementException {

		int loc = -1; //location
		int i;
		int k;

		for (i = 0; i < numCourses; i ++) {
			if (courses[i].equals(course)) {
				loc = i;
			}
		}

		if (loc == 0) {
			for (k = 1; k < numCourses; k++) {
				courses[k - 1] = courses[k];
			}

			courses[numCourses] = null;
			--numCourses;
		}

		else {
			for (k = loc; k < numCourses - 1; k++) {
				courses[k] = courses[k + 1];
			}

			courses[numCourses] = null;
			--numCourses;
		}

		if (loc == -1) {
			throw new ArrayElementException("Course not found!");
		}
	}

	// It prints all the courses in the list in each line
	@Override
	public void printCourses() {

		int i;
		System.out.println("Courses teaching this semester: ");

		for (i = 0; i < numCourses; i++) {
			System.out.println(courses[i]);
		}
	}

	@Override
	public String toString() {
		return "Teacher: " + super.toString();
	}

}