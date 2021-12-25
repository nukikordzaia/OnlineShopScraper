package database.config;

import lombok.Getter;
import lombok.SneakyThrows;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

@Getter
public class Database {

	private static Database INSTANCE;

	private DataSource dataSource;

	private Database() {
		DBConfig config = DBConfig.getInstance();
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(config.getJdbcUrl());
		dataSource.setUser(config.getUsername());
		dataSource.setPassword(config.getPassword());
		this.dataSource = dataSource;
	}

	public static synchronized Database getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
		return INSTANCE;
	}

	@SneakyThrows
	public Connection getConn() {
		return getDataSource().getConnection();
	}
}
