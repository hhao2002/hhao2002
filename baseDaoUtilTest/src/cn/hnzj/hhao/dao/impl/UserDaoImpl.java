package cn.hnzj.hhao.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.hnzj.hhao.dao.UserDao;
import cn.hnzj.hhao.exception.DaoException;
import cn.hnzj.hhao.bean.UserBean;
import cn.hnzj.hhao.util.C3P0Utils;

public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {

	public UserDaoImpl() throws DaoException {
		super("User");
		// TODO Auto-generated constructor stub
	}

	/**
	 * Title: selectByUserName Description: 通过用户名查询用户
	 */
	@Override
	public UserBean selectByUserName(UserBean user) throws SQLException {
		return new QueryRunner(C3P0Utils.getDataSource()).query("select * from user where username=?",
				new BeanHandler<UserBean>(UserBean.class), user.getUsername());
	}

}
