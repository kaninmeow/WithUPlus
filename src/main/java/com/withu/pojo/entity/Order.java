package com.withu.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order")
@ApiModel(value="Order对象", description="订单表")
public class Order {
    private Long id;
    private Long elderId;
    private Long consumerUserId;
    private String serviceType;
    private String serviceDescription;
    private Date startTime;
    private Date endTime;
    private Boolean isVolunteerPayment;
    private BigDecimal volunteerPaymentAmount;
    private Integer volunteerPaymentStatus;
    private Date volunteerPaymentTime;
    private String paymentProof;
    private BigDecimal serviceFee;
    private BigDecimal advancedPaymentAmount;
    private Integer paymentType;
    private Integer status;
    private Date createTime;
}