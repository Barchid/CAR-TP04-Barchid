<%@page import="java.util.List"%>
<%@page import="car.tp4.entity.OrderLine"%>
<%@page import="car.tp4.entity.Order"%>
<%@page import="car.tp4.entity.Book"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <%@include file="imports.jsp" %>
    </head>
    <body>
        <%
            Order order = (Order) request.getAttribute("order");
        	List<OrderLine> orderLines = (List<OrderLine>) request.getAttribute("orderLines");
        %>
        
        <div class="container-fluid">
        	<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						Order #<%=order.getId() %> (from "<%=order.getOwner() %>")
					</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table id="books" class="table table-hover table-striped">
						<thead>
							<tr>
								<th>
									ID
								</th>
								<th>
									Author
								</th>
								<th>
									Title
								</th>
								<th>
									Year of production
								</th>
								<th>
									Quantity bought
								</th>
							</tr>
						</thead>
						<tbody>
						<% for(OrderLine orderLine : orderLines){ %>
						<tr>
							<td>
								<%= orderLine.getId() %>
							</td>
							<td>
								<%= orderLine.getBook().getAuthor().getFirstname() + " " + orderLine.getBook().getAuthor().getLastname() %>
							</td>
							<td>
								<%= orderLine.getBook().getTitle() %>
							</td>
							<td>
								<%= orderLine.getBook().getYear() %>
							</td>
							<td>
								<%= orderLine.getQuantity() %>
							</td>
						</tr>
						<%}%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br><br>
    </body>
</html>
