package car.tp4.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import car.tp4.entity.Book;
import car.tp4.entity.BookBean;
import car.tp4.entity.Order;
import car.tp4.entity.OrderBean;
import car.tp4.entity.OrderLine;
import car.tp4.entity.OrderLineBean;

/**
 * @author Sami BARCHID
 *
 *         The cart session of a user to buy books in the book store
 *         application.
 */
@Stateful
@Local
public class Cart {
	public static final String SESSION_KEY = "cart-session";

	@EJB
	private BookBean bookBean;

	@EJB
	private OrderBean orderBean;

	@EJB
	private OrderLineBean orderLineBean;

	/**
	 * Map that contains the book ids selected by the current session's user and the
	 * quantity of books related.
	 */
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();

	/**
	 * Adds a new book id into the map of current books
	 * 
	 * @param bookId   the id of the book to add
	 * @param quantity the quantity of book to add
	 * @return boolean true if there is enough stock available to add the item into
	 *         the cart or else false (false too when the book does not exist)
	 */
	public boolean addItem(int bookId, int quantity) {
		Book book = this.bookBean.findById(bookId);
		if (book == null || book.getQuantity() < quantity) {
			return false;
		}

		this.items.put(bookId, quantity);
		return true;
	}

	/**
	 * Removes the specified of the book from the cart session. If the quantity is
	 * greater than the quantity contained in the cart, it will simply remove the
	 * entry.
	 * 
	 * @param bookId   the id of the book to remove
	 * @param quantity the quantity of books to remove
	 * @return true if the removal operation is sucessful or false if the cart does
	 *         not contain the specified book
	 */
	public boolean removeItem(int bookId, int quantity) {
		if (!this.items.containsKey(bookId)) {
			return false;
		}

		int quantityContained = this.items.get(bookId);
		if (quantityContained <= quantity) {
			this.items.remove(bookId);
			return true;
		} else {
			this.items.put(bookId, quantityContained - quantity);
			return true;
		}
	}

	/**
	 * Gets the cart map composed of the books entity (key) linked to the quantity
	 * in the cart (value).
	 * 
	 * @return the cart map
	 */
	public Map<Book, Integer> getCart() {
		Map<Book, Integer> cart = new HashMap<Book, Integer>();
		for (Entry<Integer, Integer> entry : this.items.entrySet()) {
			Book book = this.bookBean.findById(entry.getKey());
			if (book == null) {
				continue;
			}

			cart.put(book, entry.getValue());
		}

		return cart;
	}

	/**
	 * Creates the order for the current user based on the current cart
	 * 
	 * @param email the email address of the owner
	 * @return the order made by the user or null if the cart was empty at first or
	 *         if there is not enough quantity available in stock
	 */
	public Order confirmOrder(String email) {
		if (this.items.isEmpty()) {
			return null;
		}

		Order order = new Order(email);
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		for (Entry<Integer, Integer> entry : this.items.entrySet()) {
			Book book = this.bookBean.findById(entry.getKey());
			if (book == null) {
				return null;
			}
			if (book.getQuantity() < entry.getValue()) {
				return null;
			}

			OrderLine orderLine = new OrderLine(book, entry.getValue(), order);
			orderLines.add(orderLine);
		}

		this.orderBean.makeOrder(order, orderLines);
		this.end();
		return order;
	}

	@Remove
	private void end() {
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
	 * @return the orderBean
	 */
	public OrderBean getOrderBean() {
		return orderBean;
	}

	/**
	 * @param orderBean the orderBean to set
	 */
	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	/**
	 * @return the orderLineBean
	 */
	public OrderLineBean getOrderLineBean() {
		return orderLineBean;
	}

	/**
	 * @param orderLineBean the orderLineBean to set
	 */
	public void setOrderLineBean(OrderLineBean orderLineBean) {
		this.orderLineBean = orderLineBean;
	}
}
