package com.fastcampus.toyproject2.salesorder.service;

import com.fastcampus.toyproject2.salesorder.dao.SalesOrderDao;
import com.fastcampus.toyproject2.salesorder.dto.SalesOrder;
import com.fastcampus.toyproject2.salesorder.dto.SalesOrderDetail;
import com.fastcampus.toyproject2.ETC.util.pagination.CursorRequest;
import com.fastcampus.toyproject2.ETC.util.pagination.CursorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SalesOrderServiceImpl implements SalesOrderService {

    @Qualifier("salesOrderDaoMysql")
    @Autowired
    SalesOrderDao dao;

    @Override
    public void addOrder(SalesOrder order) throws Exception {
        try {
            // orderId 만들어서 전달하기
            LocalDateTime orderDate = order.getOrderDatetime();
            String orderId = orderDate.format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSSSSS"));
            // orderId가 pk이므로 중복 검증 - 괜찮을 것 같은데 둬도 되는지? / 시퀀스(날마다 초기화) / 랜덤값 붙이기 고려 / 나중에 다중서버환경 동시성 이슈를 디비 트랜젝션 격리 수준과 락으로 해결해보기
            while (dao.selectOrder(orderId) != null) {
                orderId += "1";
            }
            // orderId 셋팅
            order.setOrderId(orderId);
            for (SalesOrderDetail detail : order.getSalesOrderDetailList()) {
                detail.setOrderId(orderId);
            }
            dao.insert(order);
        } catch (Exception e){
            throw new Exception("주문 등록에 실패했습니다", e.getCause());
        }
    }

    @Override
    public SalesOrder readOrderAndDetails(String orderId) {
        return dao.selectOrderAndDetail(orderId);
    }

    @Override
    public void updateOrderState(SalesOrder order) throws Exception {    // orderId, stateCode만 필요
        if(order.getOrderId()==null){
            throw new Exception("주문 상태 수정에 필요한 값이 없습니다");
        }
        dao.updateState(order);
    }

    @Override
    public CursorResponse<SalesOrder> getOrders(int memberId, CursorRequest<String> cursorRequestByStringKey) throws Exception {
        // 목록 찾기
        List<SalesOrder> orders;
        try {
            if (cursorRequestByStringKey.hasKey()) {     // key가 있음
                orders = dao.selectAllByLessThanIdAndMemberIdAndOrderByDesc(cursorRequestByStringKey.key(), memberId, cursorRequestByStringKey.size());
            } else {    // key가 없음 - 가장 최신부터 조회
                orders = dao.selectAllByMemberIdAndOrderByDesc(memberId, cursorRequestByStringKey.size());
            }
        } catch (Exception e) {
            throw new Exception("주문 목록 조회에 실패했습니다", e.getCause());
        }
        // 다음 키값 찾기
        String nextKey = orders.stream()
                .map(SalesOrder::getOrderId).min(Comparator.naturalOrder()) // desc 정렬 -> 가장 작은 값이 cursor
                .orElse(null);    // 주어진 Key 없을 때
        return new CursorResponse<>(cursorRequestByStringKey.next(nextKey), orders);
    }

}
