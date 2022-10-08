package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.service.TagService;
import com.wcy.blog.dao.TagDao;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author Snail
* @description 针对表【tb_tag】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService{


    @Resource
    private TagDao tagDao;

    @Override
    public PageResult<TagDTO> TagList() {
        List<Tag> tagList = tagDao.selectList(null);
        List<TagDTO> tagDTOList = copyList(tagList);
        Integer count = tagDao.selectCount(null);
        return new PageResult(count, tagDTOList);
    }

    @Override
    public PageResult<TagBackDTO> TagListBack(ConditionVo conditionVo) {
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Tag::getTagName, conditionVo.getKeywords()));
        if (count <= 0) return new PageResult<TagBackDTO>();

        List<TagBackDTO> tagBackDTOList =
                tagDao.selectBackList(PageUtils.getCurrent(), PageUtils.getSize(), conditionVo);
        return new PageResult<TagBackDTO>(count, tagBackDTOList);
    }


    private List<TagDTO> copyList(List<Tag> tagList) {
        ArrayList<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagDTOList.add(copy(tag));
        }
        return tagDTOList;
    }

    private TagDTO copy(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

}




