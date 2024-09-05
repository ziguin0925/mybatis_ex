package com.fastcampus.toyproject2.Item.product.controller;

import com.fastcampus.toyproject2.Item.category.dto.Category;
import com.fastcampus.toyproject2.Item.category.dto.SubCategoryDto;
import com.fastcampus.toyproject2.Item.category.service.CategoryService;
import com.fastcampus.toyproject2.Item.product.dto.pagination.Page;
import com.fastcampus.toyproject2.Item.product.service.ProductService;
import com.fastcampus.toyproject2.Item.product.dto.ProductAdminList;
import com.fastcampus.toyproject2.Item.product.dto.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    //페이지마다 컨트롤러가 하나씩 있는게 아님. (랭킹, 세일, 이벤트 등등 숫자로 구분) - 무신사

    /*
    * 일단 모름.
    * page 번호 넘겨줘서 페이지 번호에 따라 sortCode Service에서 변경하기.
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
        }else if(pageNumber == Page.SALESPAGE){
            return "main/saleQuantity";
        }else if(pageNumber == Page.LOWPRICE){
            return "main/lowPrice";
        }else if(pageNumber == Page.MAINPAGE){
            return "main/main";
        }

        return"redirect:/product/100";
    }


    /*
    *   상품 상세 페이지 연결
    *
    *   상품 코드를 숫자로 할지 생각.
    *   디테일 페이지에 들어가면 자동으로 조회수 증가 하도록
    * */
    @GetMapping("/detail/{productId}")
    public String productDetailPage(@PathVariable String productId, Model model) {
            try {

                model.addAttribute("productDetail", productService.findProductDetailById(productId));

                //일단 조회수 중복 증가 방지를 위해 따로 빼둠. -> 나중에 cookie 등으로 중복 증가 하지 못하게등 생각해보기.
                productService.viewCount(productId);

            }catch (Exception e){
                e.printStackTrace();
                //해당 상품을 못찾으므로 error페이지를 띄워주거나 error 메세지를 클라이언트에게 띄우기.
                return "error";
            }
        return "product/productDetail";
    }

    /*
    *
    * 상품 등록 페이지
    *
    * (홈페이지 관리자, 각 브랜드 담당자, 일반 회원)
    * 해당 브랜드의 사람이 등록을 할건데. 브랜드 Id를 path로 주는게 맞는지.
    * */
    @GetMapping("/admin/register")
    public String adminRegisterPage(Model model) throws Exception {
        //카테고리 가져오기.
        //카테고리를 선택할 건데 - 화면에 어떻게 뿌려줄건지.

        //상세 설명 가져오기. - 상세설명에 브랜드도 있어야 할까 - 어떤 상품에 어떤 상세 설명이 쓰였는지?, 어떤 카테고리에 어떤 상세 설명이 쓰였는지?

        List<SubCategoryDto> categoryList = categoryService.findSubCategory(Category.FIRSTCATEGORY);

        model.addAttribute("categoryList", categoryList);

        return "product/register";
    }



    /*
    * 등록한 상품 목록 페이지 - 등록 상품 목록 페이지에 수정, 제거 버튼 넣기.
     *
     *
    * */

    @GetMapping("/admin/list")
    public String adminProductList(Model model) throws Exception {
        List<ProductAdminList>  productAdminList = productService.findProductAdminList();
        model.addAttribute("products", productAdminList);
        return "product/adminProductList";
    }

    @GetMapping("imagetest")
    public String S3ImageTest(){
        return "test/ImageTest";
    }





    /*
    *  상품 수정 페이지
    *
    * */

    @GetMapping("/admin/{productId}")
    public String adminEditProduct(@PathVariable("productId") String productId, Model model) throws Exception {

        //해당 productId의 상세 정보 불러오기.
        ProductDetailDto productData =productService.findProductDetailById(productId);
        model.addAttribute("productData", productData);

        //C001 의류 의 한단계 아래 카테고리 목록 불러오기.
        List<SubCategoryDto> categoryList = categoryService.findSubCategory(Category.FIRSTCATEGORY);
        model.addAttribute("categoryList", categoryList);

        return "product/adminProductEdit";
    }

    @GetMapping("/search")
    public String searchPage(Model model){


        return "product/searchPage";
    }






}
