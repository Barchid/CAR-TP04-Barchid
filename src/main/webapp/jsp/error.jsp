<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error !</title>
        <%@include file="imports.jsp" %>
    </head>
    <body>
	   <%
	   		// Retrieve the error number
	   		int error = (Integer) request.getAttribute("status");
	   		String errorText = (String) request.getAttribute("errorText");
	   		response.setStatus(error); // set status error code
	   	%>
		<div class="container-fluid">
			<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<div class="jumbotron">
						<h1>
							Error <%= error %>
						</h1>
						<p>
							<%= errorText %>
						</p>
						<p>
							<a class="btn btn-primary btn-large" href="/">Go to home</a>
						</p>
					</div>
				</div>
			</div>
		</div>
    </body>
</html>
