# P95 统计awk脚本
由于ab test等测试工具，统计的结果是从发去请求，到接收到请求结果
来计算的，但是由于本地机器测试，导致并发量100，请求量为10万的，OPS为
5000-1W的时候，由于接收请求处理的慢，导致统计结果及其不准确，所以
写了一个awk脚本，去统计nginx，tomcat的acce.log日志，获取结果，
是比较准确的。
这个脚本可以用来统计，所有连续分布的数据，比如排名、等
## P95 统计脚本
用法用法需要先对统计的结果进行排序、去重、：
```
# 使用命令
grep "/loan/loanh5/"  access.log | awk '{print $10}' | sort -n | uniq -c | awk -f /tmp/cal.awkC
# cal.awk 脚本内容
#! /usr/bin/awk -f
{variance=0;sumCount+=$1;sumCost+=($2*$1);count[NR]=$1;cost[NR]=$2}
END { staticTotal[0]=50;
    staticTotal[1]=66;
    staticTotal[2]=80;
    staticTotal[3]=85;
    staticTotal[4]=90;
    staticTotal[5]=95;
    staticTotal[6]=98;
    staticTotal[7]=99;
    staticTotal[8]=100;
    staticFlag[0]=1;
    staticFlag[1]=1;
    staticFlag[2]=1;
    staticFlag[3]=1;
    staticFlag[4]=1;
    staticFlag[5]=1;
    staticFlag[6]=1;
    staticFlag[7]=1;
    staticFlag[8]=1;
    printf "%3s  %10s  %15s %15s\n", "static", "costt", "count", "diffPre";
    averageCost = sumCost/sumCount;
    for(i=1; i <=length(count); i++) {
        diff = (cost[i] - averageCost);
        variance += (diff*diff*count[i]/(sumCount-1));
        #printf("diff %s, variance %s, count[%s]: %s, cost[%s]: %s \n", diff, variance, i, count[i], i, cost[i]);
        countTotal += count[i];
        for (j=0; j <length(staticTotal); j++) {
        if (countTotal >= sumCount*staticTotal[j]/100) if (staticFlag[j]==1) {
            staticFlag[j]=sprintf("P%-3s  %10s %15s %15s", staticTotal[j],cost[i],countTotal, countTotal - countTotalPre);             countTotalPre = countTotal;
        }
    }
    };

for( i=0;i<length(staticFlag);i++) print staticFlag[i];
printf "count total: %s\n", sumCount, countTotal;
printf "average cost: %s \n", averageCost;
printf "variance cost: %s \n", variance;
}

```

## 统计http状态码
一样用上面的脚本就可以
## 统计某个tcp请求的连接数量-每秒更新
```
# 使用命令 -n 1表示 每秒更新一次
watch -n 1 -d 'bash t.sh'
# t.sh 脚本内容
netstat -aln |grep 29999  |awk '/^tcp/{++S[$NF]}END{for(m in S) print m,S[m]}'
```
## 总结
awk sek 确实是神器，当没有合适的统计脚本的时候，可以自己写一个
因为有的时候确实么有现成的，不要嫌弃麻烦，写脚本也是一个学习、成长的过程。

