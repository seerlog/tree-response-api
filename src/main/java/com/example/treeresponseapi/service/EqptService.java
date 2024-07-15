package com.example.treeresponseapi.service;

import com.example.treeresponseapi.domain.eqpt.EqptRepository;
import com.example.treeresponseapi.domain.part.PartRepository;
import com.example.treeresponseapi.vo.EqptMenuVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
        List<EqptMenuVo> eqptMenuPrntNodes = eqptMenus.stream()
                .filter(eqptMenu -> eqptMenu.getLevel() == 3 && eqptMenu.getLeafYn().equals("Y"))
                .collect(Collectors.toList());
        List<String> eqptMenuPrntMenuCds = eqptMenuPrntNodes.stream().map(EqptMenuVo::getMenuCd).collect(Collectors.toList());
        List<EqptMenuVo> eqptItems = eqptRepository.findByEqptMenuPrntMenuCdIn(eqptMenuPrntMenuCds).stream()
                .map(e -> EqptMenuVo.of(e, eqptMenuPrntNodes.get(0).getLevel() + 1)).collect(Collectors.toList());

        // 루트 노드 조회
        EqptMenuVo root = eqptMenus.stream().filter(vo -> vo.getLevel() == 0).findFirst().orElse(null);

        // 트리 깊이 확인
        int maxDepth = eqptMenus.stream().mapToInt(EqptMenuVo::getLevel).max().orElse(0) + 1;

        // 트리 메뉴 생성
        generateTree(maxDepth, root, eqptMenus, eqptItems);

        // 미사용 노드 제거
        removeUnusedNodes(maxDepth, root);

        return root;
    }

    private void generateTree(int maxDepth, EqptMenuVo root, List<EqptMenuVo> eqptMenus, List<EqptMenuVo> eqptItems) {
        for (int i = 1; i <= maxDepth; i++) {
            final int finalI = i;
            List<EqptMenuVo> currentDepth = eqptMenus.stream()
                    .filter(vo -> vo.getLevel() == finalI).collect(Collectors.toList());
            if(i == 1) {
                root.setChildren(currentDepth);
                root.setChildrenCnt(currentDepth.size());
            }
            for (EqptMenuVo current : currentDepth) {
                List<EqptMenuVo> children = eqptMenus.stream()
                        .filter(vo -> vo.getPrntMenuCd().equals(current.getMenuCd())).collect(Collectors.toList());
                if(ObjectUtils.isEmpty(children)) {
                    List<EqptMenuVo> items = eqptItems.stream()
                            .filter(vo -> vo.getPrntMenuCd().equals(current.getMenuCd())).collect(Collectors.toList());
                    current.setChildren(items);
                    current.setChildrenCnt(items.size());
                } else {
                    current.setChildren(children);
                    current.setChildrenCnt(children.size());
                }
            }
        }
    }

    private void removeUnusedNodes(int maxDepth, EqptMenuVo root) {
        // 루트 노드와 리프 노드를 제외한 중간 노드를 검사
        for (int i = 0; i < maxDepth - 1; i++) {
            removeUnusedNode(1, maxDepth, root);
        }
    }

    private String removeUnusedNode(int depth, int maxDepth, EqptMenuVo node) {
        if(depth > maxDepth) {
            return null;
        }

        if(node.getChildren().size() == 0) {
            return node.getMenuCd();
        }

        List<String> deleteChildMenuCds = new ArrayList<>();
        for(EqptMenuVo child : node.getChildren()) {
            String deleteMenuCd = removeUnusedNode(depth + 1, maxDepth, child);
            if(!ObjectUtils.isEmpty(deleteMenuCd)){
                deleteChildMenuCds.add(deleteMenuCd);
            }
        }

        if(deleteChildMenuCds.size() > 0) {
            List<EqptMenuVo> deleteChildNodes = node.getChildren().stream()
                    .filter(child -> deleteChildMenuCds.contains(child.getMenuCd()))
                    .collect(Collectors.toList());
            node.getChildren().removeAll(deleteChildNodes);
            node.setChildrenCnt(node.getChildren().size());
        }

        return null;
    }
}
