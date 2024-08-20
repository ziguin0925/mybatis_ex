//package com.fastcampus.toyproject2.salesorder;
//
//import com.fastcampus.toyproject2.salesorder.dao.SalesOrderDao;
//import com.fastcampus.toyproject2.salesorder.dto.SalesOrder;
//import com.fastcampus.toyproject2.salesorder.dto.SalesOrderDetail;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class DaoTest {
//    @Autowired
//    SalesOrderDao dao;
//
//    /**
//     * 테스트용 메소드 - C - R - U - D 순서
//     */
//
//    // 현재 시간으로 orderId값 생성
//    private String makeOrderId(){
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSSSSS");
//        return now.format(formatter);
//    }
//
//    // 주문상세 만들기
//    private SalesOrderDetail makeSalesOrderDetail(String orderId, String productId){
//        return SalesOrderDetail.builder()
//                .orderId(orderId)
//                .productId(productId).color("W").size("S")
//                .build();
//    }
//
//    // 주문 만들기
//    private SalesOrder makeSalesOrder(int detailUnit){
//        String orderId = makeOrderId();
//        String productId = "TESTprodId";
//        List<SalesOrderDetail> detailList = new ArrayList<>();
//        for(int i=0; i<detailUnit; i++){
//            detailList.add(makeSalesOrderDetail(orderId, productId+i));
//        }
//        return SalesOrder.builder()
//                .orderId(orderId)
//                .memberId(1).addressA("test address").phone("01000000000")
//                .salesOrderDetailList(detailList)
//                .build();
//    }
//
//
//    /**
//     * CREATE 테스트
//     */
//    @Test
//    public void insertTest() {
//        // 추가 테스트 (검증 없음)
//        String orderId = makeOrderId();
//        SalesOrderDetail detail = SalesOrderDetail.builder()
//                .orderId(orderId)
//                .productId("BBAA12321SS").color("W").size("XL")
//                .build();
//        List<SalesOrderDetail> list = new ArrayList<>();
//        list.add(detail);
//
//        SalesOrder order = SalesOrder.builder()
//                .orderId(orderId).memberId(1)
//                .recipient("패스트").phone("01011112222").addressA("서울특별시 강남구 강남대로 364").addressB("10층").postcode("06241").deliveryRequest("문 앞에 놔주세요")
//                .paymentId(111122223333L).paymentMethod("CREDIT_CARD").paymentAmount(50000)
//                .salesOrderDetailList(list)
//                .build();
//
//        dao.insert(order);
//    }
//
//    @Test
//    public void insertTestWithCheck(){
//        // 추가 작업
//        SalesOrder inserted = makeSalesOrder(3);   // 하드코딩을 고려
//        dao.insert(inserted);
//
//        // 검증  작업
//        SalesOrder selected = dao.selectOrderAndDetail(inserted.getOrderId());
//        assertEquals(inserted.getOrderId(), selected.getOrderId());
//
//        // Order 안의 order detail List 검증
//        List<SalesOrderDetail> insertedList = inserted.getSalesOrderDetailList();
//        List<SalesOrderDetail> selectedList = selected.getSalesOrderDetailList();
//        // 입력한 / 검색된 리스트의 순서가 다를 수 있기 때문에 정렬
//        insertedList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getOrderId())));
//        selectedList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getOrderId())));
//
//        for(int i=0; i<insertedList.size(); i++){
//            assertEquals(insertedList.get(i).getOrderId(),
//                    selectedList.get(i).getOrderId());
//            assertEquals(insertedList.get(i).getColor(),
//                    selected.getSalesOrderDetailList().get(i).getColor());
//            assertEquals(insertedList.get(i).getSize(),
//                    selected.getSalesOrderDetailList().get(i).getSize());
//        }
//    }
//
//
//    /**
//     * READ 테스트
//     */
//    @Test
//    public void selectTest() {
//        // 전체 삭제
//        dao.deleteAll();
//
//        // 추가
//        SalesOrderDetail detail = SalesOrderDetail.builder()
//                .orderId("202408071320111111")
//                .productId("AAA123BBB").color("R").size("L")
//                .build();
//        List<SalesOrderDetail> list = new ArrayList<>();
//        list.add(detail);
//
//        SalesOrder order = SalesOrder.builder().orderId("202408071320111111")
//                .memberId(1).recipient("패스트").phone("01011112222")
//                .addressA("서울특별시 강남구 강남대로 364").addressB("10층")
//                .postcode("06241").deliveryRequest("문 앞에 놔주세요")
//                .paymentId(111122223333L).paymentMethod("CREDIT_CARD").paymentAmount(50000)
//                .salesOrderDetailList(list)
//                .build();
//        dao.insert(order);
//
//        // 추가한 것을 선택했음을 확인
//        SalesOrder selectedOrder = dao.selectOrder(order.getOrderId());
//        assertNotNull(selectedOrder);
//        assertEquals(selectedOrder.getRecipient(), order.getRecipient());
//        assertEquals(selectedOrder.getPostcode(), order.getPostcode());
//        assertEquals(selectedOrder.getPaymentId(),order.getPaymentId());
//    }
//
//    // 주문과 상세 선택
//    @Test
//    public void selectOrderAndDetailTest(){
//        // 삭제
//        dao.deleteAll();
//        // 추가
//        SalesOrder inserted = makeSalesOrder(1);
//        dao.insert(inserted);
//        // 선택해서 비교
//        SalesOrder selected = dao.selectOrderAndDetail(inserted.getOrderId());
//        assertEquals(selected.getOrderId(), inserted.getOrderId());
//        assertEquals(selected.getSalesOrderDetailList().get(0).getOrderId(), inserted.getOrderId());
//    }
//
//    // 사용자 pk로 주문 정보 선택
//    @Test
//    public void selectByMemberIdTest(){
//        // 삭제
//        dao.deleteAll();
//        // 추가
//        SalesOrder inserted = makeSalesOrder(1);
//        String orderId = inserted.getOrderId();
//        dao.insert(inserted);
//        // 선택해서 비교
//        List<SalesOrder> orders = dao.selectById(1);
//        assertEquals(orderId, orders.get(0).getOrderId());
//        assertEquals(orderId, orders.get(0).getSalesOrderDetailList().get(0).getOrderId());
//    }
//
//    // row 개수 세기
//    @Test
//    public void countTest() {
//        // 삭제
//        dao.deleteAll();
//        assertEquals(0, dao.countOrder());
//
//        // 추가
//        dao.insert(makeSalesOrder(1));
//
//        // 개수 비교
//        assertEquals(1, dao.countOrder());
//
//        // 여러개 추가해서 비교
//        for(int i = 0; i<10; i++){
//            dao.insert(makeSalesOrder(2));
//        }
//        assertEquals(11, dao.countOrder());
//    }
//
//    /**
//     * UPDATE 테스트
//     */
//    // 상태를 변경
//    @Test
//    public void updateStateTest(){
//        // 추가 작업, insertion시 state code는 0
//        SalesOrder inserted = makeSalesOrder(5);
//        String orderId = inserted.getOrderId();
//        dao.insert(inserted);
//        SalesOrder insertedOrder = dao.selectOrder(orderId);
//        System.out.println("insertedOrder = " + insertedOrder);
//        assertEquals(0, insertedOrder.getStateCode());
//
//        // state code를 수정해서 비교
//        SalesOrder updateInfo = SalesOrder.builder()
//                .orderId(orderId)
//                .stateCode(3).build();
//        dao.updateState(updateInfo);
//        assertEquals(3, dao.selectOrder(orderId).getStateCode());
//    }
//
//    /**
//     * DELETE 테스트
//     */
//    @Test
//    public void deleteAllTest(){
//        dao.deleteAll();
//        assertEquals(0, dao.countOrder());
//    }
//
//    @Test
//    public void deleteAllTestWithInsert(){
//        dao.deleteAll();
//        assertEquals(0, dao.countOrder());
//        dao.deleteAll();
//        dao.insert(makeSalesOrder(2));
//        assertEquals(1, dao.countOrder());
//    }
//
//    // 다른 것이 삭제되지는 않는지를 검증하기 추가
//    // 실패 테스트 추가
//}
