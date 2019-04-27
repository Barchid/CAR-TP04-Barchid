package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.Author;
import car.tp4.entity.AuthorBean;
import car.tp4.entity.BookBean;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to get the authors informations
 */
@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private AuthorBean authorBean;

	@EJB
	private BookBean bookBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorServlet() {
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
			request.setAttribute("authors", this.authorBean.findAll());
			this.getServletContext().getRequestDispatcher("/jsp/authors.jsp").forward(request, response);
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			Author author = this.authorBean.findById(id);
			request.setAttribute("author", author);
			request.setAttribute("books", this.bookBean.findByAuthor(author));
			this.getServletContext().getRequestDispatcher("/jsp/author.jsp").forward(request, response);
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
