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
0, 首先声明mysql8.0之后取消了  .frm文件，数据库结构和数据都放到了ibd文中，同时DDL语句都使用
innodb引擎控制，从而支持原子性，所以创建数据库语句可以在binlog、redolog、 undolog中看到，同时会存储在mysql.ibc
文件中，如果你把一个数据库删除了， 可以通过这些文件进行灰度。同时删除mysql_research也会导致启动报错，因为8.0之后
都是独立表空间，默认表表空间文件都存放在数据库名称对应的表里面
### binlog
```
# mysqlbinlog -vv binlog.000002 | grep -10 mysql_research
mysqlbinlog -vv binlog.000004  | grep mysql_test
```
create database mysql_test DEFAULT CHARSET utf8 COLLATE utf8_general_ci
binglog会记录所有数据库操作，从这里可以看到

### mysql.ibd
会增加数据库的结构
### redo log
针对数据页的操作，page(2)  offset 20 ,set value 12, 13
mysql为了防止写入数据出现问题，所以使用 pre-log方式，先把书库写入日志文件中，写入成功才算事务执行成功，这样当
继续向磁盘写入数据的时候，即使出现了异常情况，也可以通过redo log进行恢复，保证数据库的持久性，由于redo log的特点
所以redo log 是冥等的，是覆盖更新操作，而且是针对数据页的操作。
redolog主要记录重做日志，防止断电、系统出问题之后，会对写入的数据进行

### undo log
记录回滚操作，即删除库
delete dababase mysql_test;

### 创建目录
上面是一个创建数据库的语句，当我们执行上面的sql语句的时候，会在mysql  的 dataDir目录下面
创建一个  mysql_research 的目录，
mysql 获取 dataDir目录方法
```
show variables like '%datadir%';
```
###  存储变量
同时把 charset 和 coliate 存储到全局变量中，作为该库的默认配置 如果创建表的时候不指定，则使用该配置。
可以通过以下命令查看
```
user mysql_research
show variables like '%character_set_database%';
show variables like '%collation_database%';
```

## 总结
mysql创建数据库其实是比较简单的一个事情，就是创建了一个目录，添加了几个配置，所以创建数据库的
操作是非常快的。反过来可以推断删除一个数据库也是很快的，除非表超级多导致删除目录耗时比较大。

