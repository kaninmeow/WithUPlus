package com.withu.service;

public interface AdminUserService {
    void approveOrder(Long id);

    void rejectOrder(Long id, String reason);
}
