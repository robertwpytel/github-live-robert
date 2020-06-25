package projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CourseRepoDB {
	// Attributes
	private static String db; // name

	// Constructors

	public CourseRepoDB(String db) {
		this.db = db;
	}

	// Methods

	/**
	 * @param connection
	 * @desc Sets up the database to be created and used if necessary
	 * @assumes A database connection is already established
	 */
	public void dbUSE(Connection connection) {
		try (Statement statement = connection.createStatement();) {
			// use the database
			statement.execute("USE " + this.db);
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public void dbCREATE(Connection connection) {
		try (Statement statement = connection.createStatement();) {
			// create the database if needed
			statement.execute("CREATE DATABASE IF NOT EXISTS " + this.db);
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public void dbDROP(Connection connection) {
		try (Statement statement = connection.createStatement();) {
			// create the database if needed
			statement.execute("DROP DATABASE IF EXISTS " + this.db);
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public Connection getConnection() {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.db, "root",
				"root");) {
			return connection;
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return null;
	}

	/**
	 * @param course
	 * @see Saves a course to a database
	 */
	public boolean saveCourse(Course course) {
		// connection
		try (Connection connection = this.getConnection(); Statement statement = connection.createStatement();) {
			// setup
			// dbDROP(connection);
			dbCREATE(connection);
			dbUSE(connection);

			// things to get from database
			String quote = "\"";
			String title = quote + course.getTitle() + quote;
			double price = course.getPrice();
			String author = quote + course.getAuthor() + quote;
			String instructor = quote + course.getInstructor() + quote;
			String description = quote + course.getDesc() + quote;

			// DB
			// title varchar(50) not null,
			// price double not null,
			// author varchar(50) not null,
			// instructor varchar(50) not null,
			// aDescription varchar(50) not null
			StringBuffer update = new StringBuffer();
			update.append("INSERT INTO courses (title, price, author, instructor, aDescription) " + "VALUES (" + title
					+ price + author + instructor + description + ");");
			int rows = statement.executeUpdate(update.toString());
			System.out.println(rows + " rows updated.");
		} catch (SQLException s) {
			s.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param crud
	 * @return <code>true</code> if all courses have been saved successfully to the
	 *         database.
	 */
	public boolean saveallCourses(CoursesCRUD crud) {
		// connection
		try (Connection connection = this.getConnection(); Statement statement = connection.createStatement();) {
			// setup
			// dbDROP(connection);
			dbCREATE(connection);
			dbUSE(connection);

			StringBuffer update = new StringBuffer();
			crud.getCoursesHashMap().forEach((Integer integer, Course course) -> {
				// things to get from database
				String quote = "\"";
				String title = quote + course.getTitle() + quote;
				double price = course.getPrice();
				String author = quote + course.getAuthor() + quote;
				String instructor = quote + course.getInstructor() + quote;
				String description = quote + course.getDesc() + quote;

				// DB
				// title varchar(50) not null,
				// price double not null,
				// author varchar(50) not null,
				// instructor varchar(50) not null,
				// aDescription varchar(50) not null
				update.delete(0, update.length());
				update.append("INSERT INTO courses (title, price, author, instructor, aDescription) " + "VALUES ("
						+ title + price + author + instructor + description + ");");
				try {
					statement.executeUpdate(update.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param crud
	 * @param attrString
	 * @return The course if it exists in the database.
	 */
	public Course fetchCourse(String name) {

		// connection
		try (Connection connection = this.getConnection(); Statement statement = connection.createStatement();) {
			// setup
			// dbDROP(connection);
			dbCREATE(connection);
			dbUSE(connection);

			// DB
			// title varchar(50) not null,
			// price double not null,
			// author varchar(50) not null,
			// instructor varchar(50) not null,
			// aDescription varchar(50) not null
			StringBuffer update = new StringBuffer();
			update.append("SELECT * FROM courses (title) " + "WHERE title = " + "\"" + name + ";");
			statement.execute(update.toString());
			ResultSet resultSet = statement.getResultSet();
			if (resultSet.next()) {
				// things to get from database
				String title = resultSet.getString("title");
				double price = resultSet.getDouble("price");
				String author = resultSet.getString("author");
				String instructor = resultSet.getString("instructor");
				String description = resultSet.getString("aDescription");
				resultSet.close();
				return new Course(title, description, author, instructor, price);
			}
			resultSet.close();
		} catch (SQLException s) {
			s.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * @return A <code>HashMap Integer, Course </code> that should be received by a
	 *         <code>CoursesCRUD</code> object.
	 */
	public HashMap<Integer, Course> fetchAllCourses() {

		// connection
		try (Connection connection = this.getConnection(); Statement statement = connection.createStatement();) {
			// setup
			// dbDROP(connection);
			dbCREATE(connection);
			dbUSE(connection);

			// DB
			// title varchar(50) not null,
			// price double not null,
			// author varchar(50) not null,
			// instructor varchar(50) not null,
			// aDescription varchar(50) not null
			StringBuffer update = new StringBuffer();
			update.append("SELECT * FROM courses;");
			statement.execute(update.toString());
			ResultSet resultSet = statement.getResultSet();
			CoursesCRUD crud = new CoursesCRUD();
			while (resultSet.next()) {
				// things to get from database
				String title = resultSet.getString("title");
				double price = resultSet.getDouble("price");
				String author = resultSet.getString("author");
				String instructor = resultSet.getString("instructor");
				String description = resultSet.getString("aDescription");
				resultSet.close();
				crud.createCourse(new Course(title, description, author, instructor, price));
			}
			resultSet.close();
			return crud.getCoursesHashMap();
		} catch (SQLException s) {
			s.printStackTrace();
			return null;
		}
	}
}