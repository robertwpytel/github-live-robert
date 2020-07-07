package com.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.model.Actor;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	Connection Connection;

	@Override
	public void init() throws ServletException {
		super.init();
		// any variables to be initialized would go here.
		// Setup!
		// EX: You make a new scanner here.
	}

	// You would use the scanner in here
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Read parameters from the form
		String actorString = request.getParameter("actorName");
		String patternString = request.getParameter("searchPattern");
		int hash = patternString.hashCode();

		// sanitize the input from any odd characters
		String sanitizeString = "?.,<>`~!@#$%^&*()_+-=/\\;:\'\"[]{}| 1234567890";
		String sfString = "_or_";
		patternString = patternString.replace(sanitizeString, "");

		patternString = "'%" + patternString + "%'";
		ArrayList<Actor> arrayList = new ArrayList<Actor>();
		// Gets all actors from database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection connection =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?allowMultiQueries=true",
			// "root", "root");
			// Connection connection =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root",
			// "mysql");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "root");
			Statement statement = connection.createStatement();

			String htmlString = "<p></p>"; // this is bad practice.
			StringBuilder builder = new StringBuilder();

			// statement.executeUpdate("USE sakila;");
			// "SELECT * FROM actor WHERE first_name = 'actorString';"
			ResultSet resultSet = statement.executeQuery("SELECT * FROM actor WHERE first_name LIKE " + patternString);
			// You can't get database results without a ResultSet to hold it for you.
			while (resultSet.next()) {
				String first_name = resultSet.getString("first_name");
				String last_name = resultSet.getString("last_name");
				arrayList.add(new Actor(0, first_name));
				// builder.append(htmlString.substring(0, 3) + first_name + " " + last_name +
				// htmlString.substring(3, 7));
			}

//				while (statement.getMoreResults()) {
//					resultSet = statement.getResultSet();
//					first_name = resultSet.getString("first_name");
//					builder.append(htmlString.substring(0, 3) + first_name + htmlString.substring(3, 7));
//				}

			request.setAttribute("searchedActors", builder.toString());

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Searching for the actor
//		for (int i = 0; i < 3; ++i) {
//			// String actorName = actor.getFirst_name().substring(0, 2); // first 2 letters
//
//			// Are the first 2 letters a match?
//			if (actorID == penelopeActor.getActor_id()) {
//				// resultString[i] = actorString;
//				request.setAttribute("searchedActors", penelopeActor);
//			} else if (actorID == alpenleibeActor.getActor_id()) {
//				// resultString[i] = actorString;
//				request.setAttribute("searchedActors", alpenleibeActor);
//			} else {
//				// nothing
//			}
//
//		}
		// If the actor is not found, there is no actor in the request.
		// Send the request with the model to search-results.jsp

		request.setAttribute("actorsList", arrayList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("view/search-results.jsp");
		requestDispatcher.forward(request, response);

	}

	@Override
	public void destroy() {
		// We free up all the memory that we were using in the service() method.
		// These should be the same variables that were given memory in the init()
		// method.
		// Try thinking of it like a web-style destructor.
	}
}
