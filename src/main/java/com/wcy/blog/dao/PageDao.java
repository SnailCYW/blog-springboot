package com.wcy.blog.dao;

import com.wcy.blog.entity.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.PageVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_page(页面)】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.Page
*/
public interface PageDao extends BaseMapper<Page> {

    List<PageVO> listPages();
}




