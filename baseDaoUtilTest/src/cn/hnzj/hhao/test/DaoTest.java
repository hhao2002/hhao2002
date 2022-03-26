package cn.hnzj.hhao.test;

import cn.hnzj.hhao.bean.UserBean;
import cn.hnzj.hhao.dao.impl.UserDaoImpl;

public class DaoTest {
	public static void main(String[] args) {
		try {
			  UserBean user = new UserBean(); UserDaoImpl userDao = new UserDaoImpl();
			  user.setUserid(2); user = userDao.selectById(user);
			  System.out.println("父类方法的查询所有:\n" + userDao.selectAll());
			  System.out.println("自己的方法：通过用户名查询用户:\n" + userDao.selectByUserName(user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
