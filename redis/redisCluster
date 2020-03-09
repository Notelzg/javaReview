# cluster 记录
redis cluster是如何实现，从数据一致性（主从复制一致性、事物一致性）
节点hash一致性、重新分片、故障转移，从数据结构的角度来理解redis设计
思路和实现。
## 主从复制
### 同步
slave服务器收到slave of ip port之后，把ip port设置成自己的客户端，
经过权限验证、等一系列操作之后，发送psync命令给master，第一次进行同步
master使用bgsave生成一个rdb文件，让slave进行同步。同步之后把期间的
缓冲区的数据传递给slave，完成这段时间的数据同步。在处理命令的时候都会
维护一个命令的
主从复制主要slave每秒向master发送ping命令，同时把自己的slave_off_set
作为参数进行传递，通过和master_offset_
完全同步
slave 发送psync -1 给master, slave master会互相成为彼此的client，然后
master使用basave生成一个当前时间的rdb文件，同时使用一个缓冲区保存所有的写命令
等到rdb加载完成之后，把缓冲区的命令，传输到slave，完成同步。
部分同步
如果每次都进行完全同步，会造成网络拥塞，占用大量的cpu、内存、网络等资源，所以
使用部分同步机制来替换完全同步。
部分同步机制的原理是，使用一个循环队列作为一个写命令的缓冲区(circleQueue)。master和slave分别
保存自己的offset，slave每秒会向master发送一个 replicate_offset ack，里面放着
自己的offset,master收到offset之后，和自己的offset进行对比，如果master_offset-slave_offset > circleQueue.size
则进行完成同步，如果小于circleQueue.size则把两个slave_offset作为开始，master_offset作为
结束进行命令的传输，完成主从复制同步。
当然这里使用的复制算法是基于raft共识性算法，raft共识性算法通过纪元+offset的方式保证数据的一致性
通过对比纪元+offset-1数据，进行保证offset之前的数据都是正确的，从而保证数据的一致性。
数据一致性https://cloud.tencent.com/developer/article/1015442
而redis借鉴了raft算法通过复制模式实现了数据的一致性，属于顺序一致性，因为所有的写命令是按照顺序执行
FIFO，先进先出的，因为redis服务是单进程的，所以也符合FIFO一致性。因为offset是一直累加的，从服务器启动
是0，所以需要一个 
Class redisServer{
    long long master_repl_offset;   /* My current replication offset */
    long long second_replid_offset; /* Accept offsets up to this for replid2. */
    int slaveseldb;                 /* Last SELECTed DB in replication output */
    int repl_ping_slave_period;     /* Master pings the slave every N seconds */
    //这里存储的就是循环队列，作为一个写命令的缓冲区
    char *repl_backlog;             /* Replication backlog for partial syncs */
    //缓冲区的大小一般设置为一秒传输的数据量*3-5秒，
    long long repl_backlog_size;    /* Backlog circular buffer size */
    //由于是循环队列，所以histlen是实际存储的数据大小
    long long repl_backlog_histlen; /* Backlog actual data length */
    //缓冲区现在的下标，作为数据存储的开始，其实就是队尾，插入数据
    long long repl_backlog_idx;     /* Backlog circular buffer current offset,
                                       that is the next byte will'll write to.*/
     //由于使用的是循环队列，所以需要一个记录缓冲区开始的offset，这样才能和slave的offset对比
     //的时候判断，缓冲区是否满足slave的需求，如果不能则进行完全同步
    long long repl_backlog_off;     /* Replication "master offset" of first
                                       byte in the replication backlog buffer.*/
}                                       

/* Add data to the replication backlog.
 * This function also increments the global replication offset stored at
 * server.master_repl_offset, because there is no case where we want to feed
 * the backlog without incrementing the offset. */
 //添加一个写命令到缓冲区中
