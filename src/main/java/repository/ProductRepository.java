package repository;

import db.Database;
import lombok.SneakyThrows;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCTS";
	private static final String INSERT = "INSERT INTO PRODUCTS(LINK, BRAND, PROCESSORTYPE, PRICE, RAMSIZE, SSDSIZE)" +
		" VALUES(?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM PRODUCTS WHERE LINK=?;";

	@SneakyThrows
	public List<Product> getAllProduct() {
		try (Statement statement = Database.getInstance().getConn().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				String link = resultSet.getString("LINK");
				String brand = resultSet.getString("BRAND");
				String processorType = resultSet.getString("PROCESSORTYPE");
				int ramSize = resultSet.getInt("RAMSIZE");
				int ssdSize = resultSet.getInt("SSDSIZE");
				int price = resultSet.getInt("PRICE");
				products.add(new Product(link, brand, processorType, ramSize, ssdSize, price));
			}
			return products;
		}
	}

	@SneakyThrows
	public void saveProduct(Product product) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(INSERT)) {
			preparedStatement.setString(1, product.getLink());
			preparedStatement.setString(2, product.getBrand());
			preparedStatement.setString(3, product.getProcessorType());
			preparedStatement.setInt(4, product.getPrice());
			preparedStatement.setInt(5, product.getRamSize());
			preparedStatement.setInt(6, product.getSsdSize());
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public void deleteProduct(Product product) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE)) {
			preparedStatement.setString(1, product.getLink());
			preparedStatement.execute();
		}
	}
}
