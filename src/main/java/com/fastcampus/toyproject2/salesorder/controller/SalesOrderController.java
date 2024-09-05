package com.fastcampus.toyproject2.salesorder.controller;

import com.fastcampus.toyproject2.salesorder.dto.SalesOrder;
import com.fastcampus.toyproject2.salesorder.service.SalesOrderServiceImpl;
import com.fastcampus.toyproject2.ETC.util.pagination.CursorRequest;
import com.fastcampus.toyproject2.ETC.util.pagination.CursorResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class SalesOrderController {

    @Autowired
    private SalesOrderServiceImpl service;

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
    public ResponseEntity<?> addOrder(@RequestBody SalesOrder order, HttpSession session) throws Exception{
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
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderAndDetails(@RequestParam String orderId, HttpSession session) throws Exception{
        // 로그인 체크
//        if(checkLogin(session) == null)
//            return new ResponseEntity<>("로그인이 안 된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);
        SalesOrder order = service.readOrderAndDetails(orderId);
        // 상품 정보 불러오기 추가하기
        return ResponseEntity.status(HttpStatus.OK)
                .body(order);
    }

    /**
     * 회원이 주문 목록 조회 -> 회원으로?
     * 회원 생기면 memberId를 파라미터가 아닌 세션에서 얻어오는 걸로 변경하기
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getOrdersByMemberId (
            @PathVariable int memberId, @RequestParam CursorRequest<String> cursorRequestByStringKey, HttpSession session) throws Exception {
        // 로그인 체크
//        if(checkLogin(session) == null)
//            return new ResponseEntity<>("로그인이 안 된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);
        CursorResponse<SalesOrder> list = service.getOrders(memberId, cursorRequestByStringKey);
        // 상품 정보 불러오기 추가하기
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }

    /**
     * 주문 상태 변경하기
     */
    @PatchMapping
    public ResponseEntity<?> updateOrderState(@RequestBody SalesOrder salesOrder) throws Exception{
        service.updateOrderState(salesOrder);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("orderId",salesOrder.getOrderId(), "stateCode", salesOrder.getStateCode()));
    }

}
