package projects;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Robert
 * @description A class used to perform all CRUD (Creation, Reading, Updating,
 *              and Deleting) operations on a Course(s).
 * @date 6/22/2020
 */
public class CoursesCRUD {
	// Attributes

	private HashMap<Integer, Course> coursesHashMap;
	private static File coursesFile;
	private int nextIndex;
	private static final int MIN_INDEX = 0;
	private static final String FILENAME_STRING = "courses.dat";

	// Constructors

	/**
	 * @Default
	 */
	public CoursesCRUD() {
		this.coursesHashMap = new HashMap<Integer, Course>();
		makeFileIfNeededInCurrentDirectory();
		this.nextIndex = 0;
	}

	/**
	 * @param coursesHashMap
	 */
	public CoursesCRUD(HashMap<Integer, Course> coursesHashMap) {
		this.coursesHashMap = coursesHashMap;
		makeFileIfNeededInCurrentDirectory();
		this.nextIndex = 0;
	}

	/**
	 * @param coursesHashMap
	 * @param nextIndex
	 */
	public CoursesCRUD(HashMap<Integer, Course> coursesHashMap, int nextIndex) {
		this.coursesHashMap = coursesHashMap;
		makeFileIfNeededInCurrentDirectory();
		this.nextIndex = 0;
	}

	// Methods

	public static void makeFileIfNeededInCurrentDirectory() {
		coursesFile = new File(FILENAME_STRING);
		try {
			coursesFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param title
	 * @param desc
	 * @param author
	 * @param instructor
	 * @param price
	 * @see Creates a new course and adds it to the collection.
	 */
	public void createCourse(String title, String desc, String author, String instructor, double price) {
		Course course = new Course(title, desc, author, instructor, price);
		coursesHashMap.put(nextIndex, course);
		++nextIndex;
	}

	/**
	 * @param title
	 * @param desc
	 * @param author
	 * @param instructor
	 * @param price
	 * @see Creates a new course and adds it to the collection.
	 */
	public void createCourse(Course course) {
		coursesHashMap.put(nextIndex, course);
		++nextIndex;
	}

	/**
	 * @param withAttribute
	 * @see Param might be Course title, description, author, or instructor Strings.
	 *      Deletes the first element with the specified String attribute that was
	 *      passed from the collection.
	 */
	public void deleteCourse(String withAttribute) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		deleteMe: for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (courseEntry.getValue().getTitle().equalsIgnoreCase(withAttribute)
					|| courseEntry.getValue().getDesc().equalsIgnoreCase(withAttribute)
					|| courseEntry.getValue().getAuthor().equalsIgnoreCase(withAttribute)
					|| courseEntry.getValue().getInstructor().equalsIgnoreCase(withAttribute)) {
				set.remove(courseEntry);// remove it permanently
				--nextIndex; // one less course
				break deleteMe; // break the named loop
			}
		}
	}

