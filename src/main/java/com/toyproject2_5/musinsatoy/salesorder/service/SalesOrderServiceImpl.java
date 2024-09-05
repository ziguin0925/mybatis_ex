package com.toyproject2_5.musinsatoy.salesorder.service;

import com.toyproject2_5.musinsatoy.Item.product.service.ProductService;
import com.toyproject2_5.musinsatoy.salesorder.dao.SalesOrderDao;
import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrder;
import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrderDetail;
import com.toyproject2_5.musinsatoy.ETC.util.pagination.CursorRequest;
import com.toyproject2_5.musinsatoy.ETC.util.pagination.CursorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Component
public class SalesOrderServiceImpl implements SalesOrderService {
    @Autowired
    SalesOrderDao dao;
//    @Autowired
//    ProductService productService;

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
    public SalesOrder readOrderAndDetails(String orderId) throws Exception {
        SalesOrder order = dao.selectOrderAndDetail(orderId);
        List<SalesOrderDetail> details = order.getSalesOrderDetailList();
//        List<ProductDetailDto> products = new ArrayList<>();
//        for(SalesOrderDetail detail : details){
//            ProductDetailDto dto = productService.findProductDetailById(detail.getProductId());
//            products.add(dto);
//        }

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
        System.out.println("서비스 시작");
        List<SalesOrder> orders;
        try {
            if (cursorRequestByStringKey.hasKey()) {     // key가 있음
                orders = dao.selectAllByLessThanIdAndMemberIdAndOrderByDesc(cursorRequestByStringKey.key(), memberId, cursorRequestByStringKey.size());
                System.out.println("orders hasKey = " + orders.toString());
            } else {    // key가 없음 - 가장 최신부터 조회
                orders = dao.selectAllByMemberIdAndOrderByDesc(memberId, cursorRequestByStringKey.size());
                System.out.println("orders noKey = " + orders.toString());
            }
            System.out.println("orders = " + orders.toString());
        } catch (Exception e) {
            System.out.println("서비스에서 트라이 캐치 예외 발생");
            System.out.println("e.getMessage() = " + e.getMessage());
            System.out.println("e.getCause() = " + e.getCause());
            throw new Exception("주문 목록 조회에 실패했습니다", e.initCause(e.getCause()));
        }
        // 다음 키값 찾기
        String nextKey = orders.stream()
                .map(SalesOrder::getOrderId).min(Comparator.naturalOrder()) // desc 정렬 -> 가장 작은 값이 cursor
                .orElse(null);    // 주어진 Key 없을 때
        System.out.println("서비스 마지막 orders = " + orders.toString());
        return new CursorResponse<>(cursorRequestByStringKey.next(nextKey), orders);
    }

}
