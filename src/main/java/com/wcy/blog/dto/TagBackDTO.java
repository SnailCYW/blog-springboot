package com.wcy.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/08/18:01
 */
@Data
public class TagBackDTO {

    private Integer articleCount;

    private LocalDateTime createTime;

    private Integer id;

    private String tagName;

}
