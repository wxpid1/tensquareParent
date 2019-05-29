package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 标签熔断器类
 */
@Component
public class LabelClientImpl  implements LabelClient{


    @Override
    public Result findById(String id) {
        //记录日志。。。
        System.out.println("调用了熔断器类.....");
        return new Result(false, StatusCode.REMOTEERROR,"远程调用失败");
    }
}
