package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 广告表实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 广告标题
     */
    private String adTitle;

    /**
     * 广告描述
     */
    private String adDesc;

    /**
     * 广告素材（图片或视频的URL）
     */
    private String adMaterial;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
