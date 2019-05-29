package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新回答
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in ( select problemid from Pl where labelid=?1 ) order by p.replytime desc ")
    public Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);


    /**
     * 热门回答
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in ( select problemid from Pl where labelid=?1 ) order by p.reply desc ")
    public Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);


    /**
     * 等待回答
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in ( select problemid from Pl where labelid=?1 ) and p.reply=0  order by p.createtime desc ")
    public Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);

}
