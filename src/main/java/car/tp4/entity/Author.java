package car.tp4.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Sami BARCHID
 * 
 *         Represents the author entity in the database
 */
@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String lastname;

	private String firstname;

	private Collection<Book> books = new ArrayList<Book>();

	public Author() {
		super();
	}

	/**
	 * @param lastname
	 * @param firstname
	 */
	public Author(String lastname, String firstname) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the books
	 */
	public Collection<Book> getBooks() {
		return books;
	}

	/**
	 * Adds a book in the book collection of the current author
	 * 
	 * @param book the book to add to the book collection of the current author
	 */
	public void addBook(Book book) {
		if (book.getAuthor() == null || book.getAuthor().getId() != this.id) {
			book.setAuthor(this);
		}
		this.books.add(book);
	}

	/**
	 * Removes the specified book from the book collection of the current author
	 * 
	 * @param book the book to remove
	 */
	public void removeBook(Book book) {
		if (book.getAuthor() != null && book.getAuthor().getId() == this.id) {
			book.setAuthor(null);
		}
		this.books.remove(book);
	}
}
