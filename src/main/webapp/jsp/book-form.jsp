<%@page import="car.tp4.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="car.tp4.entity.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book form</title>
        <%@include file="imports.jsp" %>
    </head>
    <body>
    	<%
    		// Retrieve the book under modification
    		Book book = (Book) request.getAttribute("book");
    		List<Author> authors = (List<Author>) request.getAttribute("authors");
    	%>
    	<div class="container-fluid">
    		<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-center">
						Book form
					</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<form role="form" method="POST" action="/book-form">
						<div class="form-group">
							 <input type="hidden" name="id" value="<%= book.getId()%>"/>
							<label for="title">
								Title
							</label>
							<input type="text" name="title" value="<%=book.getTitle() %>" required class="form-control" id="title" />
						</div>
						<div class="form-group">
							 
							<label for="author">
								Author
							</label>
							<select name="author" class="form-control" id="author">
							    <%for(Author author : authors){ %>
							    	<%if(book.getAuthor() ==null || author.getId() == book.getAuthor().getId()){ %>
							    		<option selected value="<%=author.getId()%>"><%= author.getFirstname() + " " + author.getLastname()%> </option>
							    	<%} else { %>
							    		<option value="<%=author.getId()%>"><%= author.getFirstname() + " " + author.getLastname()%> </option>
							    	<%} %>
							    <%} %> 
							  </select>
						</div>
						<div class="form-group">
							 
							<label for="year">
								Year of production
							</label>
							<input type="number" class="form-control" required name="year" value="<%=book.getYear() %>" id="year" />
						</div>
						
						<div class="form-group">
							 
							<label for="quantity">
								Quantity available in stock
							</label>
							<input type="number" class="form-control" required name="quantity" value="<%=book.getQuantity() %>" id="quantity" />
						</div>
						<button type="submit" class="btn btn-primary">
							Submit
						</button>
					</form>
				</div>
			</div>
		</div>
    </body>
</html>
