package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 标签控制器类
 */
@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 查询全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        System.out.println("No.2");

        Label label = labelService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",label);
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 条件+分页查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap,@PathVariable int page,@PathVariable int size){
        Page<Label> pageList = labelService.findSearch(searchMap,page,size);
        //封装分页结果
        PageResult<Label> pageResult=new PageResult<Label>(pageList.getTotalElements(),pageList.getContent());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /**
     * 增加
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     * @param label
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}" , method = RequestMethod.PUT)
    public Result update(@RequestBody Label label, @PathVariable  String id){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
