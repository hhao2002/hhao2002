package cn.hnzj.hhao.exception;

import java.util.List;

/**
 * Title: TableNameDaoException Description: 未在相应数据库下找到表名
 * 
 * @author HhaoAn
 * @date 2021年12月12日
 */
public class TableNameDaoException extends DaoException {

	/** serialVersionUID： */
	private static final long serialVersionUID = 1L;

	public TableNameDaoException() {
		// TODO Auto-generated constructor stub
		super("表名与数据库内表名不对应（如果您未设置表名需要bean类名与表名相同，或在继承类上设置tableName属性，或当前的数据库下并没有这个表）");
	}

	public TableNameDaoException(List<String> tableNames, String tableName) {
		// TODO Auto-generated constructor stub
		super("未读取到对应的表，当前数据库下存在表有：" + tableNames + "当前读取到的表名为：" + tableName + "（如果您需要设置" + tableName
				+ "的类名与数据库内的表名相同，或在继承BaseBean的类的构造方法上调用父类的有参构造方法（设置表名））");
	}
}
