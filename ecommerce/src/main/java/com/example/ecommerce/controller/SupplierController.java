package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

//    @GetMapping("/{supplierId}")
//    public ResponseEntity<Supplier> getSupplierById(@PathVariable int supplierId) {
//        Supplier supplier = supplierService.getSupplierById(supplierId);
//        return supplier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

//    @PutMapping("/{supplierId}")
//    public ResponseEntity<Supplier> updateSupplier(@PathVariable int supplierId, @RequestBody Supplier supplier) {
//        if (supplierService.getSupplierById(supplierId).isPresent()) {
//            supplier.setSupplierId(supplierId);
//            Supplier updatedSupplier = supplierService.updateSupplier(supplier);
//            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @DeleteMapping("/{supplierId}")
//    public ResponseEntity<Void> deleteSupplier(@PathVariable int supplierId) {
//        if (supplierService.getSupplierById(supplierId).isPresent()) {
//            supplierService.deleteSupplier(supplierId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
