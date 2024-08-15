package com.fastcampus.toyproject2.brand.controller;


import com.fastcampus.toyproject2.product.dto.pagination.PageInfo;
import com.fastcampus.toyproject2.product.dto.pagination.ProductRequestPageDto;
import com.fastcampus.toyproject2.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final ProductService productService;


    /*
    *
    * */
    @GetMapping(value ={ "/{brandCode}/products/list"})
    public String brandpage(
            @PathVariable(value = "brandCode") String brandCode
            , Model model
            , ProductRequestPageDto productRequestPageDto) throws Exception {

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPaging(productRequestPageDto);

        model.addAttribute("productsList", productService.findPageList(pageInfo));
        model.addAttribute("pageInfo", pageInfo);

        return "brand/productsList";
    }


    @GetMapping("/register")
    public String brandRegisterpage(Model model) {
        return "brand/register";
    }


}
