package com.wcy.blog.service;

import com.wcy.blog.dto.FriendLinkBackDTO;
import com.wcy.blog.dto.FriendLinkDTO;
import com.wcy.blog.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.FriendLinkVO;
import com.wcy.blog.vo.PageResult;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_friend_link】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface FriendLinkService extends IService<FriendLink> {

    PageResult<FriendLinkBackDTO> listLinksBack(ConditionVO condition);

    void deleteLinks(List<Integer> linkIdList);

    void addOrUpdateLink(FriendLinkVO friendLinkVO);

    PageResult<FriendLinkDTO> listLinks();
}
