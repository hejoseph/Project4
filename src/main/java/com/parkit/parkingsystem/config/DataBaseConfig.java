package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBaseConfig {
	
	private static final String CONSTANT_FILE="src/main/resources/constant.properties";

	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/prod"
				+ "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Europe/Paris", "root", this.getConstant("DB_PROD_PASSWORD"));
	}
	
	public String getConstant(String attribute) {
		String value = "";
		Properties constantProperties = new Properties();
		FileInputStream is = null;
		try {
			is = new FileInputStream(CONSTANT_FILE);
			constantProperties.load(is);
			if(constantProperties.containsKey(attribute)){
				value = constantProperties.getProperty(attribute);
			}else{
				logger.error("key:"+attribute+" does not exist in properties file :");
			}
		} catch (FileNotFoundException e) {
			logger.debug("file not found",e);
		} catch (IOException e) {
			logger.debug("ioexception",e);
		}finally {
			try {
				if(is!=null) {
					is.close();
				}
			} catch (IOException e) {
				logger.debug("cannot close is",e);
			}
		}
		return value;
	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
