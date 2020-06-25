package projects;

import java.io.Serializable;

/**
 * @author Robert
 * @description The Course will be used to teach something. People will sign up
 *              in order to be taught this something.
 * @date 6/22/2020
 */
public class Course implements Serializable {
	// Attributes of the Course

	/**
	 * Generated Serial ID for serializing (storing) the class to and from files
	 */
	private static final long serialVersionUID = 1611286055750589469L;
	private String title;
	private String desc; // description
	private String author;
	private String instructor;
	private double price;

	// Constructors

	/**
	 * @param title
	 * @param desc
	 * @param author
	 * @param instructor
	 * @param price
	 */
	public Course(String title, String desc, String author, String instructor, double price) {
		this.title = title;
		this.desc = desc;
		this.author = author;
		this.instructor = instructor;
		this.price = price;
	}

	// Methods

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + (int) Double.doubleToLongBits(price);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} else if (!instructor.equals(other.instructor))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	// Getters / Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Course [title=" + title + ", desc=" + desc + ", author=" + author + ", instructor=" + instructor
				+ ", price=" + price + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
