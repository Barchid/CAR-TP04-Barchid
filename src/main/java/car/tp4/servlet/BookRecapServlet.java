package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.BookBean;

/**
 * Servlet implementation class BookRecapServlet
 */
@WebServlet("/book-recap")
public class BookRecapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookBean bookBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookRecapServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = request.getParameter("id") != null ? Integer.valueOf(request.getParameter("id")) : 0;
		if (id == 0) {
			request.setAttribute("status", 404);
			request.setAttribute("errorText", "The book is not specified and thus cannot be found");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			request.setAttribute("book", this.bookBean.findById(id));
			this.getServletContext().getRequestDispatcher("/jsp/book-recap.jsp").forward(request, response);
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
