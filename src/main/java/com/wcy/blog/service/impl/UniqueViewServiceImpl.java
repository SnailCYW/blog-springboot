package com.wcy.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.UniqueViewDTO;
import com.wcy.blog.entity.UniqueView;
import com.wcy.blog.service.UniqueViewService;
import com.wcy.blog.dao.UniqueViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Snail
* @description 针对表【tb_unique_view】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewDao, UniqueView>
    implements UniqueViewService{

    @Autowired
    private UniqueViewDao uniqueViewDao;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -1));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewDao.listUniqueViews(startTime, endTime);
    }
}




