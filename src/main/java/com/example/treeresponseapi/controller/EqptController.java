package com.example.treeresponseapi.controller;

import com.example.treeresponseapi.domain.part.PartRepository;
import com.example.treeresponseapi.service.EqptService;
import com.example.treeresponseapi.vo.EqptMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EqptController {
    private final EqptService eqptService;

    @GetMapping
    public ResponseEntity<List<PartRepository.EqptMenuNativeVo>> getEqptMenuList() {
        return ResponseEntity.ok(eqptService.getEqptMenuList());
    }
}
