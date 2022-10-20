package com.wcy.blog.service;

import com.wcy.blog.entity.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.PageVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_page(页面)】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface PageService extends IService<Page> {

    List<PageVO> listPages();

}
