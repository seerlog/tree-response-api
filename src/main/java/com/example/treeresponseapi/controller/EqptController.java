package com.example.treeresponseapi.controller;

import com.example.treeresponseapi.service.EqptService;
import com.example.treeresponseapi.vo.EqptMenuVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EqptController {
    private final EqptService eqptService;

    @GetMapping
    public ResponseEntity<EqptMenuVo> getEqptMenuList(@RequestParam("keyword") String keyword) throws JsonProcessingException {
        return ResponseEntity.ok(eqptService.getEqptMenuList(keyword));
    }
}
