package com.study.zk.distributeConfig.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * @author ：lixiang
 * @version ：v1.0
 * @program ：zk-study-config
 * @date ：2020/4/2 13:15
 * @description ：
 */
@Component
public class PropertiesUtil {

    private Properties remoteProperties = new Properties();

    public String getProperties(String key) {
        return remoteProperties.getProperty(key);

    }

    @PostConstruct
    public void init() {
        String path = "/pay-server-config";
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryOneTime(1000));
        zkClient.start();
        //1.启动时读取远程配置中心的配置信息
        try {
            List<String> configNames = zkClient.getChildren().forPath(path);
            for (String configName : configNames) {
                byte[] value = zkClient.getData().forPath(path + "/" + configName);
                //2.保存到JVM本地【对象、容器】
                remoteProperties.setProperty(configName, new String(value));
            }
            //TODO 3. 远程配置中心数据变更及时收到更新
            TreeCache treeCache = new TreeCache(zkClient, path);
            treeCache.start();
            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                    System.out.println("配置变化了");
                    switch (treeCacheEvent.getType()) {
                        case NODE_UPDATED:
                            //通知的内容：包含路径和值
                            String path1 = treeCacheEvent.getData().getPath().replace(path+"/","");
                            byte[] data = treeCacheEvent.getData().getData();

                            //更新数据
                            remoteProperties.setProperty(path1, new String(data));
                            break;
                        default:

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
