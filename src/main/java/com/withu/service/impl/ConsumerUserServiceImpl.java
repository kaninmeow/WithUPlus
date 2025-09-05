package com.withu.service.impl;

import com.withu.pojo.entity.Address;
import com.withu.pojo.entity.ConsumerUser;
import com.withu.mapper.ConsumerUserMapper;
import com.withu.pojo.entity.Elder;
import com.withu.pojo.entity.Order;
import com.withu.service.IConsumerUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withu.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 普通消费者用户表 服务实现类
 *
 * @author 
 * @since 2025-07-20
 */
@Service
public class ConsumerUserServiceImpl extends ServiceImpl<ConsumerUserMapper, ConsumerUser> implements IConsumerUserService {

    @Autowired
    private ConsumerUserMapper consumerUserMapper;
    @Override
    public boolean relevantToTheElderly(Elder elder) {
        if (elder == null) {
            throw new BusinessException("老人信息为空");
        }
        Boolean flag = consumerUserMapper.saveElder(elder);
        if (flag) {
            return true;
        }
        return false;
    }

    @Override
    public ConsumerUser getConsumerUserById(Long id) {
        ConsumerUser consumerUser = consumerUserMapper.selectConsumerUserById(id);
        if (consumerUser == null) {
            throw new BusinessException("用户不存在");
        }
        return consumerUser;
    }

    @Override
    public List<Elder> findAllEldersById(Long id) {
        List<Elder> elderList = consumerUserMapper.findAllEldersById(id);
        if (elderList == null) {
            throw new BusinessException("没有用户");
        }
        return elderList;
    }

    @Override
    public boolean saveAddress(Address address) {
        return consumerUserMapper.insertAddress(address);
    }

    @Override
    public List<Address> findAllAddressById(Long id) {
        return consumerUserMapper.findAllAddressById(id);
    }

    @Override
    public Address findAddressById(Long id) {
        Address address = consumerUserMapper.findAddressById(id);
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        return address;
    }

    @Override
    public boolean removeAddressById(Long id) {
        if (findAddressById(id) == null) {
            throw new BusinessException("地址不存在");
        }
        return consumerUserMapper.deleteAddressById(id);
    }

    @Override
    public boolean updateAddressById(Address address) {
        if (findAddressById(address.getId()) == null) {
            throw new BusinessException("地址不存在");
        }
        removeAddressById(address.getId());
        return saveAddress(address);
    }
}