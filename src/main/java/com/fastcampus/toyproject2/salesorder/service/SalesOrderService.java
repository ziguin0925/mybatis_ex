package com.fastcampus.toyproject2.salesorder.service;

import com.fastcampus.toyproject2.ETC.util.pagination.CursorRequest;
import com.fastcampus.toyproject2.ETC.util.pagination.CursorResponse;
import com.fastcampus.toyproject2.salesorder.dto.SalesOrder;
import org.springframework.stereotype.Service;

@Service
public interface SalesOrderService {
    void addOrder(SalesOrder order) throws Exception;
    SalesOrder readOrderAndDetails(String orderId) throws Exception;
    void updateOrderState(SalesOrder order) throws Exception;
    CursorResponse<SalesOrder> getOrders(int memberId, CursorRequest<String> cursorRequestByStringKey) throws Exception;

}