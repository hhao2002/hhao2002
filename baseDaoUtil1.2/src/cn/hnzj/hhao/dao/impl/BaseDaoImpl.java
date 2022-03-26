package cn.hnzj.hhao.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hnzj.hhao.dao.BaseDao;
import cn.hnzj.hhao.exception.FieldDaoException;
import cn.hnzj.hhao.exception.TableNameDaoException;
import cn.hnzj.hhao.exception.DaoException;
import cn.hnzj.hhao.util.C3P0Utils;

/**
 * Title: BaseDao Description: 进行数据库的基本操作
 * 用法:子类继承数据连接层，继承此类，泛型用要操作的表对应的bean类。并保证Bean对象和数据库的字段名一致
 * （id,<表名>id,<表名的首字母>id,<表名的首字母>-id 不区分大小写）
 * 
 * 比如：user表对应的bean类为User则泛型写<User> 《User [id,username,userpassword]》,《User
 * [userid,username,userpassword]》,《User [userId,username,userpassword]》,《User
 * [uId,username,userpassword]》,《User [u-Id,username,userpassword]》
 * 
 * 如果类名与表名不对应： 泛型依旧用对应的Bean类，但是要重新设置表名字段：在子类调用父类的有参构造方法中传入表名
 * 
 * 如：public UserDaoImpl() throws DaoException { super("user"); }
 *
 * @author HhaoAn
 * @date 2021年12月9日
 */
public class BaseDaoImpl<T> extends Thread implements BaseDao<T> {
	// 操作语句常量
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String SELECTBYID = "selectbyid";
	private static final String SELECTALL = "selectall";
	/** runner：执行sql操作 */
	private static QueryRunner runner = null;
	/** entityClass：子类的类 */
	private Class<T> entityClass = null;
	/** tableName：子类的类名(表名) */
	private String tableName = null;
	/** idIndex：子类表的id下标 */
	private int idIndex = 0;
	private Field[] fields = null;

