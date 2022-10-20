package com.wcy.blog.service;

import com.wcy.blog.dto.UniqueViewDTO;
import com.wcy.blog.entity.UniqueView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_unique_view】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();
}
