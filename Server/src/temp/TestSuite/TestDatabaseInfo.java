package TestSuite;

import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivy Bridge
 */
public class TestDatabaseInfo {
	String url = "jdbc:mysql://localhost:3306/alaska?user=root";
	String username = "root";
	String password = "";
	
	public TestDatabaseInfo() {	
	}
	
	public DataSource getDataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(url);
		ds.setUser(username);
		ds.setPassword(password);
		return ds;
	}
}
