package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Resource;
import com.wcy.blog.service.ResourceService;
import com.wcy.blog.dao.ResourceDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_resource】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource>
    implements ResourceService{

}




