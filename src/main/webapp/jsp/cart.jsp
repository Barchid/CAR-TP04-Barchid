<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="car.tp4.entity.Book"%>
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
            Map<Book, Integer> cart = (Map<Book, Integer>) request.getAttribute("cart");
        %>
        
        
        
        <div class="container-fluid">
        	<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						Your cart
					</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table id="books" class="table table-hover table-striped">
						<thead>
							<tr>
								<th>
									Book
								</th>
								<th>
									Quantity
								</th>
								<th>
									Actions
								</th>
							</tr>
						</thead>
						<tbody>
						<% for(Entry<Book,Integer> entry : cart.entrySet()){ %>
						<tr>
							<td>
								<%= entry.getKey().getTitle() %>
							</td>
							<td>
								<%= entry.getValue() %>
							</td>
							<td>
								<a class="btn btn-danger" href="/cart-remove?id=<%=entry.getKey().getId()%>&quantity=<%=entry.getValue()%>">
									Remove
								</a>
							</td>
						</tr>
						<%}%>
						<%if(cart.isEmpty()){ %>
						<tr>
							<td></td>
							<td>There is nothing in your cart</td>
							<td></td>
						</tr>
						<%} %>
						</tbody>
					</table>
				</div>
			</div>
			<%if(!cart.isEmpty()){ %>
			<div class="row">
				<div class="col-md-12">
					<form action="/orders" method="post">
						<div class="form-row">
							<div class="form-group col-md-6">
								<input type="email" placeholder="Your e-mail address" class="form-control" name="email" required/> 
							</div>
							
							<div class="form-group col-md-6">
								<button type="submit" class="btn btn-primary btn-block">
									Buy
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<%} %>
		</div>
		<br><br>
    </body>
</html>
