package cn.hnzj.hhao.bean;

import java.lang.reflect.Field;

public class BaseBean<T> {
	public static final int FIELDONLY = 20;
	public static final int VALUEONLY = 21;
	public static final int FIELDANDVALUE = 32;

	public String toString(T t, String standard, int type) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer info = new StringBuffer();
		String[] split = standard.split("?");
		if (split.length != type / 10)
			throw new Exception();
		for (Field field : t.getClass().getDeclaredFields()) {
			info.append(split[0]);
			switch (type % 10) {
			case 0:
				info.append(field.getName()).append(split[1]);
				break;
			case 1:
				info.append(field.get(t)).append(split[1]);
				break;
			case 2:
				info.append(field.getName()).append(split[1]).append(field.get(t)).append(split[2]);
				break;
			default:
				throw new Exception();
			}
		}
		return info.toString();
	}

}
