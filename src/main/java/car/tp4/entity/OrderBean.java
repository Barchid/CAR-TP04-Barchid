package car.tp4.entity;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * @author Sami BARCHID
 *
 *         Manages the order entity in the database
 */
@Stateless
@Local
public class OrderBean {
	@PersistenceContext(unitName = "book-pu")
	private EntityManager entityManager;

	/**
	 * Inserts the specified Order to the database
	 * 
	 * @param order the order to insert in the database
	 */
	public void addOrder(Order order) {
		this.entityManager.persist(order);
	}

	/**
	 * Removes the specified order from the database
	 * 
	 * @param order the order to remove from the database
	 */
	public void removeOrder(Order order) {
		this.entityManager.remove(order);
	}

	/**
	 * Creates the order and the related order lines in the database
	 *
	 * @param order      the order to persist
	 * @param orderLines the list of order lines related to the order to persist
	 */
	public void makeOrder(Order order, List<OrderLine> orderLines) {
		this.entityManager.persist(order);
		for (OrderLine orderLine : orderLines) {
			orderLine.getBook().setQuantity(orderLine.getBook().getQuantity() - orderLine.getQuantity());
			this.entityManager.persist(orderLine.getBook());
			this.entityManager.persist(orderLine);
		}
	}

	/**
	 * Finds all the orders that has been made
	 * 
	 * @return the list of orders in the database
	 */
	public List<Order> findAll() {
		return this.entityManager.createQuery("SELECT o FROM order_table AS o ORDER BY o.id DESC").getResultList();
	}

	/**
	 * Finds the order specified by the id in parameter
	 * 
	 * @param id the id of the order to find
	 * @return the order found or null if not exist
	 */
	public Order findById(int id) {
		return (Order) this.entityManager.createQuery("SELECT o FROM order_table AS o WHERE o.id = :id")
				.setParameter("id", id).getResultList().stream().findFirst().orElse(null);
	}
}
