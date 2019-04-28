package car.tp4.servlet;

import java.io.IOException;

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
 *         Servlet used to remove an item from the current cart
 */
@WebServlet("/cart-remove")
public class CartRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartRemoveServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		Cart cart = this.getCart(request);
		boolean result = cart.removeItem(id, quantity);
		if (result) {
			response.sendRedirect("/cart");
		} else {
			request.setAttribute("status", 404);
			request.setAttribute("errorText", "The item to remove from cart does not exist.");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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

	private Cart getCart(HttpServletRequest request) throws ServletException {
		Cart cart = (Cart) request.getSession().getAttribute(Cart.SESSION_KEY);
		if (cart == null) {
			try {
				InitialContext ic = new InitialContext();
				cart = (Cart) ic.lookup("java:global//Cart");
				request.getSession().setAttribute(Cart.SESSION_KEY, cart);
			} catch (NamingException e) {
				throw new ServletException("Naming exception");
			}
		}

		return cart;
	}
}
