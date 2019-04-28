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
 *         Represents the order entities of the database. The order are created
 *         when a logged in user has confirmed his books chosen to buy in his
 *         cart.
 */
@Entity(name = "order_table")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Collection<OrderLine> lines = new ArrayList<OrderLine>();

	private String owner;

	/**
	 * @param owner the email address of the order's owner
	 */
	public Order(String owner) {
		super();
		this.owner = owner;
	}

	public Order() {
		super();
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
	 * @return the lines
	 */
	public Collection<OrderLine> getLines() {
		return lines;
	}

	/**
	 * Adds the specified order line to the line collection of the current order
	 * 
	 * @param line the line to add
	 */
	public void addLine(OrderLine line) {
		if (this.lines.contains(line)) {
			return;
		}

		if (line.getOrder() == null || line.getOrder().id != this.id) {
			line.setOrder(this);
		}

		this.lines.add(line);
	}

	/**
	 * Removes the specified order line from the current order
	 * 
	 * @param line the order line to remove from the current order
	 */
	public void removeLine(OrderLine line) {
		if (!this.lines.contains(line)) {
			return;
		}

		if (line.getOrder() != null && line.getOrder().id == this.id) {
			line.setOrder(null);
		}

		this.lines.remove(line);
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
}
