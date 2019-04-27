<nav class="navbar navbar-expand-lg navbar-light bg-light navbar-dark bg-dark fixed-top">		 
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		<span class="navbar-toggler-icon"></span>
	</button> <a class="navbar-brand" href="/">Home</a>
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="navbar-nav">
			<li class="nav-item">
				 <a class="nav-link" href="/books">View books</a>
			</li>
			<li class="nav-item">
				 <a class="nav-link" href="/book-form">Create book</a>
			</li>
			
			<li class="nav-item">
				 <a class="nav-link" href="/author-form">Create author</a>
			</li>
			
			<li class="nav-item">
				 <a class="nav-link" href="/authors">View authors</a>
			</li>
			
			<li class="nav-item">
				 <a class="nav-link" href="/book-year">Books ordered by release year</a>
			</li>
		</ul>
		<form class="form-inline" onsubmit="event.preventDefault()">
			<input id="title-input" class="form-control mr-sm-2" type="text" /> 
			<button onclick="onSearchTitle()" class="btn btn-primary my-2 my-sm-0" type="submit">
				Search with title
			</button>
		</form>
		<ul class="navbar-nav ml-md-auto">
			<li class="nav-item">
				 <a class="nav-link" href="/cart">Cart</a>
			</li>
			
			<li class="nav-item">
				 <a class="nav-link" href="/orders">View orders</a>
			</li>
		</ul>
	</div>
	<script>
	function onSearchTitle() {
		var term = document.getElementById('title-input').value;
		console.log(term);
		window.location.replace("/book-title?title=" + term);
	}
	</script>
</nav>