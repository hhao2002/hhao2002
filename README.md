内容：
  根据所学的c3p0，基于dbUtils，进行了进一步的封装
  当然，相对于框架来说，这简直拉跨太多了，我当时还没有学习到框架，就只是单纯的练习与学习。
  作用一，实现基本的数据库操作 《增上改查》(BaseDaoImpl实现了4个的方法)

用法：
1、导入相关jar包和c3p0-config.xml到项目的src目录下
  （依赖：mysql-connector-java-8.0.16.jar，mchange-commons-java-0.2.19.jar，commons-dbutils-1.7.jar，c3p0-0.9.5.5.jar，baseDaoUtil1.2.jar）
2、建数据库和表
3、修改，配置c3p0-config.xml文件
4、编写表对应的bean类（字段名要相同）
（导入baseDaoUtil.jar包）
5、编写DAO层的接口，并继承cn.hnzj.hhao.dao.BaseDao接口
6、编写DAO的事项类，并继承cn.hnzj.hhao.dao.impl.BaseDaoImpl类
  （如果自己编写的DAO事项类类名与数据库表名不同，可通过设置BaseDaoImpl的tableName属性设置，默认为DAO实现类的类名《可通过构造方法中调用super(tableName)》）

注：
（1）DAO继承时的泛型为表对应的bean类
（2）bean类要求：字段名和数量与数据库表对应，id属性名需为：（id名可以是id 或 <表名>id 或 <表名的首字母>id 或 <表名的首字母>-id   不区分大小写）
（3）如果bean类名与数据库的表名不同，需要调用父类的有参构造方法（参数为String类型<表名>），设置表名
（4）bean字段的fianl字段不会被识别。
（5）我使用的是mysql数据库，如果数据库不同，要更改数据库驱动jar包和xml对应的驱动名和值），jdk为17.0.1

作用一，（表对应的DAO继承类）
可以直接用 此类[.insert][.update][.delete][.selectById][.selectAll]
  这5个方法（继承与jar包的BaseDaoImpl类有这些方法），因此不需要编写这些方法，直接使用即可
 
作用二，(C3P0Utils)
1、提供数据源对象
2、提供数据库连接对象
3、提供QueryRunner对象
4、查询当前数据库下的所有表名
5、查询某个表的所有字段

使用方法用法：直接用C3P0Utils.<方法>
