package car.tp4.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.Book;
import car.tp4.entity.BookBean;

/**
 * Servlet implementation class BookTitleSearchServlet
 */
@WebServlet("/book-title")
public class BookTitleSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookBean bookBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookTitleSearchServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		List<Book> books = this.bookBean.findLikeTitle(title);
		if (books == null) {
			request.setAttribute("errorText", "Title missing.");
			request.setAttribute("status", 400);
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			request.setAttribute("books", books);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/book-title.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
