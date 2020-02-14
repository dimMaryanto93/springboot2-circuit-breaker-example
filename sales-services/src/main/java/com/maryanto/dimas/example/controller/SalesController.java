package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.entity.Sales;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private List<Sales> list = new ArrayList<>();

    public SalesController() {
        list.addAll(Arrays.asList(
                new Sales(UUID.randomUUID().toString(), "Dimas Maryanto", "Bandung", "PT. Hasjrat Abadi"),
                new Sales(UUID.randomUUID().toString(), "Abdul", "Jakarta", "PT. Kombos"),
                new Sales(UUID.randomUUID().toString(), "Muhammad Yusuf", "Bandung", "PT. Toyota"),
                new Sales(UUID.randomUUID().toString(), "Prima", "Bandung", "PT. Kombos")
        ));
    }

    @GetMapping("/list")
    public List<Sales> findAll() {
        return list;
    }
}
