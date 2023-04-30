package cn;

import cn.wolfcode.MyElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobDemo {
    public static void main(String[] args) {
        // JobScheduler(注册中心对象, 任务中心对象)
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }
    //注册中心配置
    private static CoordinatorRegistryCenter createRegistryCenter() {
        ZookeeperConfiguration zookeeperConfiguration =
                new ZookeeperConfiguration("localhost:2181", "elastic-job-demo");
        // 设置节点超时时间
        zookeeperConfiguration.setSessionTimeoutMilliseconds(100);
        // ZookeeperConfiguration("zookeeper地址", "项目名")
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
    }
    //定时任务类配置
    private static LiteJobConfiguration createJobConfiguration() {
        // 定义作业核⼼配置 newBuilder("任务名称", "cron表达式", "分片数量")
        JobCoreConfiguration simpleCoreConfig =
                JobCoreConfiguration.newBuilder
                        ("myElasticJob","0/10 * * * * ?",1).build();
        // 定义SIMPLE类型配置 cn.wolfcode.myElasticJob
        System.out.println("MyElasticJob.class.getCanonicalName()---->" +
                MyElasticJob.class.getCanonicalName());
        SimpleJobConfiguration simpleJobConfig =
                new SimpleJobConfiguration
                        (simpleCoreConfig, MyElasticJob.class.getCanonicalName());
        // 定义Lite作业根配置 .overwrite(true)允许覆盖配置，"0/10 * * * * ?"才能生效
        return LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
    }
}
