<%@page import="car.tp4.entity.Author"%>
<%@page import="java.util.List"%>
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
            List<Author> authors = (List<Author>) request.getAttribute("authors");
        %>
        
        <div class="container-fluid">
        	<%@include file="navbar.jsp" %>
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">
						Existing authors
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
									Firstname
								</th>
								<th>
									Lastname
								</th>
								<th>
									Action
								</th>
							</tr>
						</thead>
						<tbody>
						<% for(Author author : authors){ %>
						<tr>
							<td>
								<%= author.getId() %>
							</td>
							<td>
								<%= author.getFirstname()%>
							</td>
							<td>
								<%= author.getLastname()%>
							</td>
							<td>
								<a class="btn btn-info" href="/author-form?id=<%=author.getId()%>">
									Edit
								</a>
								<a class="btn btn-secondary" href="/authors?id=<%=author.getId()%>">
									View
								</a>
							</td>
						</tr>
						<%}%>
						</tbody>
						<tfoot>
				            <tr>
				                <th>
									ID
								</th>
								<th>
									Firstname
								</th>
								<th>
									Lastname
								</th>
								<th>
								</th>
				            </tr>
				        </tfoot>
					</table>
				</div>
			</div>
		</div>
		<br><br>
        <script>
        $(document).ready(function() {
            // Setup - add a text input to each footer cell
            $('#books tfoot th').each( function () {
                var title = $(this).text();
                if(title !== "") {
                	$(this).html( '<input type="text" placeholder="Search '+title+'" />' );
                }
            } );
         
            // DataTable
            var table = $('#books').DataTable();
         
            // Apply the search
            table.columns().every( function () {
                var that = this;
         
                $( 'input', this.footer() ).on( 'keyup change', function () {
                    if ( that.search() !== this.value ) {
                        that
                            .search( this.value )
                            .draw();
                    }
                } );
            } );
        } );
        </script>
    </body>
</html>
