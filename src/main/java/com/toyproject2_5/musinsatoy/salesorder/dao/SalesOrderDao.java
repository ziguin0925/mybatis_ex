package com.toyproject2_5.musinsatoy.salesorder.dao;

import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrder;

import java.util.List;

public interface SalesOrderDao {
    void insert(SalesOrder order) ;
    List<SalesOrder> selectAllByLessThanIdAndMemberIdAndOrderByDesc(String orderId, int memberId, int size);
    List<SalesOrder> selectAllByMemberIdAndOrderByDesc(int memberId, int size);
    SalesOrder selectOrder(String orderId) ;
    SalesOrder selectOrderAndDetail(String orderId);
    void updateState(SalesOrder order);
    void deleteAll();
    int countOrder();
}
