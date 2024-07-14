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
import org.springframework.util.ObjectUtils;

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
        EqptMenuVo root = eqptMenus.stream().filter(vo -> vo.getLevel() == 0).findFirst().orElse(null);
        int maxDepth = eqptMenus.stream().mapToInt(EqptMenuVo::getLevel).max().orElse(0);

        for (int i = 1; i <= maxDepth; i++) {
            final int finalI = i;
            List<EqptMenuVo> currentDepth = eqptMenus.stream()
                    .filter(vo -> vo.getLevel() == finalI).collect(Collectors.toList());
            if(i == 1) {
                root.setChildren(currentDepth);
            }
            for (EqptMenuVo current : currentDepth) {
                List<EqptMenuVo> children = eqptMenus.stream()
                        .filter(vo -> vo.getPrntMenuCd().equals(current.getMenuCd())).collect(Collectors.toList());
                if(ObjectUtils.isEmpty(children)) {
                    // 장비 아이템 리스트 세팅하기
                    // 장비 아이템은 children 노드를 가지면 안된다.
                } else {
                    current.setChildren(children);
                }
            }
        }

        return root;
    }
}
