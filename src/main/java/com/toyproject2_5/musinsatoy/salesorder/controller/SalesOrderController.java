package com.toyproject2_5.musinsatoy.salesorder.controller;

import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrder;
import com.toyproject2_5.musinsatoy.salesorder.service.SalesOrderService;
import com.toyproject2_5.musinsatoy.ETC.util.pagination.CursorRequest;
import com.toyproject2_5.musinsatoy.ETC.util.pagination.CursorResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/orders")
public class SalesOrderController {
    @Autowired
    private SalesOrderService service;

    /**
     * 로그인 체크
     */
    private Integer checkLogin(HttpSession session){
        return (Integer)session.getAttribute("memberId");
    }

    /**
     * 주문 세트(주문+상세들)를 추가
     */
    @PostMapping
    public ResponseEntity<?> addOrder(@Valid @RequestBody SalesOrder order, HttpSession session) throws Exception{
        // 로그인 체크
//        Integer memberId = checkLogin(session);
//        if(memberId == null)
//            return new ResponseEntity<>("로그인이 안 된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);
        // validate
//        if(memberId != order.getMemberId())
//            throw new AddException("주문자 정보가 올바르지 않습니다");
        service.addOrder(order);
        return ResponseEntity.status(HttpStatus.OK)
                .body(order.getOrderId());
    }

    /**
     * 주문의 주문상세 보기
     */
    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable String orderId, HttpSession session) throws Exception{
        SalesOrder order = service.readOrderAndDetails(orderId);
        // 상품 정보 불러오기 추가하기
        return ResponseEntity.status(HttpStatus.OK)
                .body(order);
    }

    /**
     * 회원이 주문 목록 조회 -> 회원으로?
     * 회원 생기면 memberId를 파라미터가 아닌 세션에서 얻어오는 걸로 변경하기
     */
    @GetMapping("/members")
    public ResponseEntity<?> getOrdersByMemberId (
            @RequestParam int memberId, // 수정 필요
            @RequestParam(required = false) String key,
            @RequestParam(required = false) Integer size,
            HttpSession session) throws Exception {
        // 로그인 체크
//        if(checkLogin(session) == null)
//            return new ResponseEntity<>("로그인이 안 된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);

        if(Objects.equals(key, "")) key = null; // String key validate
        CursorRequest<String> cursorRequest = new CursorRequest<>(key, size);
        CursorResponse<SalesOrder> cursorResponse = service.getOrders(memberId, cursorRequest);
        // 상품 정보 불러오기 추가하기
        return ResponseEntity.status(HttpStatus.OK)
                .body(cursorResponse);
    }

    /**
     * 주문 상태 변경하기
     */
    @PatchMapping
    public ResponseEntity<?> updateOrderState(@Valid @RequestBody SalesOrder salesOrder) throws Exception{
        service.updateOrderState(salesOrder);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("orderId",salesOrder.getOrderId(), "stateCode", salesOrder.getStateCode()));
    }

}
