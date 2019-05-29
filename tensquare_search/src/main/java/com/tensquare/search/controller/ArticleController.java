package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章搜索控制器类
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }


    /**
     * 根据关键字查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKeywords(@PathVariable String keywords,@PathVariable  int page, @PathVariable  int size){
        PageResult pageResult = articleService.findByKeywords(keywords, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

}
