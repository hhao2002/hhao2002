package cn.hnzj.hhao.exception;

/**
 * Title: ParamDaoException Description: 数据库没有成功连接时的报错
 * 
 * @author HhaoAn
 * @date 2021年12月10日
 */
public class C3P0ParamDaoException extends DaoException {

	/** serialVersionUID： */
	private static final long serialVersionUID = 1L;

	/**
	 * Title: Description: 报错信息
	 */
	public C3P0ParamDaoException() {
		// TODO Auto-generated constructor stub
		super("无法连接数据库，请检查您的c3p0-config文件内的参数是否正确的与数据库匹配,或更改了c3p0-config文件的名称，位置等");
	}
}
