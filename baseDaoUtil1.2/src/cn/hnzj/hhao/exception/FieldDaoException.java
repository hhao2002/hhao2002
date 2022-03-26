package cn.hnzj.hhao.exception;

import java.util.List;

/**
 * Title: FieldDaoException Description: 数据库与bean类字段问题
 * 
 * @author HhaoAn
 * @date 2021年12月12日
 */
public class FieldDaoException extends DaoException {

	/** serialVersionUID： */
	private static final long serialVersionUID = 1L;

	public FieldDaoException(String beanName) {
		// TODO Auto-generated constructor stub
		super("您的" + beanName + "类的id属性名与默认的id名（id,<表名>id,<表名的首字母>id,<表名的首字母>-id 不区分大小写）不匹配");
	}

	public FieldDaoException(String beanName, String tableName, List<String> fields, List<String> columnNames) {
		// TODO Auto-generated constructor stub
		super("您的" + beanName + "类属性名有：" + fields + "与数据库内" + tableName + "表的字段名：" + columnNames + "数量或名称不匹配");
	}

}
