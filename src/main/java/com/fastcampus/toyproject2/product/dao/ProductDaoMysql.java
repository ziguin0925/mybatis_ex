package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.*;
import com.fastcampus.toyproject2.product.dto.pagination.cursor.ProductCursorPageDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Repository
@MapperScan
public class ProductDaoMysql implements ProductDao {

    private final SqlSession sqlSession;

    private String namespace ="productMapper.";


    @Override
    public int insertTest(Product product) throws Exception{
        return sqlSession.insert(namespace+"insert", product);
    }

    @Override
    public int insert(HashMap<String, Object> testMap) throws Exception {

        return sqlSession.insert(namespace + "insertTest", testMap);
    }

    @Override
    public String findNameById(String productId) throws Exception {
        return sqlSession.selectOne(namespace+"findNameById", productId);
    }


    @Override
    public ProductDetailDto findProductDetailById(String productId) throws Exception {
        return sqlSession.selectOne(namespace + "findProductDetailDtoById", productId);
    }

    @Override
    public List<ProductAdminList> findProductAdminList() throws Exception {
        return sqlSession.selectList(namespace + "findProductAdminList");
    }

    @Override
    public List<ProductCursorPageDto> findCursorList(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorPaging", map);
    }

    @Override
    //별점 순
    public List<ProductCursorPageDto> findCursorPageListOrderByRank(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorRankingPage", map);
    }

    @Override
    //판매량 순
    public List<ProductCursorPageDto> findCursorPageListOrderBySales(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorSalesQuantityPage", map);
    }

    @Override
    public List<ProductCursorPageDto> findCursorPageListByHighPrice(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorHighPricePage", map);
    }

    @Override
    public List<ProductCursorPageDto> findCursorPageListByLowPrice(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorLowPricePage", map);
    }

    @Override
    public List<ProductCursorPageDto> findCursorPageListByLastest(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorLatestPage", map);
    }


    //페이지 기반
    //총 상품 개수 / 현재 페이지  / 보여줄 페이지 / 페이지당 사이즈
    //offset, count, page, size
    @Override
    public List<ProductPageDto> findPageList(HashMap<String, Object> pageMap) throws Exception {
        return sqlSession.selectList(namespace + "pagePaging", pageMap);
    }
    @Override
    public int countProduct(HashMap<String, Object> countMap) throws Exception {
        return sqlSession.selectOne(namespace + "countProduct", countMap);
    }

    @Override
    public int updateProduct(ProductUpdateDto productUpdateDto) throws Exception {
        return sqlSession.update(namespace+"updateProduct", productUpdateDto);
    }




    @Override
    public int deleteByProductId(String productId) throws Exception {
        return sqlSession.delete(namespace + "deleteByProductId", productId);
    }


}
