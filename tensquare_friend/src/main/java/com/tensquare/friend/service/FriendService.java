package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 交友业务逻辑
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserClient userClient;


    @Transactional
    public int addFriend(String userid,String friendid){
        //判断好友表中是否存在该记录
        int count = friendDao.selectCount(userid, friendid);
        if(count>0){
            return 0;
        }
        //向好友表中添加记录
        Friend friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断如果对方已添加你为好友，则修改互粉状态
        if(friendDao.selectCount(friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }
        userClient.incFanscount(friendid,1);//增加粉丝数
        userClient.incFollowcount(userid,1);//增加关注数
        return 1;
    }

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加非好友
     * @param userid
     * @param friendid
     */
    public  void addNoFriend(String userid,String friendid){
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }


    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        friendDao.deleteFriend(userid,friendid);//删除
        friendDao.updateLike(friendid,userid,"0");//修改状态

        userClient.incFanscount(friendid,-1);//增加粉丝数
        userClient.incFollowcount(userid,-1);//增加关注数
    }

}
