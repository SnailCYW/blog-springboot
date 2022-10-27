package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.FriendLinkBackDTO;
import com.wcy.blog.dto.FriendLinkDTO;
import com.wcy.blog.entity.FriendLink;
import com.wcy.blog.service.FriendLinkService;
import com.wcy.blog.dao.FriendLinkDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.FriendLinkVO;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author Snail
* @description 针对表【tb_friend_link】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkDao, FriendLink>
    implements FriendLinkService{

    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public PageResult<FriendLinkBackDTO> listLinksBack(ConditionVO condition) {
        final Page<FriendLink> page = new Page<>(PageUtils.getLimitCurrent(), PageUtils.getSize());
        final Page<FriendLink> friendLinkPage = friendLinkDao.selectPage(page, new LambdaQueryWrapper<FriendLink>()
                .like(Objects.nonNull(condition.getKeywords()), FriendLink::getLinkName, condition.getKeywords()));
        return new PageResult<>((int) friendLinkPage.getTotal(), BeanCopyUtils.copyList(page.getRecords(), FriendLinkBackDTO.class));
    }

    @Override
    public void deleteLinks(List<Integer> linkIdList) {
        friendLinkDao.deleteBatchIds(linkIdList);
    }

    @Override
    public void addOrUpdateLink(FriendLinkVO friendLinkVO) {
        FriendLink friendLink = BeanCopyUtils.copyObject(friendLinkVO, FriendLink.class);
        this.saveOrUpdate(friendLink);
    }

    @Override
    public List<FriendLinkDTO> listLinks() {
        List<FriendLink> friendLinkList = friendLinkDao.selectList(new LambdaQueryWrapper<FriendLink>());
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkDTO.class);
    }
}




