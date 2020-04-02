package com.study.zk.distributeConfig.web;

import com.study.zk.distributeConfig.config.PropertiesUtil;
import com.study.zk.distributeConfig.service.PayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：lixiang
 * @version ：v1.0
 * @program ：zk-study-config
 * @date ：2020/4/2 13:52
 * @description ：
 */

@RestController
@RequestMapping("/api")
public class PayController {

    @Resource
    private PayService payService;



    AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/pay")
    public String pay(String type) {
        //处理请求前记录，判断是否超过阈值

//        propertiesUtil.getProperties();

        //调用具体的支付实现
        String testInfo = payService.pay(type);


        //处理完毕，登记----将处理量-1
        return testInfo;
    }
}
