package com.study.zk.distributeConfig.service;

import com.study.zk.distributeConfig.config.PropertiesUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：lixiang
 * @version ：v1.0
 * @program ：zk-study-config
 * @date ：2020/4/2 13:53
 * @description ：
 */

@Service
public class PayService {

    @Resource
    private PropertiesUtil propertiesUtil;
    /**
     * 根据不同类型调用不同支付接口
     * @param type Alipay | weixin
     * @return
     */
    public String pay(String type) {

        if ("alipay".equals(type)) {
            System.out.println("调用支付宝接口，当前参数配置如下：");
            return propertiesUtil.getProperties("pay.alipay.url");
        }

        if ("weixin".equals(type)) {
            System.out.println("调用微信接口，当前参数配置如下：");
        }

        return "OK";
    }
}
