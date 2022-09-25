package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.service.TagService;
import com.wcy.blog.dao.TagDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_tag】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag>
    implements TagService{

}