	public BaseDaoImpl() {// 创建子类对象时会调用父类的无参构造方法，从而实现赋值
		init();// 初始化信息
		try {
			// 检测标准
			fieldCheck();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Title: Description: 提供表名，修改泛型的表名
	 * 
	 * @param tableName
	 */
	public BaseDaoImpl(String tableName) {// 创建子类对象时会调用父类的无参构造方法，从而实现赋值
		init();// 初始化信息
		this.tableName = tableName.toLowerCase();
		System.out.println(this.tableName);
		try {
			// 检测标准
			fieldCheck();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Title: insert Description: 通过对象插入数据
	 * 
	 * @param 需要添加的对象
	 * @return 执行后，影响的行数
	 */
	@Override
	public int insert(T t) {
		int row = -1;
		try {
			row = runner.update(getSql(INSERT), setArgs(INSERT, t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * Title: update Description: 通过对象更改对应id的数据
	 * 
	 * @param 需要修改的对象（需要含有id值）
	 * @return 执行后，影响的行数
	 */
	@Override
	public int update(T t) {
		int row = -1;
		try {
			row = runner.update(getSql(UPDATE), setArgs(UPDATE, t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * Title: delete Description: 通过对象的id属性删除数据
	 * 
	 * @param 需要删除的对象（需要含有id值）
	 * @return 执行后，影响的行数
	 */
	@Override
	public int delete(T t) {
		int row = -1;
		try {
			return runner.update(getSql(DELETE), setArgs(DELETE, t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * Title: selectById Description: 通过对象的id的属性查询数据
	 * 
	 * @param 需要查询的对象
	 * @return 执行后，查找的一个对象
	 */
	@Override
	public T selectById(T t) {
		try {
			t = runner.query(getSql(SELECTBYID), new BeanHandler<T>(entityClass), setArgs(SELECTBYID, t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Title: selectAll Description: 查询所有的数据
	 * 
	 * @param 需要查询的对象
	 * @return 执行后，对应表的所有信息
	 */
	@Override
	public List<T> selectAll() {
		List<T> result = null;
		try {
			result = runner.query(getSql(SELECTALL), new BeanListHandler<T>(entityClass));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Title: getSql Description: 获取sql语句
	 * 
	 * @throws Exception
	 */
	private String getSql(String operation) throws Exception {
		StringBuffer sql = new StringBuffer();
		switch (operation) {
		case INSERT:// 插入语句 insert into <table> values(?,?...);
			sql.append("insert into ").append(tableName).append(" values(");
			for (int i = 0; i < fields.length; i++)
				sql.append("?,");
			sql.deleteCharAt(sql.length() - 1);
			sql.append(");");
			break;
		case UPDATE:// 修改语句 update <table> set *=?,*=?... where <id>=?;
			sql.append("update ").append(tableName).append(" set ");
			for (int i = 0; i < fields.length; i++)
				if (i != idIndex)
					sql.append(fields[i].getName()).append("=?,");
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where ").append(fields[idIndex].getName()).append("=?;");
			break;
		case DELETE:// 删除语句 delete from <table> where <id>=?;
			sql.append("delete from ").append(tableName).append(" where ").append(fields[idIndex].getName())
					.append("=?;");
			break;
		case SELECTBYID:// 通过id查询语句 select * from <table> where <id>=?;
			sql.append("select * from ").append(tableName).append(" where ").append(fields[idIndex].getName())
					.append("=?;");
			break;
		case SELECTALL:// 查询全部语句 select * from <table>
			sql.append("select * from ").append(tableName);
			break;
		default:// 操作参数传入异常，产生错误，阻止程序继续运行
			throw new Exception("getSql方法的传入参数有误");
		}
		return sql.toString();
	}

	/**
	 * Title: setArgs Description: 把参数包装为数组
	 * 
	 * @throws Exception
	 */
	private Object[] setArgs(String operation, T t) throws Exception {
		Object[] args = null;
		switch (operation) {
		case INSERT:// 插入语句 [*,*,*...]
			args = new Object[fields.length];
			for (int i = 0; i < args.length; i++) {
				fields[i].setAccessible(true);
				args[i] = fields[i].get(t);
			}
			break;
		case UPDATE:// 修改语句 [*,*,*...,<id>]
			args = new Object[fields.length];
			for (int i = 0; i < args.length; i++) {
				fields[i].setAccessible(true);
				if (i < idIndex)
					args[i] = fields[i].get(t);
				else if (i == idIndex)
					args[args.length - 1] = fields[idIndex].get(t);
				else
					args[i - 1] = fields[i].get(t);
			}
			break;
		case DELETE:// 删除语句 删除和通过id查询都只需要<id>参数，可以共用
		case SELECTBYID:// 通过id查询语句
			fields[idIndex].setAccessible(true);
			args = new Object[] { fields[idIndex].get(t) };
			break;
		default:// 操作参数传入异常，产生错误，阻止程序继续运行
			throw new Exception("setArgs方法的第一个传入参数有误");
		}
		return args;
	}

	/**
	 * Title: init Description: 初始化信息
	 */
	@SuppressWarnings("unchecked") // 不报错提示
	private void init() {
		// 获取QueryRunner对象，进行sql语句操作
		runner = C3P0Utils.getQueryRunner();
		// 获取泛型参数
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();// this.getClass()等价于getClass();
		// 获取泛型的类
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
		// 获取子类的表名 .substring(0, getClass().getSimpleName().length() - 4)
		tableName = entityClass.getSimpleName().toLowerCase();
		// 排除常量字段
		List<Field> var = new ArrayList<Field>();
		for (Field field : entityClass.getDeclaredFields()) {// 如果不是fianl修饰字段，则添加到列表中
			if (!java.lang.reflect.Modifier.isFinal(field.getModifiers()))
				var.add(field);
		}
		fields = new Field[var.size()];
		fields = var.toArray(fields);
	}

	/**
	 * Title: fieldCheck Description: 检查格式是否匹配
	 */
	private void fieldCheck() throws DaoException {
		List<String> tableNames = C3P0Utils.getTableNames();
		// 判断表名是否存在
		if (!tableNames.contains(tableName))
			throw new TableNameDaoException(tableNames, tableName);
		List<String> columnNames = C3P0Utils.getColumnNames(tableName);
		List<String> fields = new ArrayList<String>();
		for (Field field : this.fields)
			fields.add(field.getName());
		// 判断字段名是否匹配
		if (!(fields.containsAll(columnNames) && columnNames.containsAll(fields)))
			throw new FieldDaoException(entityClass.getSimpleName(), tableName, fields, columnNames);
		// 获取id属性值的下标
		for (String field : fields) {
			if (field.equalsIgnoreCase("id") || field.equalsIgnoreCase(tableName + "id")
					|| field.equalsIgnoreCase(tableName.substring(0, 1) + "id")
					|| field.equalsIgnoreCase(tableName.substring(0, 1) + "-" + "id"))// 存在时，直接结束
				break;
			idIndex++;// 下标加以
		}
		// 下标等于字段数组的长度时，说明没有找到id属性，报错
		if (idIndex == this.fields.length)
			throw new FieldDaoException(entityClass.getSimpleName());
	}
}
