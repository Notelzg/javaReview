# create table
揭开create table的面纱
## 从逻辑上分析create table 语句
```
DROP TABLE IF EXISTS `t`;
CREATE TABLE `t` (
  `a` int NOT NULL,
  `b` int DEFAULT NULL,
  `uq` varchar(255) DEFAULT NULL,
  `nornal_key` int DEFAULT NULL,
  PRIMARY KEY (`a`),
  UNIQUE KEY `uq_key_c` (`uq`) ,
  KEY(nornal_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```
### 索引
主键索引： a
唯一索引字段：uq
一般所用：normal_key
索引默认都是BTREE

创建聚餐索引和辅助索引。

## 从物理上分析create table 语句
```
show VARIABLES like 'innodb_file_per_table'
通过上面的命令查看，如果是on标识，独立表空间，每个表都会有自己的.ibd文件，否则存入共享表空间 ibddata1文件中
mysql8.0默认值为 on
```
###  表空间文件
通过 在dataDir目录下面打开，mysql_research 目录，里面生成了一个 t.ibx文件
该文件会存储表结构信息，以及数据信息，存储到一个文件中，以前是分开存储，无法保证
DDL操作的原子性，现在放入同一个文件中，方便管理恢复，也防止结构和数据不一致。

### 通过表空间恢复表
```
 ibd2sdi mysql_research/t.ibd
```
通过以上命令可以查看该表的结构，包括列、索引、默认值、编码、文件路径等所有信息都包含
是一个json结果，可以直接读取。可以通过该文件直接恢复表结构，也可以通过该文件再写sql
语句偶读可以。

### binlog
```
# at 1911
#201220 17:27:51 server id 1  end_log_pos 1990 CRC32 0x21382a4d 	Anonymous_GTID	last_committed=6	sequence_number=7	rbr_only=no	original_committed_timestamp=1608456471108068	immediate_commit_timestamp=1608456471108068	transaction_length=456
# original_commit_timestamp=1608456471108068 (2020-12-20 17:27:51.108068 CST)
# immediate_commit_timestamp=1608456471108068 (2020-12-20 17:27:51.108068 CST)
/*!80001 SET @@session.original_commit_timestamp=1608456471108068*//*!*/;
/*!80014 SET @@session.original_server_version=80022*//*!*/;
/*!80014 SET @@session.immediate_server_version=80022*//*!*/;
SET @@SESSION.GTID_NEXT= 'ANONYMOUS'/*!*/;
# at 1990
#201220 17:27:51 server id 1  end_log_pos 2367 CRC32 0x6fc18c21 	Query	thread_id=8	exec_time=0	error_code=0	Xid = 217
SET TIMESTAMP=1608456471/*!*/;
/*!80013 SET @@session.sql_require_primary_key=0*//*!*/;
CREATE TABLE `t` (
  `a` int NOT NULL,
  `b` int DEFAULT NULL,
  `uq` varchar(255) DEFAULT NULL,
  `nornal_key` int DEFAULT NULL,
  PRIMARY KEY (`a`),
  UNIQUE KEY `uq_key_c` (`uq`) ,
  KEY(nornal_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
/*!*/;
SET @@SESSION.GTID_NEXT= 'AUTOMATIC' /* added by mysqlbinlog */ /*!*/;
DELIMITER ;
# End of log file
/*!50003 SET COMPLETION_TYPE=@OLD_COMPLETION_TYPE*/;
/*!50530 SET @@SESSION.PSEUDO_SLAVE_MODE=0*/;
```
新增建表语句,比较长 
### undo 
```
delete table t;

```
删除表语句，就一句
## redolog
有待深入研究


