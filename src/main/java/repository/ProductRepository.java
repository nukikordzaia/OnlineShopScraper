package repository;

import db.Database;
import lombok.SneakyThrows;
import model.Product;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCTS";

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
}
