<%@page import="java.util.List"%>
<%@page import="car.tp4.entity.Author"%>
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
            Author author = (Author) request.getAttribute("author");
        	List<Book> books = (List<Book>) request.getAttribute("books");
        %>
        
        <div class="container-fluid">
        	<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						Author : <%=author.getFirstname()%> <%=author.getLastname()%>
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
									Title
								</th>
								<th>
									Year of production
								</th>
								<th>
									Quantity available
								</th>
								<th>
									Action
								</th>
							</tr>
						</thead>
						<tbody>
						<% for(Book book : books){ %>
						<tr>
							<td>
								<%= book.getId() %>
							</td>
							<td>
								<%= book.getTitle() %>
							</td>
							<td>
								<%= book.getYear() %>
							</td>
							<td>
								<%= book.getQuantity() %>
							</td>
							<td>
								<a class="btn btn-secondary" href="/book-recap?id=<%=book.getId()%>">
									View
								</a>
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