	/**
	 * @param price
	 * @see Deletes the first element with the specified <code>price</code> that was
	 *      passed from the collection.
	 */
	public void deleteCourse(double price) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		deleteMe: for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			// operation used in generated <code>Course.equals()</code> method.
			if (matchesPrice(courseEntry.getValue(), price)) {
				set.remove(courseEntry);// remove it permanently
				--nextIndex; // one less course
				break deleteMe; // break the named loop
			}

		}
	}

	/**
	 * @param course
	 * @see Deletes the first element that matches the passed Course from the
	 *      collection.
	 */
	public void deleteCourse(Course course) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		deleteMe: for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			// operation used in generated <code>Course.equals()</code> method.
			if (courseEntry.getValue().equals(course)) {
				set.remove(courseEntry);// remove it permanently
				--nextIndex; // one less course
				break deleteMe; // break the named loop
			}

		}
	}

	/**
	 * @param from
	 * @param to
	 * @see Param might be Course title, description, author, or instructor Strings.
	 *      Updates the first element in the collection with the specified String
	 *      attribute that was passed (from -> to).
	 */
	public void updateCourse(String from, String to) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		updateMe: for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (courseEntry.getValue().getTitle().equalsIgnoreCase(from)) {
				Course course = courseEntry.getValue();
				course.setTitle(to);
				courseEntry.setValue(course);
				break updateMe; // break the named loop
			} else if (courseEntry.getValue().getDesc().equalsIgnoreCase(from)) {
				Course course = courseEntry.getValue();
				course.setDesc(to);
				courseEntry.setValue(course);
				break updateMe; // break the named loop
			} else if (courseEntry.getValue().getAuthor().equalsIgnoreCase(from)) {
				Course course = courseEntry.getValue();
				course.setAuthor(to);
				courseEntry.setValue(course);
				break updateMe; // break the named loop
			} else if (courseEntry.getValue().getInstructor().equalsIgnoreCase(from)) {
				Course course = courseEntry.getValue();
				course.setInstructor(to);
				courseEntry.setValue(course);
				break updateMe; // break the named loop
			} else {
				// no match found
			}

		}
	}

	/**
	 * @param price
	 * @see Updates the first element in the collection with the specified
	 *      <code>price</code> attribute that was passed (from -> to).
	 */
	public void updateCourse(double fromPrice, double toPrice) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		updateMe: for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			// operation used in generated <code>Course.equals()</code> method.
			double currentPrice = courseEntry.getValue().getPrice();
			if (Double.doubleToLongBits(currentPrice) == Double.doubleToLongBits(fromPrice)) {
				Course course = courseEntry.getValue();
				course.setPrice(toPrice);
				courseEntry.setValue(course);
				break updateMe; // break the named loop
			}

		}
	}

	/**
	 * @param findMe
	 * @see Param might be Course title, description, author, or instructor Strings.
	 *      Returns true if an element with the specified String attribute exists in
	 *      the collection.
	 */
	public boolean searchCourse(String findMe) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (matchesAnyStringAttributes(courseEntry.getValue(), findMe)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param findMe
	 * @see Returns true if an element with the specified price attribute exists in
	 *      the collection.
	 */
	public boolean searchCourse(double findMe) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (matchesPrice(courseEntry.getValue(), findMe)) {
				return true;
			}
		}
		return false;
	}

	// Getters / Setters

	/**
	 * @param getMe
	 * @see Param might be Course title, description, author, or instructor Strings.
	 *      Gets an element with the specified String attribute if it exists in the
	 *      collection. Returns <code>null</code> otherwise.
	 */
	public Course getCourse(String getMe) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (matchesAnyStringAttributes(courseEntry.getValue(), getMe)) {
				return courseEntry.getValue();
			}
		}
		return null;
	}

	/**
	 * @param getMe
	 * @see Gets an element with the specified price attribute if it exists in the
	 *      collection. Returns <code>null</code> otherwise.
	 */
	public Course getCourse(double getMe) {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		for (HashMap.Entry<Integer, Course> courseEntry : set) {
			/** @see */
			if (matchesPrice(courseEntry.getValue(), getMe)) {
				return courseEntry.getValue();
			}
		}
		return null;
	}

	/**
	 * @param course
	 * @param matchMe
	 * @return <code>true</code> if any String attribute of
	 *         <code>course.equals(matchMe)</code>
	 */
	public boolean matchesAnyStringAttributes(Course course, String matchMe) {
		return course.getTitle().equalsIgnoreCase(matchMe) || course.getDesc().equalsIgnoreCase(matchMe)
				|| course.getAuthor().equalsIgnoreCase(matchMe) || course.getInstructor().equalsIgnoreCase(matchMe);
	}

	/**
	 * @param course
	 * @param matchMe
	 * @return <code>true</code> if the price of the <code>course</code> ==
	 *         <code>matchMe</code>
	 */
	public boolean matchesPrice(Course course, double matchMe) {
		return Double.doubleToLongBits(course.getPrice()) == Double.doubleToLongBits(matchMe);
	}

	/**
	 * @return the coursesHashMap
	 */
	public HashMap<Integer, Course> getCoursesHashMap() {
		return coursesHashMap;
	}

	/**
	 * @param coursesHashMap the coursesHashMap to set
	 */
	public void setCoursesHashMap(HashMap<Integer, Course> coursesHashMap) {
		this.coursesHashMap = coursesHashMap;
	}

	/**
	 * @return the nextIndex
	 */
	public int getNextIndex() {
		return nextIndex;
	}

	/**
	 * @param nextIndex the nextIndex to set
	 */
	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	public File getCoursesFile() {
		return coursesFile;
	}

	public void setCoursesFile(File coursesFile) {
		this.coursesFile = coursesFile;
	}

	public static String getFilenameString() {
		return FILENAME_STRING;
	}

	/**
	 * @param nextIndex the nextIndex to set
	 */
	public String toString() {
		Set<Entry<Integer, Course>> set = coursesHashMap.entrySet();
		StringBuffer buffer = new StringBuffer("");
		set.forEach((element) -> buffer.append(element.toString() + "\n"));
		return buffer.toString();
	}
}
