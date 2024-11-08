package com.toyproject2_5.musinsatoy.salesorder.dao;

import com.toyproject2_5.musinsatoy.salesorder.dto.SalesOrder;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SalesOrderDaoMysql implements SalesOrderDao {
    @Autowired
    private SqlSession session;
    private final String namespace = "com.toyproject2_5.musinsatoy.salesorder.dao.SalesOrderMapper.";

    @Override
    public void insert(SalesOrder order) {
        session.insert(namespace+"insert", order);
    }

    @Override
    public List<SalesOrder> selectAllByLessThanIdAndMemberIdAndOrderByDesc(String orderId, int memberId, int size) {
        Map map = new HashMap();
        map.put("orderId", orderId);
        map.put("memberId", memberId);
        map.put("size", size);
        return session.selectList(namespace+"selectAllByLessThanIdAndMemberIdAndOrderByDesc", map);
    }

    @Override
    public List<SalesOrder> selectAllByMemberIdAndOrderByDesc(int memberId, int size) {
        Map<String, Integer> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("size", size);
        return session.selectList(namespace+"selectAllByMemberIdAndOrderByDesc", map);
    }

    @Override
    public SalesOrder selectOrder(String orderId) {
        return session.selectOne(namespace+"selectOrder", orderId);
    }

    @Override
    public SalesOrder selectOrderAndDetail(String orderId) {
        return session.selectOne(namespace+"selectOrderAndDetail", orderId);
    }

    @Override
    public void updateState(SalesOrder order) {
        session.update(namespace+"updateState", order);
    }

    @Override
    public void deleteAll() {
        session.delete(namespace+"deleteAll");
    }

    @Override
    public int countOrder() {
        return session.selectOne(namespace+"countOrder");
    }

}
