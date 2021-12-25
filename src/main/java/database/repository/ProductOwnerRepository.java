package database.repository;

import database.config.Database;
import database.model.ProductOwner;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductOwnerRepository {

	private static final String SELECT_ALL = "SELECT * FROM PRODUCTOWNERS";
	private static final String INSERT = "INSERT INTO PRODUCTOWNERS(USERID, USERNAME, PHONENUMBER, PRODUCTS)" +
		" VALUES(?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM PRODUCTOWNERS WHERE USERID=?;";

	@SneakyThrows
	public List<ProductOwner> getAllOwner() {
		try (Statement statement = Database.getInstance().getConn().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			List<ProductOwner> owners = new ArrayList<>();
			while (resultSet.next()) {
				String userId = resultSet.getString("USERID");
				String username = resultSet.getString("USERNAME");
				String phoneNumber = resultSet.getString("PHONENUMBER");
				String products = resultSet.getString("PRODUCTS");
				owners.add(new ProductOwner(userId, username, phoneNumber, products));
			}
			return owners;
		}
	}

	@SneakyThrows
	public void saveProductOwner(ProductOwner productOwner) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(INSERT)) {
			preparedStatement.setString(1, productOwner.getUserID());
			preparedStatement.setString(2, productOwner.getUsername());
			preparedStatement.setString(3, productOwner.getPhoneNumber());
			preparedStatement.setString(4, productOwner.getProductList());
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public void deleteProductOwner(ProductOwner productOwner) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE)) {
			preparedStatement.setString(1, productOwner.getUserID());
			preparedStatement.execute();
		}
	}
}
