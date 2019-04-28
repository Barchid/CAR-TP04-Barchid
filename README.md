# TP4 - "Book store"

Application web de vente et d'administration de livres, auteurs et des commandes en Java sous le standard JEE

BARCHID Sami

28/04/2019

## Introduction
Cette application est un site web de gestion de livres (ainsi que du stock), de leurs auteurs et de commandes de ces livres pour tout utilisateur qui s'y connecte.

L'application respecte les standards J2E.
 
## Architecture



  - `main/java/car/tp4/entity`
    
    Contient les entités persistées et le entity bean chargé de gérer la persistence de l'entité (EJB) (exemple : `Book` entity, `BookBean`).
    
  - `main/java/car/tp4/servlet`
  
    Contient les servlets de l'application
    
  - `resources/META-INF`
    
    Contient les fichiers de configurations utiles au déploiement de la solution
    `persistence.xml` décrit comment persister les entity beans.
    
  - `webapp/jsp`
  
  Contient tous les fichiers JSP de l'application (en dehors de l'index)
  
  - `webapp/WEB-INF`
  
  Contient les fichiers de configurations de l'application web
  
  
  
#### Gestion d'erreurs
De par la spécificité du fonctionnement de Java EE et de la simplicité des cas d'utilisation demandés, il n'y a qu'une seule erreur.

```java
private Cart getCart(HttpServletRequest request) throws ServletException {
		Cart cart = (Cart) request.getSession().getAttribute(Cart.SESSION_KEY);
		if (cart == null) {
			try {
				InitialContext ic = new InitialContext();
				cart = (Cart) ic.lookup("java:global//Cart");
				request.getSession().setAttribute(Cart.SESSION_KEY, cart);
			} catch (NamingException e) {
				throw new ServletException("Naming exception");
			}
		}

		return cart;
}
```
L'erreur vient du fait que la récupération d'un EJB via le lookup JNDI peut possiblement lancer une `NamingException` si l'adresse JNDI est incorrecte. Cette exception ne devrait jamais se lancer. C'est pour cela que, dans le cas où elle est lancée, une ServletException est lancée pour signifier que l'erreur est bien problématique.
 
## Commandes disponibles

#### Déploiement de l'application
```
mvn clean package tomee:run
```

Une fois déployée, l'application est disponible à l'adresse : 
```
http://localhost:8080
```

#### Réinitialiser l'application et nettoyer les données persistées
`mvn:clean`

#### Générer la javadoc
```
mvn javadoc:javadoc
```
 
#### Exécution des tests
```
mvn test
```

## Code samples
#### Choix de l'EJB pour le panier de l'utilisateur courant
Le panier d'un utilisateur est un session bean de type stateful contenant un dictionnaire qui lie l'ID d'un livre à sa quantité voulue par l'utilisateur

```java
@Stateful
@Local
public class Cart {
	[...]

	/**
	 * Map that contains the book ids selected by the current session's user and the
	 * quantity of books related.
	 */
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();
	[...]
```

#### Stockage du panier dans la session de l'utilisateur
Stockage du panier de l'utilisateur dans sa session en réglant les problèmes de cycles de vie différents entre les servlets et les stateful session beans (le code suivant est présent dans les servlets utilisant le panier).

```java
private Cart getCart(HttpServletRequest request) throws ServletException {
		Cart cart = (Cart) request.getSession().getAttribute(Cart.SESSION_KEY);
		if (cart == null) {
			try {
				InitialContext ic = new InitialContext();
				cart = (Cart) ic.lookup("java:global//Cart");
				request.getSession().setAttribute(Cart.SESSION_KEY, cart);
			} catch (NamingException e) {
				throw new ServletException("Naming exception");
			}
		}

		return cart;
}
```

#### Destruction du panier lors de la confirmation d'une commande
Destruction du stateful session bean du panier électronique lorsqu'une commande valide est passée

```java
	/**
	 * Creates the order for the current user based on the current cart
	 * 
	 * @param email the email address of the owner
	 * @return the order made by the user or null if the cart was empty at first or
	 *         if there is not enough quantity available in stock
	 */
	public Order confirmOrder(String email) {
		if (this.items.isEmpty()) {
			return null;
		}

		Order order = new Order(email);
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		for (Entry<Integer, Integer> entry : this.items.entrySet()) {
			Book book = this.bookBean.findById(entry.getKey());
			if (book == null) {
				return null;
			}
			if (book.getQuantity() < entry.getValue()) {
				return null;
			}

			OrderLine orderLine = new OrderLine(book, entry.getValue(), order);
			orderLines.add(orderLine);
		}

		this.orderBean.makeOrder(order, orderLines);
		this.end();
		return order;
	}

	@Remove
	private void end() {
	}
```

#### Récupération d'une entité par son ID de manière non-rigide
Utilisation des fonctionnalités Java 8 (Optionnal class) pour éviter la rigidité de la récupération du résultat d'un query qui ne renvoie rien (id erroné, par exemple)

```java
	/**
	 * Finds the book specified by its id
	 * 
	 * @param id the id of the book to find
	 * @return the book found or null if not exist.
	 */
	public Book findById(int id) {
		return (Book) this.entityManager.createQuery("SELECT b FROM Book AS b WHERE b.id = :id").setParameter("id", id)
				.getResultList().stream().findFirst().orElse(null);
	}
```