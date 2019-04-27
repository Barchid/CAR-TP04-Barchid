package car.tp4.entity;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Sami BARCHID
 *
 *         Manages the order lines in the database
 */
@Stateless
@Local
public class OrderLineBean {
	@PersistenceContext(unitName = "book-pu")
	private EntityManager entityManager;

	/**
	 * Adds a new order line in the database
	 * 
	 * @param orderLine the orderLine to add in the database
	 */
	public void addOrderLine(OrderLine orderLine) {
		this.entityManager.persist(orderLine);
	}

	/**
	 * Removes the specified OrderLine from the database
	 * 
	 * @param orderLine the OrderLine to remove from the database
	 */
	public void removeOrderLine(OrderLine orderLine) {
		this.entityManager.remove(orderLine);
	}

	/**
	 * finds the order lines related to the order in parameter
	 * 
	 * @param order the order used to find the order lines
	 * @return the list of order lines that are found
	 */
	public List<OrderLine> findByOrder(Order order) {
		return this.entityManager.createQuery("SELECT ol FROM OrderLine AS ol WHERE ol.order = :order")
				.setParameter("order", order).getResultList();
	}
}
