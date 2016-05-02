package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Abstract class that must be implemented by all DAO's that wish to access 
 * the system's database, contains method to get configuration of the database.
 * 
 * @author Aran
 *
 */
public abstract class DAO {
	
	/**
	 * Used to get Database information.
	 * 
	 * @return DataSource Object configured with the local system's database, 
	 * as specified in META-INF/db.properties .
	 */
	protected DataSource getMySQLDataSource() {
        Properties props = new Properties();
        InputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream("META-INF/db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
}
