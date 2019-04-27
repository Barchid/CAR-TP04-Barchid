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
            Collection<Book> books = (Collection<Book>) request.getAttribute("books");
        %>
        
        
        
        <div class="container-fluid">
        	<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						<%if(request.getParameter("title")!=null) { %>
						 Books found with criteria : "<%= request.getParameter("title") %>"
						<%} else { %>
						Books ordered by year
						<%} %>
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
									Quantity
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
								<%= book.getAuthor().getFirstname() + " " + book.getAuthor().getLastname() %>
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
