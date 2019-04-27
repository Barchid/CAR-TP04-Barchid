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
    		// Retrieve the author to create
    		Author author = (Author) request.getAttribute("author");
    	%>
    	<div class="container-fluid">
    		<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-center">
						Author form
					</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<form role="form" method="POST" action="/author-form">
						<div class="form-group">
							 <input type="hidden" name="id" value="<%= author.getId()%>"/>
							<label for="firstname">
								Firstname
							</label>
							<input type="text" name="firstname" value="<%=author.getFirstname() %>" class="form-control" id="firstname" required/>
						</div>
						<div class="form-group">
							<label for="lastname">
								Lastname
							</label>
							<input type="text" name="lastname" value="<%=author.getLastname() %>" class="form-control" id="lastname" required/>
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
