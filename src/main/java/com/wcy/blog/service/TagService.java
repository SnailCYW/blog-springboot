package com.wcy.blog.service;

import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.TagVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_tag】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface TagService extends IService<Tag> {

    PageResult<TagDTO> listTags();

    PageResult<TagBackDTO> listTagsBack(ConditionVo conditionVo);

    List<TagDTO> searchTag(ConditionVo conditionVo);

    void addOrModifyTags(TagVO tagVO);

    void deleteTags(List<Integer> tagIdList);
}
