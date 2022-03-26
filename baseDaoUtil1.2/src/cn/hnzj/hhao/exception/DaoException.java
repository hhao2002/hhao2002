package cn.hnzj.hhao.exception;

/**
 * Title: DaoException Description: 数据连接层的基本报错
 * 
 * @author HhaoAn
 * @date 2021年12月12日
 */
public class DaoException extends Exception {

	/** serialVersionUID： */
	private static final long serialVersionUID = 1L;

	public DaoException() {
		// TODO Auto-generated constructor stub
		super("数据库表结构和Bean类不匹配或无法连接数据库");
	}

	public DaoException(String info) {
		// TODO Auto-generated constructor stub
		super(info);
	}

}