package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.ResourceDTO;
import com.wcy.blog.entity.Resource;
import com.wcy.blog.service.ResourceService;
import com.wcy.blog.dao.ResourceDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.CommonUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Snail
 * @description 针对表【tb_resource】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List<ResourceDTO> listResourcesBack(ConditionVO condition) {
        List<Resource> resources = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Resource::getResourceName, condition.getKeywords()));
        List<Resource> parentList = resources.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
        Map<Integer, List<Resource>> childrenMap = resources.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));

        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO parent = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            parent.setChildren(BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class));
            childrenMap.remove(item.getId());
            return parent;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childrenMap)) {
            for (List<Resource> value : childrenMap.values()) {
                resourceDTOList.addAll(BeanCopyUtils.copyList(value, ResourceDTO.class));
            }
        }
        return resourceDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateResource(ResourceVO resourceVO) {
        Resource resource = BeanCopyUtils.copyObject(resourceVO, Resource.class);
        this.saveOrUpdate(resource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Integer resourceId) {
        resourceDao.deleteById(resourceId);
    }

    @Override
    public List<LabelOptionDTO> listRoleResources() {
        List<Resource> resources = resourceDao.selectList(null);
        List<Resource> parentResource = resources.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
        Map<Integer, List<Resource>> childrenMap = resources.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
        List<LabelOptionDTO> labelOptionDTOList = parentResource.stream().map(item -> {
            List<Resource> children = childrenMap.get(item.getId());
            List<LabelOptionDTO> childrenList = children.stream().map(child -> LabelOptionDTO.builder()
                    .id(child.getId())
                    .label(child.getResourceName())
                    .build())
                    .collect(Collectors.toList());
            childrenMap.remove(item.getId());
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(childrenList).build();
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childrenMap)) {
            for (List<Resource> value : childrenMap.values()) {
                List<LabelOptionDTO> labelOptionDTOS = value.stream().map(item -> LabelOptionDTO.builder()
                        .id(item.getId())
                        .label(item.getResourceName())
                        .build()).collect(Collectors.toList());
                labelOptionDTOList.addAll(labelOptionDTOS);
            }
        }
        return labelOptionDTOList;
    }
}




