package car.tp4.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.Author;
import car.tp4.entity.AuthorBean;
import car.tp4.entity.Book;
import car.tp4.entity.BookBean;
import car.tp4.managers.BookManager;

/**
 * Servlet implementation class BookFormServlet
 */
@WebServlet("/book-form")
public class BookFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookBean bookBean;

	@EJB
	private AuthorBean authorBean;

	@EJB
	private BookManager bookManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookFormServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Author> authors = this.authorBean.findAll();
		request.setAttribute("authors", authors);

		if (request.getParameter("id") != null) {
			int id = Integer.valueOf(request.getParameter("id"));
			Book book = this.bookBean.findById(id);
			if (book == null) {
				book = new Book();
				book.setTitle("");
				book.setYear(0);
			}
			request.setAttribute("book", book);
		} else {
			Book book = new Book();
			book.setTitle("");
			book.setYear(0);
			request.setAttribute("book", book);
		}

		this.getServletContext().getRequestDispatcher("/jsp/book-form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		int year = Integer.valueOf(request.getParameter("year"));
		int idAuthor = Integer.valueOf(request.getParameter("author"));
		int id = Integer.valueOf(request.getParameter("id"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));

		boolean result = this.bookManager.createOrUpdateBook(id, title, year, idAuthor, quantity);
		if (!result) {
			request.setAttribute("errorText", "The book is not valid.");
			request.setAttribute("status", 400);
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			response.sendRedirect("/books");
		}
	}

}
