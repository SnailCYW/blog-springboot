package com.wcy.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/19:13
 */
@Data
public class ArchiveDTO {

    private String articleTitle;

    private LocalDateTime createTime;

    private Integer id;

}
