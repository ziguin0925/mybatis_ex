package com.fastcampus.toyproject2.product.controller;

import com.fastcampus.toyproject2.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


}