void feedReplicationBacklog(void *ptr, size_t len) {
    unsigned char *p = ptr;
    server.master_repl_offset += len; 
    /* 因为是一个循环队列，所以如果len的数据比较长，是需要循环进行复制，最多
       *只存储队列size的数据，只存储最新的数据，老的数据都出队,这里使用一个
       * while循环进行处理，*/
    /* This is a circular buffer, so write as much data we can at every
     * iteration and rewind the "idx" index if we reach the limit. */
    while(len) {
        // 队列空余空间大小
        size_t thislen = server.repl_backlog_size - server.repl_backlog_idx;
        //如果命令长度小于空闲空间，则直接复制到队列中，否则先把所有的空余空间复制数据
       // idx置位0，从ptr+thislen的位置继续复制数据，知道len为0，表示数据完成复制
        if (thislen > len) thislen = len;
        memcpy(server.repl_backlog+server.repl_backlog_idx,p,thislen);
        server.repl_backlog_idx += thislen;
        //队列满了，之后idx设置为0，从头开始循环
        if (server.repl_backlog_idx == server.repl_backlog_size)
            server.repl_backlog_idx = 0;
        len -= thislen;
        p += thislen;
        server.repl_backlog_histlen += thislen;
    }
    //如果histlen大于队列长度，说明队列满了，
    if (server.repl_backlog_histlen > server.repl_backlog_size)
        server.repl_backlog_histlen = server.repl_backlog_size;
    /* Set the offset of the first byte we have in the backlog. */
    //加1是因为，offset从0开始，histlen从1开始。
    server.repl_backlog_off = server.master_repl_offset -
                              server.repl_backlog_histlen + 1;
}
消息丢失同步
### 命令传播
当主从的offset一致之后，master每进行一次写操作，都会把该操作传播到所有的salve中
slave每秒会发送一次 replay_offset给master，让master对命令传播结果进行验证，如果
一致则没问题，如果出现数据丢失则进行同步。由于redis命令需要保证执行的顺序性，所以传播
中途出现命令丢失，服务端会接收到以error，此时salve变得可写之后，数据会被写入缓冲区
但是检测到异常之后，不会再把数据写入，如果出现异常，则后序的命令不会再传播到该client
知道master检测到ack reply offset异常之后，再把从异常开始之后的所有命令进行传播，完成
副本数据的一致性。
但是
## 事物
### 事物的实现分析
事物是通过一个队列底层是一个链表实现的
步骤
客户端发送multi到服务器server，到server端，server的client列表中
由于client是一个需要遍历的，同时经常发生的操作是遍历和两端插入，所以
使用一个双端链表，其实是和java的LinkedList是一样的，同时由于redis是
单线程模型，所以是线程安全的。
把该客户端的标记改为，事物标记，然后就调用addQueueCmd方法，把客户端传递
的命令存储到一个数组中，就是java的ArrayList，如果内存不够就自动进行扩容。
同时由于每个命令、参数、都是一个objc对象，由于redis的coding知道，都是属于
String类型的，而且使用ref引用计数进行内存的自动清理，而java是使用可达性算法
进行垃圾处理，所以这里只要把相关的参数，赋值就可，直接使用对象引用即可。
直到收到exec命令，结束事物，然后开始执行事物。
由于是单线程所以事物的执行是原子性的，因为会把整个事物执行完毕一次性，不中断
持久性的是根据aof/rbd进行的,当然也可以每次执行完事务之后进行save命令，保证持久性。
隔离性因为单线程模型的话所以是满足的
一致性也是满足的，在事物执行之前和之后都会数据状态是一样的
但是redis事物不支持回滚。
### 用java代码实现redis事物
```
c代码转为java代码是
Class ListNode{
    listNode next;
    listNode prev;
    Object value;
}
Class List{
   ListNode tail; 
   ListNode head; 
   /**
   * 使用抽象的方式，对三个函数进行扩展
    也可以使用接口的方式，使用代理/对象适配器/组合模式进行进行多态的扩展
   */
   abstract void dup(Object ptr){};
   abstract void free(Object ptr){};
   abstract void match(Object ptr){};
   Long len;
}
Class redisServer{
   /* 所有连接服务器的客户端,包括服务器使用的伪客户端（加载rdb )*/
   List clients ;
}
Class client{
    MultiState mstate;   
    int argc;
    String[] argv;
    String cmd;
}
Class MultiCommand{
    RedisCmd cmd;
    String[] args;
    int argc;
}
Class multiState{
    ArrayList commands; 
    int count;//命令总数
}
/* Add a new command into the MULTI commands queue */
void queueMultiCommand(Client c) {
    MultiCmd mc = new MultiCmd;
    mc.cmd = c.cmd;
    mc.argc = c.argc;
    mc.argv = c.argc;
    memcpy(mc->argv,c->argv,sizeof(robj*)*c->argc);
    for (j = 0; j < c->argc; j++)
        incrRefCount(mc->argv[j]);
    c.mstate.count++;
    c.mstate.cmd_flags |= c->cmd->flags;
}

```
### redis 事物源码，版本：5.8
```
typedef struct listNode {
    struct listNode *prev;
    struct listNode *next;
    void *value;
} listNode;
typedef struct list {
    listNode *head;
    listNode *tail;
    void *(*dup)(void *ptr);
    void (*free)(void *ptr);
    int (*match)(void *ptr, void *key);
    unsigned long len;
} list;
typedef struct redisServer{
    list *clients;              /* List of active clients */
}
typedef struct client{
    multiState mstate;      /* MULTI/EXEC state */
}
typedef struct multiState {
    multiCmd *commands;     /* Array of MULTI commands */
    int count;              /* Total number of MULTI commands */
    int cmd_flags;          /* The accumulated command flags OR-ed together.
                               So if at least a command has a given flag, it
                               will be set in this field. */
    int minreplicas;        /* MINREPLICAS for synchronous replication */
    time_t minreplicas_timeout; /* MINREPLICAS timeout as unixtime. */
};
/* Client MULTI/EXEC state */
typedef struct multiCmd {
    robj **argv;
    int argc;
    struct redisCommand *cmd;
} multiCmd;
/* Add a new command into the MULTI commands queue */
void queueMultiCommand(client *c) {
    multiCmd *mc;
    int j;
    c->mstate.commands = zrealloc(c->mstate.commands,
            sizeof(multiCmd)*(c->mstate.count+1));
    mc = c->mstate.commands+c->mstate.count;
    mc->cmd = c->cmd;
    mc->argc = c->argc;
    mc->argv = zmalloc(sizeof(robj*)*c->argc);
    memcpy(mc->argv,c->argv,sizeof(robj*)*c->argc);
    for (j = 0; j < c->argc; j++)
        incrRefCount(mc->argv[j]);
    c->mstate.count++;
    c->mstate.cmd_flags |= c->cmd->flags;
}
```
### redis事物的同步
redis事物通过multi打开，通过exec执行事物，所以multi命令首先把client的标记设置为 事物标记
exec命令执行的时候，开始循环执行整个事物的队列，同时对其中修改数据库的命令进行传播.
multi命令解析
```
void multiCommand(client *c) {
     //如果已经开启事物则报错，事物不能嵌套
    if (c->flags & CLIENT_MULTI) {
        addReplyError(c,"MULTI calls can not be nested");
        return;
    }
    //标记设置为事物标记 
    c->flags |= CLIENT_MULTI;
    //回复客户端消息
    addReply(c,shared.ok);
}
```
exec命令解析
```
void execCommand(client *c) {
    //标记是否进行传播，如果其中没有修改数据库的命令，则不进行传播
    int must_propagate = 0; /* Need to propagate MULTI/EXEC to AOF / slaves? */
    //master
    int was_master = server.masterhost == NULL;
    //如果没有开启，则报错
    if (!(c->flags & CLIENT_MULTI)) {
        addReplyError(c,"EXEC without MULTI");
        return;
    }
   // 如果一些watch键已经改变、队列中的命令有问题，则结束，不进行事物的运行，保证了原子性。
    if (c->flags & (CLIENT_DIRTY_CAS|CLIENT_DIRTY_EXEC)) {
        addReply(c, c->flags & CLIENT_DIRTY_EXEC ? shared.execaborterr :
                                                   shared.nullarray[c->resp]);
        discardTransaction(c);
        goto handle_monitor;
    }
    /* 一次性执行所有的命令，保证原子性  */
    unwatchAllKeys(c); /* Unwatch ASAP otherwise we'll waste CPU cycles */
    //返回事物包含命令的总数，给客户端
    addReplyArrayLen(c,c->mstate.count);
    // for遍历，事物数组，进行命令的执行、传播
    for (j = 0; j < c->mstate.count; j++) {
        c->argc = c->mstate.commands[j].argc;
        c->argv = c->mstate.commands[j].argv;
        c->cmd = c->mstate.commands[j].cmd;
          
        //如果事物中包含有写命令，则进行multi命令的传播，开启slave的事物,此命令只执行一次
        if (!must_propagate && !(c->cmd->flags & (CMD_READONLY|CMD_ADMIN))) {
            execCommandPropagateMulti(c); // 把multi命令传播到客户端以及aof，如果开启aof
            must_propagate = 1;
        }
        int acl_retval = ACLCheckCommandPerm(c); //检查命令
        if (acl_retval != ACL_OK) {
            //输出错误日志，然后继续执行下一个命令，此时不会结束事物的运行
        } else {
             // 如果服务正在加载rdb、aof文件处于恢复状态，则所有命令失效，不执行
             // call是所有命令执行的如果，在call方法里面会判断如果如果执行的命令
             //是写命令则会进行命令的传播保证副本的一致性，同时进行aof持久化，如果开启了aof的
            call(c,server.loading ? CMD_CALL_NONE : CMD_CALL_FULL);
        }
    }
    discardTransaction(c); //事物结束,消除标志
  }
```
## redis集群
```
/* 用来记录收到的节点挂掉的记录
typedef struct clusterNodeFailReport {
    struct clusterNode *node;  /* Node reporting the failure condition. */
    mstime_t time;             /* Time of the last report from this node. */
} clusterNodeFailReport;

/* 集群节点数据结构，主要用来记录集群节点的详细信息，以下只处理重要的信息*/
typedef struct clusterNode {
    uint64_t configEpoch; /* 当前的纪元，用来作为故障转移使用 */
    /* 是一个位数组，用来标记自身负责的槽点，同时用于向其他master广播，分享自己的槽点信息 */
    unsigned char slots[CLUSTER_SLOTS/8];
    int numslots;   /* 负责的槽点总数 */
} clusterNode;
/* 集群状态， 作为redisServer的一个字段 , CLUSTER_SLOTS = 16384, 是一个默认值 */
typedef struct clusterState { 
    /* key 是node的name值是， ClusterNode数据结构，这样取出一个node的时间是 O(1) */
    dict *nodes;          /* Hash table of name -> clusterNode structures */
    // 把自己的槽点，转移到其他ClusterNode,如果migrating_slots_to不为空，则表示正在向 
    //节点转移 ,导入和移入两个数组主要用于分片过程中，节点可以继续提供服务，实现不停机分片
    clusterNode *migrating_slots_to[CLUSTER_SLOTS];
     //导入槽点，从其他的ClusterNode中导入槽点，数组中都是需要导入槽点的节点
    clusterNode *importing_slots_from[CLUSTER_SLOTS];
    // 当查找一个槽点的时候，通过 该数组找到节点，如果节点就是本节点，则直接进行操作
    // ，如果不是则进行moved，转向目标节点进行操作,直接使用槽点就是下标，所以操作时间复杂度O(1)
    clusterNode *slots[CLUSTER_SLOTS];
    // 槽点存储的数据的个数，如果要进行计算某个槽点的key的个数，使用该数组时间复杂度是O(1) 不需要遍历
    //所有的数据，进行计算，这个是为了 slot_key_count 命令服务的
    uint64_t slots_keys_count[CLUSTER_SLOTS];
    //slots_to_keys用于记录，每个槽点的里面存储的数据元素，这里只记录元素的key值，如果需要value则在通过get方法获取
    // 之前的版本使用skipList结构存储，优化之后使用rax压缩前缀法，节省了更多的空间，和skiplist的时间复杂度一样其
   // 但是空间复杂度降低，因为rax只存储key值，以及下一个节点，不存储其他信息。但是rax给key值增加一个前缀，前缀就是
   //槽点的 比如，set name lzg， name在槽点 1086上面， rax上面存储的就是 1086name, 让槽点作为前缀，好处就是如果要
   //找某个槽点的所有元素，只要查找到前缀之后，遍历前缀的所有子元素即可,关于rax更多信息可以百度，或者看redis源码里面也有解释
    rax *slots_to_keys;
} clusterState;
```
### 节点hash一致性
节点hash一致性是分布式的都要处理的一个问题，当一个节点下线的时候，需要t把该节点的服务，让别的节点
进行负载，当新增一个节点的时候，需要把其他节点的服务，分配一些到新的节点，进行均衡。redis使用hash
槽的方式设置了16384个槽点，来解决这个问题，所有的key都会被分配到一个槽点，通过crc16hash算法计算出来
然后让所有的节点负责16384个槽点就可以，如果一个节点下线，则让其他的节点负责该节点之前负责的槽点即可
新增节点，则把其他的节点的槽点分配一些给新的节点，因为16384个槽点是固定大小的，不会随着节点的改变而
改变，就解决了新增节点、删除节点造成的hash不一致性。redis使用16384个槽点是因为，redis本身特点导致
如果超过16384个节点的话，集群每秒钟进行的心跳检测、集群状态传播，都会导致网络堵塞，拥堵，继而造成redis
性能下降。 
###  重新分片
副本机制解决了分布式读的性能，分片机制解决了redis写的性能瓶颈。分片的过程也是一个槽点分配的过程，这个是
使用脚本来完成的，
所以redis使用了两个数据结构
一个 unsigned char slots[16384/8] ,是一个位数组，用来标记自身负责的槽点，同时用于向其他master广播，分享自己的槽点信息
还有一个 slotNode slot[],是一个节点数组，每个节点存储了自身的ip port，槽点位数组，当查找一个槽点的时候，通过
该数组找到节点，如果节点就是笨节点，则直接进行操作，如果不是则进行moved，转向目标节点进行操作。分片的过程使用
两个数组  import migration来完成，保证了分片可以不停机进行，分片的过程中也可以继续提供服务。
## 故障转移
redisCluster的故障转移，使用 n/2+1的方式，进行判断一个节点是否下线，主观下线是连接超时就可以确定，但是还需要
一个客观下线判断，客观下线的判断必须有超过一半的节点判断主观下线，才可以确定为主观下线，主观下线判断完成，就可以
进行故障转移过程。
确定下线之后，会给所有的服务器包括从服务器，从服务器收到信息，发现是自己的主服务器挂了，就开始进行选主的过程
使用epoch纪元作为时间，因为只有每个纪元中只有得到n/2+1的主服务器的投票，从服务器才可以就可以结束选举，成为
主服务器。由于各种原因可能本级元没有选出，则纪元自动加1，进行第n轮大的选举，直到选出一个主服务器，完成选主。
接着执行slave of one 成为一个主服务器，接着让从服务器发送sync给主服务器，成为其从服务器，由于run_id改变，
所以所有从服务器进行全部更新，从而和主服务器的数据保持一致。
## 订阅/发布
集群模式下的订阅和发布，是server收到信息之后，把信息广播给所有的客户端，收到的客户端，查看自己的订阅dict，如果匹配
则发送给订阅者。后期可能会使用布隆选择器或者其他算法优化。
## redis5.8 和 3.0对比
### 字符串
使用 sds_5, sds_8,sds_16,sds_32,sds_64， 5种类型，而不是之前的sds一种，内部不再使用free结构,具体如下
```
struct __attribute__ ((__packed__)) sdshdr64 {
    uint64_t len; /* used */
    uint64_t alloc; /* excluding the header and null terminator */
    unsigned char flags; /* 3 lsb of type, 5 unused bits */
    char buf[];
};
```
5、8、16、32、64是字符串的长度，如果超出了长度则进行转换，类似于整数集合类型的升级，同样是不可逆的，由于
redis使用直接使用内存，而且存储的数据大量使用了字符串大部分长度都是少于2^64，而之前的字符串的 len alloc 
都是64bit、8byte的，现在改用不同的类型是为了减少空间的占用，这个是个人理解。
### list
3.0版本链表是使用ziplist，和双链表实现的，5.8版本链表使用了quicklist实现的，qucklist也是一个双链表
但是其元素是ziplist，不再是单个的元素，结合ziplist和链表，其时间和空间有所提升。
### slot_key_count, rax代替skiplist
记录当前key所在的slot(槽)，使用压缩前缀代替了之前的跳跃表结构，同时在存储key的sds时候，需要在前两个字节
存储其key所在的slot，使用两个字节是因为，slot范围是（0--16383),总共2^14-1=16384个槽点，所有两个字节足够。
由于使用了压缩前缀树，所以相同的前缀也只会存储一次，所以内存的使用其实是降低了，同时相同的前缀存储在同一个子树
中，其查找和遍历都提升了速度，其实际复杂度是O(log n),不再是O(n),每层只要查找一个节点即可。
##  总结
历经了一个月终于把redis，跟着<redis设计与实现-黄建宏>这本书自己对着5.8版本的代码过了一遍，把整个架构和主要的数据结构
自己看了一遍，对于redis存储数据使用的数据结构和操作有了了解，redis的数据结构设计展示了时间和空间的平衡之道，同时redis
的数据结构也在不断的改变，为了减少空间的占用和提供效率。