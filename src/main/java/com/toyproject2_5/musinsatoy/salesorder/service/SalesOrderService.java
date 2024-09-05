package com.toyproject2_5.musinsatoy.salesorder.service;

import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrder;
import com.toyproject2_5.musinsatoy.ETC.util.pagination.*;
import org.springframework.stereotype.Service;

@Service
public interface SalesOrderService {
    void addOrder(SalesOrder order) throws Exception;
    SalesOrder readOrderAndDetails(String orderId) throws Exception;
    void updateOrderState(SalesOrder order) throws Exception;
    CursorResponse<SalesOrder> getOrders(int memberId, CursorRequest<String> cursorRequestByStringKey) throws Exception;

}