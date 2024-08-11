package com.fastcampus.toyproject2.product.dto.pagination;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductPageDto {

    //요청 받은 페이지 번호
    private int pageNum;
    private int countPerPage;

    private String brandId;

    //브랜드 페이지 에서 카테고리 ID 별 키워드 검색 도 생각해보기.
    private String keyword;

    private String categoryId;

    //byDate, starRate, sales, price
    //되는지 먼저 보고 orderName = p.create_datetime, p.star_rating등으로 바꿔서 넣기.

    /* pagePaging
    * <when test ="orderName != null">
            ${orderName}  ${orderBy}
    * </when>
    * orderName Name 과 orderBy는 여기서 static 변수로 값 넣어주기.
    *
    * */
    private String orderName;

    //DESC, ASC
    private String orderBy;


    public ProductPageDto() {
        this.pageNum = 1;
        this.countPerPage = 10;
    }
}
