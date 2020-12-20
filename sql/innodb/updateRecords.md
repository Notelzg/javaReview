# 修改一条记录

## 逻辑sql语句
```
1, UPDATE t set b=1000 where nornal_key = 100;
2, UPDATE t set nornal_key=1000 where uk=100;
3, UPDATE t set nornal_key=1000 where a=100;
4, UPDATE t set nornal_key=1000 where a>100;
因为数据库会自动添加事务
所以等价于
BEGIN
UPDATE t set nornal_key=1000;
commit

```
### 锁范围分析
语句1， 命中普通索引，锁定所有的索引，因为 nornal_key 是一个普通的索引，新增的key值可能在当前key值的前面或者后面，所以
为了防止出现幻读所以要给整个索引加锁；
语句2，命中唯一索引，且使用的等号，所以该记录只会存在一条，所以只要锁定一个记录就可以了。
语句3，命中主键索引,主键索引是唯一性的，且使用的等号，所以该记录只会存在一条，所以只要锁定一个记录就可以了。
语句4， 命中主键索引，但是 > 100是一个范围，所以会使用 next locking 锁定 大于100的索引。
### 隔离性 
修改结束的记录在 REPEATABLE READ 隔离级别下是无法看到的。
但是在 read Commit隔离级别下，是可以看到的。
## 物理
### double write
数据缓存先进入double weite，再写入相应的数据页
### ibddata1
只会更新相应的索引值
### t.bid
找到记录，并更新
### redolog
记录索引，记录值的修改，用于重做

### undolog
修改语句就算事务结束了，由于MVCC的存在，也是不能擅长的，否则就违反了
隔离性了。
对应的反向操作 ,
```
1, UPDATE t set b=100 where nornal_key = 100;
```
重做日志 
### binlog
就是insert 语句，这里就不再列出了，太长了，大家可以使用 
mysqlbinlog -vv binlog.000004 语句去观察，但是需要注意的是 binglog
有三种不同的记录方式，statement、row、mixed,其内容是不一样的。
### notice 
如果更新的是主键的值，数据库执行的操作是，先删除再插入，所以尽量显示的
开启事务。
## 总结
修改如果不对索引字段修改的话，是不会触发索引的更新，只会更新数据，同时数据
由于缓存的存在，所以是比较搞笑的，和一次内存写效率是一样的，但是如果存在
大量的唯一索引，那效率就很慢了。索引是一个好事，但是适合的才是最好的。

