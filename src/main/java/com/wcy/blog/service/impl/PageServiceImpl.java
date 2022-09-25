package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Page;
import com.wcy.blog.service.PageService;
import com.wcy.blog.dao.PageDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_page(页面)】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class PageServiceImpl extends ServiceImpl<PageDao, Page>
    implements PageService{

}




