package cn.hnzj.hhao.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.hnzj.hhao.exception.C3P0ParamDaoException;

/**
 * Title: C3P0Utils Description: 获取数据库连接 记得根据自己的数据库调整c3p0-config.xml的参数
 * 
 * @author HhaoAn
 * @date 2021年12月6日
 */
public class C3P0Utils {
	/** ds：声明一个数据源 */
	public static DataSource ds;
	static {
		ds = new ComboPooledDataSource("test");// 创建一个test数据库数据源对象
	}

	/**
	 * Title: getDataSource Description: 获取数据源
	 * 
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * Title: getConnection Description: 获取一个连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * Title: getQueryRunner Description: 获取一个QueryRunner对象
	 * 
	 * @return QueryRunner
	 */
	public static QueryRunner getQueryRunner() {
		return new QueryRunner(ds);
	}

	/**
	 * Title: getColumnNames Description: 获取数据库下的所有表名
	 * 
	 * @return List<String>
	 * @throws C3P0ParamDaoException
	 */
	public static List<String> getTableNames() throws C3P0ParamDaoException {
		try {
			return getQueryRunner().query("show tables;", new ColumnListHandler<String>());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new C3P0ParamDaoException();
		}
	}

	/**
	 * Title: getColumnNames Description: 获取表中所有字段名称
	 * 
	 * @param tableName 表名
	 * @return List<String>
	 * @throws C3P0ParamDaoException
	 */
	public static List<String> getColumnNames(String tableName) throws C3P0ParamDaoException {
		try {
			return getQueryRunner().query("desc " + tableName, new ColumnListHandler<String>());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new C3P0ParamDaoException();
		}
	}
}
