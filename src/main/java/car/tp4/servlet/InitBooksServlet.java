package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.managers.BookManager;

/**
 * Servlet implementation class InitBooksServlet
 */
@WebServlet("/init-books")
public class InitBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookManager bookManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitBooksServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.bookManager.initBooks();
		response.sendRedirect("/books");
	}

}
