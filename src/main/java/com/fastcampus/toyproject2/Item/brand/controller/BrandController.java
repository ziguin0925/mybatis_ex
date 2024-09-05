package com.fastcampus.toyproject2.Item.brand.controller;


import com.fastcampus.toyproject2.Item.brand.dto.BrandListDto;
import com.fastcampus.toyproject2.Item.brand.dto.page.BrandRequestPageDto;
import com.fastcampus.toyproject2.Item.brand.dto.page.BrandPageInfo;
import com.fastcampus.toyproject2.Item.brand.service.BrandService;
import com.fastcampus.toyproject2.Item.product.dto.ProductPageDto;
import com.fastcampus.toyproject2.Item.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.Item.product.dto.pagination.ProductRequestPageDto;
import com.fastcampus.toyproject2.Item.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final ProductService productService;

    private final BrandService brandService;

    @GetMapping(value ={ "/main"})
    public String brand(BrandRequestPageDto brandRequestPageDto, Model model) throws Exception {

        BrandPageInfo brandPageInfo = new BrandPageInfo();
        brandPageInfo.setPaging(brandRequestPageDto);
        System.out.println(brandPageInfo);



        List<BrandListDto> brandList = brandService.findBrandMainPage(brandPageInfo);
        model.addAttribute("brands", brandList);
        model.addAttribute("pageInfo", brandPageInfo);

        return "brand/brandList";
    }


    /*
    *   브랜드
    *
    *
    * */
    @GetMapping(value ={ "/{brandCode}"})
    public String brandpage(
            @ModelAttribute @PathVariable(value = "brandCode") String brandId
            , Model model
            , ProductRequestPageDto productRequestPageDto) throws Exception {


        productRequestPageDto.setBrandId(brandId);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPaging(productRequestPageDto);
        List<ProductPageDto> productPageDtos = productService.findPageList(pageInfo);

        model.addAttribute("brandId",brandId);
        model.addAttribute("productsList", productPageDtos);
        model.addAttribute("pageInfo", pageInfo);

        return "brand/brandPage";
    }


    @GetMapping("/admin/register")
    public String brandRegisterpage() {
        //브랜드 등록 창은 정말 아무런 정보가 안담겨도 괜찮을까

        return "brand/register";
    }


}
