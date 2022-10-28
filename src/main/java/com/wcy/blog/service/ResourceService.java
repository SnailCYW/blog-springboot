package com.wcy.blog.service;

import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.ResourceDTO;
import com.wcy.blog.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.ResourceVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_resource】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface ResourceService extends IService<Resource> {

    List<ResourceDTO> listResourcesBack(ConditionVO condition);

    void addOrUpdateResource(ResourceVO resourceVO);

    void deleteResource(Integer resourceId);

    List<LabelOptionDTO> listRoleResources();
}
