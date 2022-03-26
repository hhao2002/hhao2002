package cn.hnzj.hhao.dao;

import java.sql.SQLException;

import cn.hnzj.hhao.bean.UserBean;

/**
* Title: UserDao Description: 实现用户表的基本操作
* @author HhaoAn
* @date 2021年12月10日
*/
public interface UserDao extends BaseDao<UserBean>{

	/**
	 * Title: selectByUserName Description: 通过用户名查询用户
	 */
	UserBean selectByUserName(UserBean user) throws SQLException;

}
