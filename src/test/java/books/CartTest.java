package books;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import car.tp4.entity.Author;
import car.tp4.entity.AuthorBean;
import car.tp4.entity.Book;
import car.tp4.entity.BookBean;
import car.tp4.entity.Order;
import car.tp4.entity.OrderBean;
import car.tp4.entity.OrderLineBean;
import car.tp4.managers.BookManager;
import car.tp4.managers.Cart;

/**
 * @author Sami BARCHID
 *
 */
public class CartTest {
	private BookBean bookBean;

	private OrderLineBean orderLineBean;

	private OrderBean orderBean;

	private AuthorBean authorBean;

	private Cart cart;

	private BookManager bookManager;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.bookBean = Mockito.mock(BookBean.class);
		this.orderLineBean = Mockito.mock(OrderLineBean.class);
		this.orderBean = Mockito.mock(OrderBean.class);
		this.authorBean = Mockito.mock(AuthorBean.class);

		this.cart = new Cart();
		this.cart.setBookBean(this.bookBean);
		this.cart.setOrderBean(this.orderBean);
		this.cart.setOrderLineBean(this.orderLineBean);

		this.bookManager = new BookManager();
		this.bookManager.setAuthorBean(this.authorBean);
		this.bookManager.setBookBean(this.bookBean);
	}

	@Test
	public void testAddItemNotFound() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(Mockito.anyInt())).thenReturn(null);
		assertFalse(this.cart.addItem(1, 3));
	}

	@Test
	public void testAddItemNotRightId() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertFalse(this.cart.addItem(2, 3));
	}

	@Test
	public void testAddItemWrongQuantity() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 1);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertFalse(this.cart.addItem(1, 3));
	}

	@Test
	public void testAddItem() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertTrue(this.cart.addItem(1, 3));
	}

	@Test
	public void testAddItemContentSize() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertTrue(this.cart.addItem(1, 3));

		Map<Book, Integer> map = this.cart.getCart();
		assertEquals(1, map.size());
	}

	@Test
	public void testAddItemContent() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertTrue(this.cart.addItem(1, 3));

		Map<Book, Integer> map = this.cart.getCart();
		assertTrue(map.containsKey(zem));
		assertEquals(3, map.get(zem).intValue());
	}

	@Test
	public void testRemoveItem() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 3));
	}

	@Test
	public void testRemoveItemContentSize() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 3));
		Map<Book, Integer> map = this.cart.getCart();
		assertEquals(0, map.size());
	}

	@Test
	public void testRemoveItemContent() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 3));
		Map<Book, Integer> map = this.cart.getCart();
		assertFalse(map.containsKey(zem));
	}

	@Test
	public void testRemoveItemQuantity() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 1));
	}

	@Test
	public void testRemoveItemQuantityContentSize() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 1));
		Map<Book, Integer> map = this.cart.getCart();
		assertEquals(1, map.size());
	}

	@Test
	public void testRemoveItemQuantityContent() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		assertTrue(this.cart.removeItem(1, 1));
		Map<Book, Integer> map = this.cart.getCart();
		assertTrue(map.containsKey(zem));
		assertEquals(2, map.get(zem).intValue());
	}

	@Test
	public void testRemoveItemNotFound() {
		Mockito.when(this.bookBean.findById(Mockito.anyInt())).thenReturn(null);
		assertFalse(this.cart.removeItem(1, 1));
	}

	@Test
	public void testConfirmOrderEmpty() {
		assertNull(this.cart.confirmOrder("lol"));
	}

	@Test
	public void testConfirmOrder() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		Order order = this.cart.confirmOrder("Lol");
		Mockito.verify(this.orderBean).makeOrder(Mockito.any(), Mockito.any());
	}

	@Test
	public void testConfirmOrderContent() {
		// Ads one item
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);

		Order order = this.cart.confirmOrder("Lol");
		assertEquals("Lol", order.getOwner());
	}

	@Test
	public void testConfirmOrderItemRemoved() {
		// Ads one item then removes it
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);
		Mockito.when(this.bookBean.findById(1)).thenReturn(null);

		assertNull(this.cart.confirmOrder("Lol"));
	}

	@Test
	public void testConfirmOrderItemLessQuantity() {
		// Ads one item then lowers the quantity available
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		this.cart.addItem(1, 3);
		zem.setQuantity(1);

		assertNull(this.cart.confirmOrder("Lol"));
	}
}
