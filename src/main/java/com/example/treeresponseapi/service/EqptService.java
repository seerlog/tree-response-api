package com.example.treeresponseapi.service;

import com.example.treeresponseapi.domain.eqpt.EqptRepository;
import com.example.treeresponseapi.domain.part.PartRepository;
import com.example.treeresponseapi.vo.EqptMenuVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EqptService {
    private final PartRepository partRepository;
    private final EqptRepository eqptRepository;

    public EqptMenuVo getEqptMenuList(String keyword) {
        // 장비 메뉴 조회
        List<EqptMenuVo> eqptMenus = partRepository.getEqptMenuList(keyword).stream().map(EqptMenuVo::of).collect(Collectors.toList());

        // 장비 목록 조회
//        List<EqptMenuVo> eqptItems = eqptRepository.findByEqptMenuPrntMenuCdIn().stream().map(EqptMenuVo::of).collect(Collectors.toList());

        // 루트 노드 조회
        EqptMenuVo root = eqptMenus.stream().filter(vo -> vo.getLevel() == 0).findFirst().orElse(null);

        // 트리 깊이 확인
        int maxDepth = eqptMenus.stream().mapToInt(EqptMenuVo::getLevel).max().orElse(0);

        // 트리 메뉴 생성
        EqptMenuVo tree = convertToTree(maxDepth, root, eqptMenus);

        // 미사용 노드 제거

        return root;
    }

    private EqptMenuVo convertToTree(int maxDepth, EqptMenuVo root, List<EqptMenuVo> eqptMenus) {
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

    private void removeUnusedNodes(int maxDepth, EqptMenuVo root) {

    }

    private void removeUnusedNode(int depth, int maxDepth, EqptMenuVo node) {

    }
}
