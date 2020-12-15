# create database
以mysql8.0作为实验对象
创建库
## 从逻辑上分析
1, mysql 官网提供的创建数据库的语句模板：https://dev.mysql.com/doc/refman/8.0/en/creating-database.html
```
CREATE DATABASE [IF NOT EXISTS] database_name
[CHARACTER SET charset_name]
[COLLATE collation_name]
```

2, 现在创建一个数据库
```
create database mysql_research DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
```

3, 现在我们就可以使用这个数据库了，逻辑上这个数据库已经创建好了，可以在这个数据库里面建表。
## 从物理上分析 
上面我们使用sql语句创建了一个数据库，但是物理上发生了，什么呢。
1, 上面是一个创建数据库的语句，当我们执行上面的sql语句的时候，会在mysql  的 dataDir目录下面
创建一个  mysql_research 的目录，
mysql 获取 dataDir目录方法
```
show variables like '%datadir%';
```
2, 同时把 charset 和 coliate 存储到全局变量中，作为该库的默认配置 如果创建表的时候不指定，则使用该配置。
可以通过以下命令查看
```
user mysql_research
show variables like '%character_set_database%';
show variables like '%collation_database%';
```

## 总结
mysql创建数据库其实是比较简单的一个事情，就是创建了一个目录，添加了几个配置，所以创建数据库的
操作是非常快的。反过来可以推断删除一个数据库也是很快的，除非表超级多导致删除目录耗时比较大。

