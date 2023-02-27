package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.ArticleTagDao;
import com.wcy.blog.dao.TagDao;
import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.ArticleTag;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.TagService;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author Snail
* @description 针对表【tb_tag】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService{


    @Resource
    private TagDao tagDao;

    @Resource
    private ArticleTagDao articleTagDao;

    @Override
    public PageResult<TagDTO> listTags() {
        List<Tag> tagList = tagDao.selectList(null);
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList, TagDTO.class);
        Integer count = tagDao.selectCount(null);
        return new PageResult(count, tagDTOList);
    }

    @Override
    public PageResult<TagBackDTO> listTagsBack(ConditionVO condition) {
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count <= 0) {
            return new PageResult<TagBackDTO>();
        }
        log.info(PageUtils.getCurrent()+"  "+PageUtils.getSize());
        List<TagBackDTO> tagBackDTOList = tagDao.selectBackList(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<TagBackDTO>(count, tagBackDTOList);
    }

    @Override
    public List<TagDTO> searchTag(ConditionVO condition) {
        List<TagDTO> tagDTOList = tagDao.searchList(condition);
        return tagDTOList;
    }

    @Override
    public void addOrModifyTags(TagVO tagVO) {
        // 查询标签名是否存在
        Tag existTag = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVO.getTagName()));
        System.out.println(existTag);
        Tag existTag1 = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                .eq(Tag::getTagName, tagVO.getTagName()));
        System.out.println(existTag1);
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new BizException("标签名已存在");
        }
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        this.saveOrUpdate(tag);
    }

    @Override
    public void deleteTags(List<Integer> tagIdList) {
        // 查询标签下是否有文章
        Integer count = articleTagDao.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIdList));
        if (count > 0) {
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagDao.deleteBatchIds(tagIdList);
    }

}




