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
	private static final String INSERT = "INSERT INTO PRODUCTOWNERS(user_id, username, product_list)" +
		" VALUES(?, ?, ?)";
	private static final String DELETE = "DELETE FROM PRODUCTOWNERS WHERE user_id=?;";
	private static final String DELETE_ALL = "DELETE FROM PRODUCTOWNERS;";

	@SneakyThrows
	public List<ProductOwner> getAllOwner() {
		try (Statement statement = Database.getInstance().getConn().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			List<ProductOwner> owners = new ArrayList<>();
			while (resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String username = resultSet.getString("username");
				String products = resultSet.getString("product_list");
				owners.add(new ProductOwner(userId, username, products));
			}
			return owners;
		}
	}

	@SneakyThrows
	public void saveProductOwner(ProductOwner productOwner) {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(INSERT)) {
			preparedStatement.setString(1, productOwner.getUserID());
			preparedStatement.setString(2, productOwner.getUsername());
			preparedStatement.setString(3, productOwner.getProductList());
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

	@SneakyThrows
	public void deleteAllProductOwner() {
		try (PreparedStatement preparedStatement = Database.getInstance().getConn().prepareStatement(DELETE_ALL)) {
			preparedStatement.execute();
		}
	}

	@SneakyThrows
	public boolean isProductOwnerUnique(ProductOwner owner) {
		for (ProductOwner ownerInDB : getAllOwner()) {
			if (owner.getUserID().equals(ownerInDB.getUserID())) {
				return false;
			}
		}
		return true;
	}
}
