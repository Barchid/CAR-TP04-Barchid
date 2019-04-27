<%@page import="car.tp4.entity.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book recap</title>
        <%@include file="imports.jsp" %>
    </head>
    <body>
    	<%
    		// Retrieve the book under modification
    		Book book = (Book) request.getAttribute("book");
    	%>
		<div class="container-fluid">
		<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						Book recap
					</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<table class="table">
						<tr>
							<th>
								Author
							</th>
							<td>
								<%= book.getAuthor().getFirstname() + " " + book.getAuthor().getLastname() %>
							</td>
						</tr>
						<tr>
							<th>
								Title
							</th>
							<td>
								<%= book.getTitle() %>
							</td>
						</tr>
						<tr>
							<th>
								Year of production
							</th>
							<td>
								<%= book.getYear() %>
							</td>
						</tr>
						<tr>
							<th>
								Quantity available in stock
							</th>
							<td>
								<%= book.getQuantity() %>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-md-3"></div>
			</div>
			<%if(book.getQuantity() > 0) { %>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<form method="post" action="/cart-add">
						<input type="hidden" value="<%=book.getId()%>" name="id">
						<div class="form-row">
							<div class="form-group col-md-6">
								<select class="form-control" name="quantity" id="quantity">
									<%for(int i=book.getQuantity();i>0;i--){ %>
									<option selected value="<%=i%>"><%=i%></option>
									<%} %>
								</select>
							</div>
							
							<div class="form-group col-md-6">
								<button type="submit" class="btn btn-primary btn-block">
									Add to cart
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-3"></div>
			</div>
			<%} %>
		</div>
    </body>
</html>
