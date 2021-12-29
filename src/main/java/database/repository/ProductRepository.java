package database.repository;

import database.config.Database;
import database.model.Product;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCTS";
	private static final String INSERT = "INSERT INTO PRODUCTS(product_id, title, description, price)" +
		" VALUES(?, ?, ?, ?)";

	private static final String DELETE = "DELETE FROM PRODUCTS WHERE product_id=?;";
	private static final String DELETE_ALL = "DELETE FROM PRODUCTS;";

	@SneakyThrows
	public List<Product> getAllProduct() {
		try (Statement statement = Database.getInstance().getConn().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				String productID = resultSet.getString("product_id");
				String title = resultSet.getString("title");
				String description = resultSet.getString("description");
				String price = resultSet.getString("price");
				products.add(new Product(productID, title, description, price));
			}
			return products;
		}
	}

	@SneakyThrows
	public void saveProduct(Product product) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(INSERT)) {
			preparedStatement.setString(1, product.getProductID());
			preparedStatement.setString(2, product.getTitle());
			preparedStatement.setString(3, product.getDescription());
			preparedStatement.setString(4, product.getPrice());
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public void deleteProduct(Product product) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE)) {
			preparedStatement.setString(1, product.getProductID());
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public void deleteAllProduct() {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE_ALL)) {
			preparedStatement.execute();
		}
	}
}
