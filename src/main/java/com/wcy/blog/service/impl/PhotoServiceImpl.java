package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Photo;
import com.wcy.blog.service.PhotoService;
import com.wcy.blog.dao.PhotoDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_photo(照片)】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, Photo>
    implements PhotoService{

}




