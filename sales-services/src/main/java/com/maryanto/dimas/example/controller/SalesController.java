package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.entity.Sales;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;

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

    @PostMapping("/{id}/findById")
    public ResponseEntity<Sales> findById(@PathVariable("id") String id, @RequestParam("nip") String nip) {
        Predicate<Sales> predicate = data -> StringUtils.endsWithIgnoreCase(data.getNip(), id);
        Optional<Sales> sales = list.stream().filter(predicate).findFirst();
        return sales.isPresent() ? ResponseEntity.ok(sales.get()) : ResponseEntity.noContent().build();
    }
}
