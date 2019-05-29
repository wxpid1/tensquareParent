package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 37269 on 2018/7/11.
 */
@FeignClient("tensquare-base")
public interface LabelClient {


    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value="/label/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);


}
