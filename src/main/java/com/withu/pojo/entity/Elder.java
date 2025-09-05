package com.withu.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 老人信息表
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("elder")
@ApiModel(value="Elder对象", description="老人信息表")
public class Elder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联consumer_user.id")
    private Long consumerId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "与用户关系")
    private String relation;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "血型：A/B/AB/O")
    private String bloodType;

    @ApiModelProperty(value = "过敏史")
    private String allergyHistory;

    @ApiModelProperty("头像")
    private String avatar;


}
