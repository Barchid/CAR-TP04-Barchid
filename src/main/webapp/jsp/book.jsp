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
						Existing books
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
							<td>
								<a class="btn btn-info" href="/book-form?id=<%=book.getId()%>">
									Edit
								</a>
								
								<a class="btn btn-secondary" href="/book-recap?id=<%=book.getId()%>">
									View
								</a>
								
								<a class="btn btn-danger" href="/book-remove?id=<%=book.getId()%>">
									Remove
								</a>
							</td>
						</tr>
						<%}%>
						</tbody>
						<tfoot>
				            <tr>
				                <th>ID</th>
				                <th>Author</th>
				                <th>Title</th>
				                <th>Release year</th>
				                <th>Quantity</th>
				                <th></th>
				            </tr>
				        </tfoot>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<% if(books.isEmpty()) { %>
					<br>
					<a class="btn btn-danger btn-lg btn-block" href="/init-books">
						Initialize book store
					</a>
					<% } %>
				</div>
				<div class="col-md-4">
					 <br>
					<a class="btn btn-primary btn-lg btn-block" href="/book-form">
						New book
					</a>
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
