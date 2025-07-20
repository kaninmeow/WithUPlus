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

    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private Long sex;

    private String idNumber;

    private Long type;

    private String avatar;
}
