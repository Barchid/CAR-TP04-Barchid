package car.tp4.entity;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Sami BARCHID
 *
 *         Bean used to manage the interactions with the database for the
 *         AuthorBean
 */
@Stateless
@Local
public class AuthorBean {
	@PersistenceContext(unitName = "book-pu")
	private EntityManager entityManager;

	/**
	 * Persists a new author in the database
	 * 
	 * @param author the author to persist in the db
	 */
	public void addAuthor(Author author) {
		this.entityManager.persist(author);
	}

	/**
	 * Deletes the specified author from the db
	 * 
	 * @param author the author to delete from the db
	 */
	public void removeAuthor(Author author) {
		this.entityManager.remove(author);
	}

	/**
	 * Finds all the authors in the database
	 * 
	 * @return the list of all the authors
	 */
	public List<Author> findAll() {
		return this.entityManager.createQuery("SELECT a FROM Author AS a").getResultList();
	}

	/**
	 * FInds the author specified by its id
	 * 
	 * @param id the id of the author to find
	 * @return the author with the specified id or null if not found
	 */
	public Author findById(int id) {
		return (Author) this.entityManager.createQuery("SELECT a FROM Author AS a WHERE a.id = :id")
				.setParameter("id", id).getSingleResult();
	}

	/**
	 * Updates the author
	 * 
	 * @param author the author to update
	 */
	public void updateAuthor(Author author) {
		this.entityManager.persist(author);
	}

	/**
	 * Creates or update the author with the specified data
	 * 
	 * @param id        the id of the author if exist
	 * @param firstname the firstname to change
	 * @param lastname  the lastname
	 * @return true if the author has been successfully updated or created
	 */
	public boolean createOrUpdate(int id, String firstname, String lastname) {
		if (firstname == null || lastname == null) {
			return false;
		}

		if (id == 0) {
			Author author = new Author();
			author.setFirstname(firstname);
			author.setLastname(lastname);
			this.addAuthor(author);
		} else {
			Author author = this.findById(id);
			if (author == null) {
				return false;
			}

			author.setFirstname(firstname);
			author.setLastname(lastname);
			this.updateAuthor(author);
		}

		return true;
	}
}
