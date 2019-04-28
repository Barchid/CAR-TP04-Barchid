package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.Book;
import car.tp4.entity.BookBean;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to remove a book
 */
@WebServlet("/book-remove")
public class BookRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookBean bookBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookRemoveServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			request.setAttribute("status", 400);
			request.setAttribute("errorText", "No book to remove provided.");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			Book book = this.bookBean.findById(id);
			if (book == null) {
				request.setAttribute("status", 400);
				request.setAttribute("errorText", "No book to remove provided.");
				this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
			} else {
				this.bookBean.removeBook(book);
				request.setAttribute("books", bookBean.getAllBooks());
				this.getServletContext().getRequestDispatcher("/jsp/book.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
