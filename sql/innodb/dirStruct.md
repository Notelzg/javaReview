# mysql 8.0目录结构
mysql8.0中，innodb的共享表空间中的， redolog、undolog、double write 都从共享表空间中
拆分出来了，拆分之后可以单独管理控制，不会相互影响, 我理解是一个解耦，随着硬件、技术的发展
以前的一些优秀设计已经成为了今天的束缚。
##  一个新的mysql数据库文件系统，文件列表
1, double wirte 写文件，不再限制大小，之前是每个文件1m，现在可以通过参数设置。
#ib_16384_0.dblwr
#ib_16384_1.dblwr

### Mac-mini.local.err
错误日志文件, 系统启动有问题，都可以在这里查看
### Mac-mini.local.pid
存放pid，用于关闭，重启服务
### binlog
binlog文件名称格式，默认自增
binlog.000002
binlog.000003
binlog.000004
binlog.index
### 证书文件,公私钥
ca-key.pem
ca.pem
client-cert.pem
client-key.pem
### innodb 重做日志文件
ib_logfile0
ib_logfile1
### ib_buffer_pool
innodb 缓冲池

### ibdata1
innodb 共享表空间
### mysql.ibd
默认mysql数据库的 ibd文件
### mysql_research
测试数据库目录
### performance_schema
性能相关的数据库, 比如当前数据库存在的锁
### 系统配置表空间目录
sys
### innodb 重做日志
用于innodb的回滚，支持事务回滚和MVCC功能
undo_001
undo_002