package com.withu.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "企业用户信息")
public class EnterpriseUserVo {
    private String token;
    private Integer id;
    private String username;
    private String name;
    private Long type;
}
