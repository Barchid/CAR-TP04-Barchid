
package car.tp4.managers;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import car.tp4.entity.Author;
import car.tp4.entity.AuthorBean;
import car.tp4.entity.Book;
import car.tp4.entity.BookBean;

/**
 * @author Sami BARCHID
 *
 *         EJB session that represents the services used to start the book store
 *         application.
 */
@Stateless
public class BookManager {
	@EJB
	private BookBean bookBean;

	@EJB
	private AuthorBean authorBean;

	/**
	 * Initializes several books and authors to start the book store application
	 */
	public void initBooks() {
		System.out.println(this.bookBean);
		Author rowling = new Author("Rowling", "J.K.");
		Author tolkien = new Author("J.R.R", "Tolkien");
		Author robillard = new Author("Robillard", "Anne");
		Author zemmour = new Author("Zemmour", "Eric");

		Book hp1 = new Book("Harry Potter l'apprenti sorcier", 1997, rowling, 5);
		Book hp2 = new Book("Harry Potter et la chambre des secrets", 1998, rowling, 5);
		Book hp3 = new Book("Harry Potter et le prisonnier d'Azkaban", 1999, rowling, 5);
		Book hp4 = new Book("Harry Potter et la coupe de feu", 2000, rowling, 5);
		Book hp5 = new Book("Harry Potter et l'ordre du Phoenix", 2001, rowling, 5);
		Book hp6 = new Book("Harry Potter et le titre que personne ne connaît", 2002, rowling, 5);
		Book hp7 = new Book("Harry Potter et les reliques de la mort", 2003, rowling, 5);
		Book sda = new Book("Le seigneur des anneaux", 2000, tolkien, 10);
		Book em1 = new Book("Les chevaliers d'émeraude : Le feu dans le ciel", 2003, robillard, 23);
		Book em2 = new Book("Les chevaliers d'émeraude : Les dragons de l'empereur noir", 2003, robillard, 15);
		Book em3 = new Book("Les chevaliers d'émeraude : Piège au royaume des ombres", 2003, robillard, 15);
		Book em4 = new Book("Les chevaliers d'émeraude : La princesse rebelle", 2004, robillard, 15);
		Book em5 = new Book("Les chevaliers d'émeraude : L'île des lézards", 2004, robillard, 15);
		Book em6 = new Book("Les chevaliers d'émeraude : Le journal d'Onyx", 2004, robillard, 15);
		Book em7 = new Book("Les chevaliers d'émeraude : L'enlèvement", 2005, robillard, 15);
		Book em8 = new Book("Les chevaliers d'émeraude : Les dieux déchus", 2006, robillard, 15);
		Book em9 = new Book("Les chevaliers d'émeraude : L'héritage de Danalieth", 2006, robillard, 15);
		Book em10 = new Book("Les chevaliers d'émeraude : Représailles", 2007, robillard, 15);
		Book em11 = new Book("Les chevaliers d'émeraude : La justice céleste", 2007, robillard, 15);
		Book em12 = new Book("Les chevaliers d'émeraude : Irianeth", 2008, robillard, 15);
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);

		this.authorBean.addAuthor(rowling);
		this.authorBean.addAuthor(tolkien);
		this.authorBean.addAuthor(zemmour);
		this.authorBean.addAuthor(robillard);

		this.bookBean.addBook(hp1);
		this.bookBean.addBook(hp2);
		this.bookBean.addBook(hp3);
		this.bookBean.addBook(hp4);
		this.bookBean.addBook(hp5);
		this.bookBean.addBook(hp6);
		this.bookBean.addBook(hp7);

		this.bookBean.addBook(em1);
		this.bookBean.addBook(em2);
		this.bookBean.addBook(em3);
		this.bookBean.addBook(em4);
		this.bookBean.addBook(em5);
		this.bookBean.addBook(em6);
		this.bookBean.addBook(em7);
		this.bookBean.addBook(em8);
		this.bookBean.addBook(em9);
		this.bookBean.addBook(em10);
		this.bookBean.addBook(em11);
		this.bookBean.addBook(em12);

		this.bookBean.addBook(zem);

		this.bookBean.addBook(sda);
	}

	/**
	 * Finds the different authors of the stored books (distinct) and return a list
	 * of them
	 * 
	 * @return the list of authors that wrote some books amongst the stored ones
	 */
	public List<Author> findAuthors() {
		return this.bookBean.getAllBooks().stream().map(book -> book.getAuthor()).distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Creates or updates the book specified by its parameters
	 * 
	 * @param id       the id of the book to update. If this id == 0, then the book
	 *                 will be created
	 * @param title    the title of the book
	 * @param year     the release year of the book
	 * @param authorId the id of the related author
	 * @param quantity the quantity of books available
	 * @return boolean true if the book has been correctly created/updated or else
	 *         false
	 */
	public boolean createOrUpdateBook(int id, String title, int year, int authorId, int quantity) {
		Author author = this.authorBean.findById(authorId);
		if (author == null || title.trim().equals("") || year < 0 || quantity < 0) {
			return false;
		}

		if (id == 0) {
			Book book = new Book();
			book.setTitle(title);
			book.setYear(year);
			book.setAuthor(author);
			book.setQuantity(quantity);
			this.bookBean.addBook(book);
		} else {
			Book book = this.bookBean.findById(id);
			if (book == null) {
				return false;
			}

			book.setTitle(title);
			book.setYear(year);
			book.setQuantity(quantity);
			book.setAuthor(author);
			this.bookBean.updateBook(book);
		}

		return true;
	}

	/**
	 * @return the bookBean
	 */
	public BookBean getBookBean() {
		return bookBean;
	}

	/**
	 * @param bookBean the bookBean to set
	 */
	public void setBookBean(BookBean bookBean) {
		this.bookBean = bookBean;
	}

	/**
	 * @return the authorBean
	 */
	public AuthorBean getAuthorBean() {
		return authorBean;
	}

	/**
	 * @param authorBean the authorBean to set
	 */
	public void setAuthorBean(AuthorBean authorBean) {
		this.authorBean = authorBean;
	}
}
