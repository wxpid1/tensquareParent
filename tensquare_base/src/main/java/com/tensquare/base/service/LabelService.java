package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签服务类
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 全部标签列表
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }


    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    public List<Label> findSearch(Map searchMap){
        Specification specification=createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    public Page<Label> findSearch(Map searchMap, int page, int size){
        Specification specification=createSpecification(searchMap);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return labelDao.findAll(specification,pageRequest);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> predicates=new ArrayList<>();//查询条件的集合
                if(searchMap!=null){
                        if(searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname"))){
                            predicates.add( cb.like( root.get("labelname").as(String.class), "%"+(String)searchMap.get("labelname")+"%"   )   );
                        }
                        if(searchMap.get("state")!=null && !"".equals(searchMap.get("state"))){
                            predicates.add( cb.equal( root.get("state").as(String.class), (String)searchMap.get("state")  ) );
                        }
                        if(searchMap.get("recommend")!=null && !"".equals(searchMap.get("recommend"))){
                            predicates.add( cb.equal( root.get("recommend").as(String.class), (String)searchMap.get("recommend")  ) );
                        }
                }
                return cb.and( predicates.toArray(  new Predicate[ predicates.size()]  )  );
            }
        };
    }

    /**
     * 增加
     * @param label
     */
    public void add(Label label){
        label.setId(idWorker.nextId()+"" );
        labelDao.save(label) ;
    }

    /**
     * 修改
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

}
