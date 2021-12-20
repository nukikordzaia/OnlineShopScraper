package db;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

//TODO use lombok-pg
@Getter
public class DBConfig {

	private static DBConfig INSTANCE;

	private String jdbcUrl;

	private String username;

	private String password;

	@SneakyThrows
	private DBConfig() {
		Properties props = new Properties();
		try (InputStream inputStream = DBConfig.class.getResourceAsStream("/db.properties")) {
			props.load(inputStream);
		}
		jdbcUrl = props.getProperty("jdbc-url");
		username = props.getProperty("username");
		password = props.getProperty("password");
	}

	public static synchronized DBConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DBConfig();
		}
		return INSTANCE;
	}
}
