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

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to create or modify an author
 */
@WebServlet("/author-form")
public class AuthorFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private AuthorBean authorBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorFormServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Author author = null;
		if (request.getParameter("id") == null) {
			author = new Author();
			author.setFirstname("");
			author.setLastname("");
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			author = this.authorBean.findById(id);
		}

		if (author == null) {
			request.setAttribute("status", 404);
			request.setAttribute("errorText", "Author not found");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			request.setAttribute("author", author);
			this.getServletContext().getRequestDispatcher("/jsp/author-form.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		int id = request.getParameter("id") != null ? Integer.valueOf(request.getParameter("id")) : -1;
		if (this.authorBean.createOrUpdate(id, firstname, lastname)) {
			response.sendRedirect("/authors");
		} else {
			request.setAttribute("status", 400);
			request.setAttribute("errorText", "Écris bien fieu !");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		}
	}

}
