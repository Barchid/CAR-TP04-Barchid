package car.tp4.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.managers.Cart;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to add a new entry to the current cart
 */
@WebServlet("/cart-add")
public class CartAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartAddServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/books");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		int id = Integer.valueOf(request.getParameter("id"));

		this.getCart(request).addItem(id, quantity);
		response.sendRedirect("/cart");
	}

	private Cart getCart(HttpServletRequest request) throws ServletException {
		Cart cart = (Cart) request.getSession().getAttribute(Cart.SESSION_KEY);
		if (cart == null) {
			try {
				InitialContext ic = new InitialContext();
				cart = (Cart) ic.lookup("java:global//Cart");
				request.getSession().setAttribute(Cart.SESSION_KEY, cart);
			} catch (NamingException e) {
				e.printStackTrace();
				throw new ServletException("Naming exc");
			}
		}

		return cart;
	}

}
