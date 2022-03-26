package cn.hnzj.hhao.bean;

/**
 * Title: user Description: userbean
 * 
 * @author HhaoAn
 * @date 2021年12月6日
 */
public class UserBean extends BaseBean<UserBean> {
	private int userid;
	private String username;
	private String password;

	/**
	 * Title: Description:
	 */
	public UserBean() {
		super();
	}

	/**
	 * Title: Description:
	 * 
	 * @param userid
	 * @param username
	 * @param password
	 */
	public UserBean(int userid, String username, String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc) Title: toString Description:
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBean [userid=" + userid + ", username=" + username + ", password=" + password + "]";
	}
}
