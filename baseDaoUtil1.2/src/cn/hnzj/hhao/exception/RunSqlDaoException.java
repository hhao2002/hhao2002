package cn.hnzj.hhao.exception;

public class RunSqlDaoException extends DaoException{

	/** serialVersionUID：*/
	private static final long serialVersionUID = 1L;

	public RunSqlDaoException(Object obj) {
		// TODO Auto-generated constructor stub
		super("请检查："+obj+"的参数是否合法（类型不匹配或id为空值）");
	}
}
