package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.BookBean;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to display the books ordered by year
 */
@WebServlet("/book-year")
public class BookYearOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookBean bookBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookYearOrderServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("books", bookBean.findOrderedByYear());
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/book-title.jsp");
		dispatcher.forward(request, response);
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
