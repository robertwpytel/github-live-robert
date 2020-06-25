package projects;

import java.io.File;

public class LMSApplication {

	public static void main(String[] args) {
		// Initialize all
		File file = new File("courses.dat");
		CourseRepo repo = new CourseRepo();
		CourseRepoDB database = new CourseRepoDB("courses");
		CoursesCRUD crud = null;

		// Create our crud to do CRUD!
		try {
			crud = new CoursesCRUD();
			repo.saveAllCourses(crud);
			crud.setCoursesHashMap(repo.fetchAllCourses(crud));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// C - creation
		System.out.println("Courses created.");
		crud.createCourse(new Course("Java: You ARE the Coffee", "Pep talks about Java best practices", "Coff33",
				"Beans", 27.99));
		crud.createCourse(new Course("Python: Talk to the Snake", "Learn how to speak Python", "Harry Potter",
				"Harry Potter", 500.00));

		// R - reading a course
		System.out.println("READING: Current courses:");
		System.out.println(crud.searchCourse("Coff33"));
		System.out.println(crud.getCourse("Coff33").toString() + "\n");

		// U - updating a course
		crud.updateCourse("Coff33", "The Barista");
		System.out.println("UPDATING: Current courses:");
		System.out.println(crud.toString());

		// D - deleting a course
		crud.deleteCourse("The Barista");
		System.out.println("DELETING: Current courses:");
		System.out.println(crud.toString());

		// database.saveCourse(crud.getCourse("Harry Potter"));
	}
}
