package car.tp4.entity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author Sami BARCHID
 *
 *         Bean used to manage the interactions with the database for the book
 *         entity.
 */
@Stateless
@Local
public class BookBean {

	@PersistenceContext(unitName = "book-pu")
	private EntityManager entityManager;

	public void addBook(Book book) {
		entityManager.persist(book);
	}

	public List<Book> getAllBooks() {
		Query query = entityManager.createQuery("SELECT m from Book as m");
		return query.getResultList();
	}

	/**
	 * Finds the book that contains the specified string in ther title
	 * 
	 * @param title the string that has to be contained in the book's title
	 * @return the list of books that contains the specified string in their title
	 */
	public List<Book> findLikeTitle(String title) {
		if (title == null) {
			return null;
		}

		Query query = entityManager.createQuery("SELECT m from Book as m WHERE m.title LIKE :title")
				.setParameter("title", "%" + title + "%");
		return query.getResultList();
	}

	/**
	 * finds the list of books that were written by the specified author
	 * 
	 * @param author the author that wrote the books to search
	 * @return the list of books that were writter by the specified author
	 */
	public List<Book> findByAuthor(Author author) {
		Query query = entityManager.createQuery("SELECT m from Book as m WHERE m.author = :author")
				.setParameter("author", author);
		return query.getResultList();
	}

	/**
	 * Finds the list of all stored bookds ordered by the year of production (DESC)
	 * 
	 * @return the list of all stored bookds ordered by the year of production
	 *         (DESC)
	 */
	public List<Book> findOrderedByYear() {
		return this.entityManager.createQuery("SELECT m from Book as m ORDER BY m.year DESC").getResultList();
	}

	/**
	 * Finds the book specified by its id
	 * 
	 * @param id the id of the book to find
	 * @return the book found or null if not exist.
	 */
	public Book findById(int id) {
		return (Book) this.entityManager.createQuery("SELECT b FROM Book AS b WHERE b.id = :id").setParameter("id", id)
				.getResultList().stream().findFirst().orElse(null);
	}

	/**
	 * Removes the specified book from the database
	 * 
	 * @param book the book to delete
	 */
	public void removeBook(Book book) {
		this.entityManager.remove(book);
	}

	/**
	 * Updates the specified book in the database
	 * 
	 * @param book the book to update in the database
	 */
	public void updateBook(Book book) {
		this.entityManager.persist(book);
	}
}