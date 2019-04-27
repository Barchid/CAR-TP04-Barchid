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

import car.tp4.entity.Order;
import car.tp4.entity.OrderBean;
import car.tp4.entity.OrderLineBean;
import car.tp4.managers.Cart;

/**
 * 
 * @author Sami BARCHID
 *
 *         Servlet used to handle the buy order process for a user
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private OrderBean orderBean;

	@EJB
	private OrderLineBean orderLineBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderServlet() {
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
			request.setAttribute("orders", this.orderBean.findAll());
			this.getServletContext().getRequestDispatcher("/jsp/orders.jsp").forward(request, response);
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			Order order = this.orderBean.findById(id);
			request.setAttribute("order", order);
			request.setAttribute("orderLines", this.orderLineBean.findByOrder(order));
			this.getServletContext().getRequestDispatcher("/jsp/order.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		Order order = this.getCart(request).confirmOrder(email);
		if (order == null) {
			request.setAttribute("status", 400);
			request.setAttribute("errorText", "One of your books you want to buy is now not in stock. Try later.");
			this.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		} else {
			request.getSession().removeAttribute(Cart.SESSION_KEY);
			request.setAttribute("order", order);
			request.setAttribute("orderLines", this.orderLineBean.findByOrder(order));
			this.getServletContext().getRequestDispatcher("/jsp/order.jsp").forward(request, response);
		}
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
