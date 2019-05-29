package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * 吐槽服务层
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /***
     * 查询全部数据
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 增加吐槽
     * @param spit
     */
    public void add(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布时间
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setComment(0);//评论数
        spit.setThumbup(0);//点赞数
        spit.setState("1");//状态

        //如果这是对某个吐槽的评论 ,让被评论的吐槽的评论数+1
        if(spit.getParentid()!=null && !spit.getParentid().equals("")){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }

        spitDao.save(spit);
    }

    /**
     * 修改吐槽
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     *  删除吐槽
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     *  根据上级ID查询吐槽
     * @param parentid 上级ID
     * @param page 页码
     * @param size 页大小
     * @return
     */
    public PageResult<Spit> findByParentid(String parentid,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        Page<Spit> pageList = spitDao.findByParentid(parentid, pageRequest);
        return new PageResult<>(pageList.getTotalElements(),pageList.getContent());
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *  点赞
     * @param id
     */
    public void updateThumbup(String id){
        //查询条件
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        //更新对象
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

}
