package com.example.treeresponseapi.service;

import com.example.treeresponseapi.domain.eqpt.EqptRepository;
import com.example.treeresponseapi.domain.part.PartRepository;
import com.example.treeresponseapi.vo.EqptMenuVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EqptService {
    private final PartRepository partRepository;
    private final EqptRepository eqptRepository;

    public List<PartRepository.EqptMenuNativeVo> getEqptMenuList() {
        return partRepository.getEqptMenuList();
    }
}
