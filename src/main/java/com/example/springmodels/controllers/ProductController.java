package com.example.springmodels.controllers;

import com.example.springmodels.models.ProductModel;
import com.example.springmodels.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
@PreAuthorize("hasAnyAuthority('MODER', 'USER')")
public class ProductController extends CrudController<ProductModel, Long> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        super(productRepository, "product");
        this.productRepository = productRepository;
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam("name") String name, Model model) {
        List<ProductModel> productByName = productRepository.findByName(name);
        model.addAttribute("products", productByName);
        return "products/search_results";
    }
}
