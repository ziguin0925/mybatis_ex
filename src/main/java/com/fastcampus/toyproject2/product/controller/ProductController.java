package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.product.dto.pagination.Page;
import com.fastcampus.toyproject2.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;



    //페이지마다 컨트롤러가 하나씩 있는게 아님. (랭킹, 세일, 이벤트 등등 숫자로 구분)

    /*
    * 일단 모름.
    * */
    @GetMapping("/{pageNumber}")
    public String mainpage(@PathVariable int pageNumber, Model model){
        if(pageNumber== Page.RANKINGPAGE){
            /*
            * 어느정도 정보 주기.
            * */
            return "main/ranking";
        }else if(pageNumber == Page.NEWPAGE){
            return "main/new";
        }
        return"main";

    }


    /*
    *   상품 상세 페이지 연결
    *
    *   상품 코드를 숫자로 할지 생각.
    * */
    @GetMapping("/{productId}")
    public String productDetailPage(@PathVariable String productId, Model model) throws Exception {

        model.addAttribute("productDetail", productService.findProductDetailById(productId));

        return "/product/productDetail";
    }








}
