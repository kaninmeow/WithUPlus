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
 * 老人常用药品表
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("commonly_used_drug")
@ApiModel(value="CommonlyUsedDrug对象", description="老人常用药品表")
public class CommonlyUsedDrug implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联elder.id")
    private Long elderId;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "药品识别码")
    private String drugIdentificationCode;


}
