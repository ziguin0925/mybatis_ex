package com.fastcampus.toyproject2.product.dao;

import com.fastcampus.toyproject2.product.dto.Product;
import com.fastcampus.toyproject2.product.dto.ProductDetailDto;
import com.fastcampus.toyproject2.product.dto.ProductListDto;
import com.fastcampus.toyproject2.product.dto.ProductUpdateDto;
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
    public String findNameById(String productId) throws Exception {
        return sqlSession.selectOne(namespace+"findNameById", productId);
    }

    @Override
    public int insert(Product product) throws Exception{
        return sqlSession.insert(namespace+"insert", product);
    }

    @Override
    public int deleteByProductId(String productId) throws Exception {
        return sqlSession.delete(namespace + "deleteByProductId", productId);
    }

    @Override
    public ProductDetailDto findProductDetailById(String productId) throws Exception {
        return sqlSession.selectOne(namespace + "findProductDetailDtoById", productId);
    }

    @Override
    public List<ProductListDto> findCursorList(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectList(namespace + "cursorPaging", map);
    }


    //페이지 기반
    //총 상품 개수 / 현재 페이지  / 보여줄 페이지 / 페이지당 사이즈
    //offset, count, page, size
    @Override
    public List<ProductListDto> findPageList(HashMap<String, Object> pageMap) throws Exception {
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
    public int insertTest(HashMap<String, Object> testMap) throws Exception {

        return sqlSession.insert(namespace + "insertTest", testMap);
        }


}
