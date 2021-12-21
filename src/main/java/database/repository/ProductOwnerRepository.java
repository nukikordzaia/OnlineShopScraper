package database.repository;

import database.config.Database;
import lombok.SneakyThrows;
import database.model.ProductOwner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductOwnerRepository {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCTOWNERS";
	private static final String INSERT = "INSERT INTO PRODUCTOWNERS(USERNAME, PHONENUMBER, PRODUCTS)" +
		" VALUES(?, ?, ?)";
	private static final String DELETE = "DELETE FROM PRODUCTOWNERS WHERE PHONENUMBER=?;";

	@SneakyThrows
	public List<ProductOwner> getAllOwner() {
		try (Statement statement = Database.getInstance().getConn().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			List<ProductOwner> owners = new ArrayList<>();
			while (resultSet.next()) {
				String username = resultSet.getString("USERNAME");
				int phoneNumber = resultSet.getInt("PHONENUMBER");
				String products = resultSet.getString("PRODUCTS");
				owners.add(new ProductOwner(username, phoneNumber, products));
			}
			return owners;
		}
	}

	@SneakyThrows
	public void saveProductOwner(ProductOwner productOwner) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(INSERT)) {
			preparedStatement.setString(1, productOwner.getUsername());
			preparedStatement.setInt(2, productOwner.getPhoneNumber());
			preparedStatement.setString(3, productOwner.getProductList());
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public void deleteProductOwner(ProductOwner productOwner) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE)) {
			preparedStatement.setInt(1, productOwner.getPhoneNumber());
			preparedStatement.execute();
		}
	}
}
