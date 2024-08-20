package com.fastcampus.toyproject2.salesorder.dao;

import com.fastcampus.toyproject2.salesorder.dto.SalesOrder;

import java.util.*;

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
