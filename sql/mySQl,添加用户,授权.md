# mysql 权限配置
## key
mysql, 授权, 新增, root有grant权限但是无法进行授权 
## 警告
如果部署Mysql的服务器是外网可以访问的,则强烈建议不要使用默认的3306端口,还有就是root账号禁用或者设置为只能从本地登录.
不然很容易被攻击.
flush privileges;
命令和 source /etc/profile 效果一样, 只不过是更新msql的设置
## 特殊语法
```
# 忽略大小写
lower_case_table_names=1
# 开启对所有远程登录的监听
bind_address=0.0.0.0
# 查看是否监听所有ip地址,都可以进入
netstat -antpl
输出结果,0.0.0.0:3306,标识所有远程ip可以访问,如果是local/127.0.0.1 则表示只能通过本地登录mysql
tcp        0      0 0.0.0.0:3306            0.0.0.0:*               LISTEN      -         
```

## 查看当前登录用户权限
```
 # 方法1
 show grants;
 # 方法2 
 ## 切换数据库
 use mysql
 select * from user;

```
## 查看当前登录用户
```
select user();

输出结果:
| user()             |
+--------------------+
| soe@218.241.189.29 |
+--------------------+
```
## 新增一个用户
```
#方法1, 用户名:zhangsan, 密码: zhangsan
    create user zhangsan identified by 'zhangsan';

# 方法2, 授权的同时创建账号,数据库:zhangsanDb, 数据库的所有表用 * , %分号标识可以使用远程登录.
    grant all privileges on zhangsanDb.* to zhangsan@'%' identified by 'zhangsan';
# 把刚才修改的内容重新载入内存,达到及时更新,否则只能通过重启mysql进行更新
    flush privileges;
```
##　删除一个用户
```
# １，　
    delete from user where user='root'
# 2,  删除账户及权限：
    drop user 用户名@'%';
    drop user 用户名@ localhost; 
```
## root有grant权限但是无法进行授权

```
    show grants for root;
#1, 如果不是显示的  all privileges grant option ,则授权不能成功.
#2,  通过查看,root的哪个权限不是 Y
    use mydql; //切换数据库
    select * from user where user='root';
#3,  把所有不是Y的权限项,改为Y
    update  user set priv='Y' where user='root';
#4  改完之后查看root,是否拥有所有权限 all privileges  ,并且有授权的权限 grant option 
    show grants for root; 
```

