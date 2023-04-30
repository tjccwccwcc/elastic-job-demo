package cn.wolfcode;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.util.Date;

public class MyElasticJob implements SimpleJob {
    public void execute(ShardingContext shardingContext) {
        System.out.println("定时任务开始====>"+new Date());
    }
}
