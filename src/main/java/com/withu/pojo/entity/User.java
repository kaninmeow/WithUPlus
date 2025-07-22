package com.withu.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private Integer sex;

    private String idNumber;

    private Integer type;

    private String avatar;
}
