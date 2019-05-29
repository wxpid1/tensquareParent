package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 文章服务类
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 增加
     * @param article
     */
    public  void add(Article article){
        articleDao.save(article);
    }

    /**
     * 根据关键字查询文章
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public PageResult findByKeywords(String keywords,int page,int size){
        PageRequest pageRequest= PageRequest.of(page-1,size);
        Page<Article> pageList = articleDao.findByTitleOrContentLike(keywords, keywords, pageRequest);
        return new PageResult(pageList.getTotalElements(),pageList.getContent() );
    }

}
