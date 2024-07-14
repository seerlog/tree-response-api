package com.example.treeresponseapi.service;

import com.example.treeresponseapi.domain.eqpt.EqptRepository;
import com.example.treeresponseapi.domain.part.PartRepository;
import com.example.treeresponseapi.vo.EqptMenuVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EqptService {
    private final PartRepository partRepository;
    private final EqptRepository eqptRepository;

    public EqptMenuVo getEqptMenuList() throws JsonProcessingException {
        List<EqptMenuVo> eqptMenus = partRepository.getEqptMenuList().stream().map(EqptMenuVo::of).collect(Collectors.toList());
        EqptMenuVo tree = convertToTree(eqptMenus);
        return tree;
    }

    private EqptMenuVo convertToTree(List<EqptMenuVo> eqptMenus) {
        EqptMenuVo root = eqptMenus.stream()
                .filter(vo -> vo.getLevel() == 0).findFirst().orElse(null);

        List<EqptMenuVo> firstDepths = eqptMenus.stream()
                .filter(vo -> vo.getLevel() == 1).collect(Collectors.toList());
        root.setChildren(firstDepths);

        for(EqptMenuVo firstDepth : firstDepths) {
            List<EqptMenuVo> secondDepths = eqptMenus.stream()
                    .filter(vo -> vo.getPrntMenuCd().equals(firstDepth.getMenuCd())).collect(Collectors.toList());
            firstDepth.setChildren(secondDepths);
            for(EqptMenuVo secondDepth : secondDepths) {
                List<EqptMenuVo> thirdDepths = eqptMenus.stream()
                        .filter(vo -> vo.getPrntMenuCd().equals(secondDepth.getMenuCd())).collect(Collectors.toList());
                secondDepth.setChildren(thirdDepths);
            }
        }

        return root;
    }
}
