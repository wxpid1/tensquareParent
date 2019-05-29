package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 交友数据访问层
 */
public interface FriendDao extends JpaRepository<Friend,String> {

    //根据用户Id和好友ID查询记录个数
    @Query("select count(f) from Friend f where f.userid=?1 and f.friendid=?2")
    public int selectCount(String userid,String friendid);

    //根据用户ID和好友id更新互粉状态（islike）
    @Modifying
    @Query("update Friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2")
    public void updateLike(String userid,String friendid,String islike);

    //删除
    @Modifying
    @Query("delete from  Friend  f where f.userid=?1 and f.friendid=?2")
    public void deleteFriend(String userid,String friendid);

}
