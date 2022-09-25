package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.UniqueView;
import com.wcy.blog.service.UniqueViewService;
import com.wcy.blog.dao.UniqueViewDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_unique_view】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewDao, UniqueView>
    implements UniqueViewService{

}




