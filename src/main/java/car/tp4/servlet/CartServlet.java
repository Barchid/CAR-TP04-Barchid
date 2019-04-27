package car.tp4.servlet;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import car.tp4.entity.Book;
import car.tp4.managers.Cart;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet that displays the current cart of the user
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<Book, Integer> map = this.getCart(request).getCart();
		request.setAttribute("cart", map);
		this.getServletContext().getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
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
				e.printStackTrace();
				throw new ServletException("Naming exc");
			}
		}

		return cart;
	}
}
