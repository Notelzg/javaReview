#
mysql 的各种配置，文件路径

## dataDir
```
# 文件路径
show variables like '%datadir%';

# 独立表空间
show VARIABLES like 'innodb_file_per_table'
```
```
## 
这个目录就是mysql配置的根目录
## binlog
## undo log 
## redo log 