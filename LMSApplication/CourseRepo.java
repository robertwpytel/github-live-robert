package projects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CourseRepo {

	/**
	 * @param nothing
	 * @see Saves a course to the hard disk
	 */
	public boolean saveCourse(CoursesCRUD crud, Course course) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(crud.getCoursesFile(), false);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			if (crud.getCoursesHashMap().containsValue(course)) {
				objectOutputStream.writeObject(crud.getCoursesHashMap());
			} else { // add it and then save
				crud.createCourse(course);
				objectOutputStream.writeObject(crud.getCoursesHashMap());
			}
		} catch (Exception e) {
			System.out.println("Could not write to new file because of:");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param nothing
	 * @see Saves all courses to the hard disk
	 */
	public boolean saveAllCourses(CoursesCRUD crud) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(crud.getCoursesFile());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(crud.getCoursesHashMap());
		} catch (Exception e) {
			System.out.println("Could not write to new file because of:");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param crud
	 * @param courseAttrString
	 * @return Course
	 * @throws Exception
	 * @see Fetches a course from the hard disk
	 */
	@SuppressWarnings("unchecked")
	public Course fetchCourse(CoursesCRUD crud, String courseAttrString) {
		try (FileInputStream fileInputStream = new FileInputStream(crud.getCoursesFile());
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
			if (crud.searchCourse(courseAttrString)) {
				// read it and then set the current <code>nextIndex</code>.
				HashMap<Integer, Course> readObject = (HashMap<Integer, Course>) objectInputStream.readObject();
				crud.setCoursesHashMap(readObject);
				crud.setNextIndex(crud.getCoursesHashMap().size());
				return crud.getCourse(courseAttrString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param crud
	 * @param coursePrice
	 * @return Course
	 * @throws Exception
	 * @see Fetches a course from the hard disk
	 */
	@SuppressWarnings("unchecked")
	public Course fetchCourse(CoursesCRUD crud, double coursePrice) {
		try (FileInputStream fileInputStream = new FileInputStream(crud.getCoursesFile());
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
			if (crud.searchCourse(coursePrice)) {
				// read it and then set the current <code>nextIndex</code>.
				HashMap<Integer, Course> readObject = (HashMap<Integer, Course>) objectInputStream.readObject();
				crud.setCoursesHashMap(readObject);
				crud.setNextIndex(crud.getCoursesHashMap().size());
				return crud.getCourse(coursePrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return HashMap<Integer, Course>
	 * @throws Exception
	 * @see Fetches all courses from the hard disk
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Course> fetchAllCourses(CoursesCRUD crud) {

		try (FileInputStream fileInputStream = new FileInputStream(new File("courses.dat"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
			// read it and then set the current <code>nextIndex</code>.
			HashMap<Integer, Course> readObject = (HashMap<Integer, Course>) objectInputStream.readObject();
			crud = new CoursesCRUD(readObject, readObject.size());
			return crud.getCoursesHashMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}