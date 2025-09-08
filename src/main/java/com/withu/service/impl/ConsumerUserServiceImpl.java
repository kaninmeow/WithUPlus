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

import java.util.List;
import com.withu.mapper.OrderMapper;

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

    @Autowired
    private OrderMapper orderMapper;

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

    @Override
    public List<Order> getPendingOrdersByConsumer(Long consumerUserId) {
        if (consumerUserId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        // 待接单状态：0
        return orderMapper.getPendingOrdersByConsumer(consumerUserId);
    }

    @Override
    public List<Order> getReviewingOrdersByConsumer(Long consumerUserId) {
        if (consumerUserId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        // 审核中状态：4（见 sql.sql）
        return orderMapper.getReviewingOrdersByConsumer(consumerUserId);
    }

    @Override
    public List<Order> getInProgressOrdersByConsumer(Long consumerUserId) {
        if (consumerUserId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        // 进行中状态：1
        return orderMapper.getInProgressOrdersByConsumer(consumerUserId);
    }

    @Override
    public List<Order> getCompletedOrdersByConsumer(Long consumerUserId) {
        // 已完成状态：3（见 sql.sql）
        return orderMapper.getCompletedOrdersByConsumer(consumerUserId);
    }

    @Override
    public Elder getElderById(Long elderId) {
        if (elderId == null) {
            throw new BusinessException("老人ID不能为空");
        }
        Elder elder = consumerUserMapper.findElderById(elderId);
        if (elder == null) {
            throw new BusinessException("老人信息不存在");
        }
        return elder;
    }

    @Override
    public boolean deleteElderById(Long elderId) {
        if (elderId == null) {
            throw new BusinessException("老人ID不能为空");
        }
        // 先检查老人是否存在
        Elder elder = consumerUserMapper.findElderById(elderId);
        if (elder == null) {
            throw new BusinessException("老人信息不存在");
        }
        return consumerUserMapper.deleteElderById(elderId);
    }

    @Override
    public boolean updateElderById(Elder elder) {
        if (elder == null) {
            throw new BusinessException("老人信息不能为空");
        }
        if (elder.getId() == null) {
            throw new BusinessException("老人ID不能为空");
        }
        // 先检查老人是否存在
        Elder existingElder = consumerUserMapper.findElderById(elder.getId());
        if (existingElder == null) {
            throw new BusinessException("老人信息不存在");
        }
        return consumerUserMapper.updateElderById(elder);
    }

    @Override
    public String getVolunteerPhoneByOrderId(Long orderId) {
        String phone = consumerUserMapper.getVolunteerPhoneByOrderId(orderId);
        if (phone == null || phone.trim().isEmpty()) {
            throw new BusinessException("该订单暂无志愿者接单或志愿者信息不存在");
        }
        return phone;
    }
}