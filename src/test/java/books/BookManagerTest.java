package books;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import car.tp4.entity.Author;
import car.tp4.entity.AuthorBean;
import car.tp4.entity.Book;
import car.tp4.entity.BookBean;
import car.tp4.entity.OrderBean;
import car.tp4.entity.OrderLineBean;
import car.tp4.managers.BookManager;
import car.tp4.managers.Cart;

/**
 * @author Sami BARCHID
 *
 */
public class BookManagerTest {

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
	public void testInitBooksAddAuthorCalled() {
		this.bookManager.initBooks();
		Mockito.verify(this.authorBean, Mockito.times(4)).addAuthor(Mockito.any());
	}

	@Test
	public void testInitBooksAddBookCalled() {
		this.bookManager.initBooks();
		Mockito.verify(this.bookBean, Mockito.times(21)).addBook(Mockito.any());
	}

	@Test
	public void testFindAuthorsBeanCalled() {
		this.bookManager.findAuthors();
		Mockito.verify(this.bookBean).getAllBooks();
	}

	@Test
	public void testFindAuthorsAuthorsEmpty() {
		List<Book> list = new ArrayList<>();

		Mockito.when(this.bookBean.getAllBooks()).thenReturn(list);
		assertTrue(this.bookManager.findAuthors().isEmpty());
	}

	@Test
	public void testFindAuthorsAuthorsSize() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		List<Book> list = new ArrayList<>();
		list.add(zem);

		Mockito.when(this.bookBean.getAllBooks()).thenReturn(list);
		assertEquals(1, this.bookManager.findAuthors().size());
	}

	@Test
	public void testFindAuthorsAuthors() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		List<Book> list = new ArrayList<>();
		list.add(zem);

		Mockito.when(this.bookBean.getAllBooks()).thenReturn(list);
		assertEquals(zemmour, this.bookManager.findAuthors().get(0));
	}

	@Test
	public void testCreateOrUpdateBookYearNegative() {
		assertFalse(this.bookManager.createOrUpdateBook(1, "ma grosse bite", -1, 0, 0));
	}

	@Test
	public void testCreateOrUpdateBookTitleEmpty() {
		assertFalse(this.bookManager.createOrUpdateBook(1, " ", 1, 0, 0));
	}

	@Test
	public void testCreateOrUpdateBookQuantityNegative() {
		assertFalse(this.bookManager.createOrUpdateBook(1,
				"z,dze,lk,lkelk,ezlk,ez,lkez,lkz,lzl,el,ezke,lekezerlz,,rlzee,rlez,rel,zelk,ezk,leze,lez,rkez,rlkez,rlez,rlk,,lez,rlekz,rlkezrkez,rkkezrlkez,kezrkke,rkezekr,zzkr,z",
				1, 0, -1));
	}

	@Test
	public void testCreateOrUpdateBookAuthorNotFound() {
		Mockito.when(this.authorBean.findById(Mockito.anyInt())).thenReturn(null);
		assertFalse(this.bookManager.createOrUpdateBook(1, "lz,mezlzerozekznfekfferofn", 1, 1, 0));
	}

	@Test
	public void testCreateOrUpdateBookBookNotFound() {
		Author zemmour = new Author("Zemmour", "Eric");
		Mockito.when(this.authorBean.findById(Mockito.anyInt())).thenReturn(zemmour);
		Mockito.when(this.bookBean.findById(Mockito.anyInt())).thenReturn(null);
		assertFalse(this.bookManager.createOrUpdateBook(1, "lz,mezlzerozekznfekfferofn", 1, 1, 0));
	}

	@Test
	public void testCreateOrUpdateBookNotSameAuthor() {
		Author zemmour = new Author("Zemmour", "Eric");
		Mockito.when(this.authorBean.findById(1)).thenReturn(zemmour);
		Mockito.when(this.authorBean.findById(2)).thenReturn(null);
		assertFalse(this.bookManager.createOrUpdateBook(1, "lz,mezlzerozekznfekfferofn", 1, 2, 0));
	}

	@Test
	public void testCreateOrUpdateBookNotSameBook() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.authorBean.findById(1)).thenReturn(zemmour);
		Mockito.when(this.bookBean.findById(2)).thenReturn(zem);
		assertFalse(this.bookManager.createOrUpdateBook(1, "lz,mezlzerozekznfekfferofn", 1, 1, 0));
	}

	@Test
	public void testCreateOrUpdateBookCreate() {
		Author zemmour = new Author("Zemmour", "Eric");
		Mockito.when(this.authorBean.findById(Mockito.anyInt())).thenReturn(zemmour);
		assertTrue(this.bookManager.createOrUpdateBook(0, "Lol", 1, 1, 0));
	}

	@Test
	public void testCreateOrUpdateBookCreateCalled() {
		Author zemmour = new Author("Zemmour", "Eric");
		Mockito.when(this.authorBean.findById(Mockito.anyInt())).thenReturn(zemmour);
		assertTrue(this.bookManager.createOrUpdateBook(0, "Lol", 1, 1, 0));
		Mockito.verify(this.bookBean).addBook(Mockito.any());
	}

	@Test
	public void testCreateOfUpdateBookUpdate() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.authorBean.findById(1)).thenReturn(zemmour);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertTrue(this.bookManager.createOrUpdateBook(1, "Lol", 2000, 1, 69));
		Mockito.verify(this.bookBean).updateBook(Mockito.any());
	}

	@Test
	public void testCreateOfUpdateBookUpdateBookUpdated() {
		Author zemmour = new Author("Zemmour", "Eric");
		Book zem = new Book("Le suicide français", 2012, zemmour, 15);
		Mockito.when(this.authorBean.findById(1)).thenReturn(zemmour);
		Mockito.when(this.bookBean.findById(1)).thenReturn(zem);
		assertTrue(this.bookManager.createOrUpdateBook(1, "Lol", 2000, 1, 69));
		Mockito.verify(this.bookBean).updateBook(Mockito.any());
		assertEquals("Lol", zem.getTitle());
		assertEquals(2000, zem.getYear());
		assertEquals(69, zem.getQuantity());
	}
}
