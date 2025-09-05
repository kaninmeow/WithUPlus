package com.withu.service;

import com.withu.pojo.entity.Address;
import com.withu.pojo.entity.ConsumerUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.pojo.entity.Elder;
import com.withu.pojo.entity.Order;

import java.util.List;

/**
 * <p>
 * 普通消费者用户表 服务类
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
public interface IConsumerUserService extends IService<ConsumerUser> {

    boolean relevantToTheElderly(Elder elder);

    ConsumerUser getConsumerUserById(Long id);

    List<Elder> findAllEldersById(Long id);

    boolean saveAddress(Address address);

    List<Address> findAllAddressById(Long id);

    Address findAddressById(Long id);

    boolean removeAddressById(Long id);

    boolean updateAddressById(Address address);
}
