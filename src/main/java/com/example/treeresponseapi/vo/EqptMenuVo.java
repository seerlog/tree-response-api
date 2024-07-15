package com.example.treeresponseapi.vo;

import com.example.treeresponseapi.domain.eqpt.Eqpt;
import com.example.treeresponseapi.domain.part.PartRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EqptMenuVo {
    private String partCd;
    private String menuNm;
    private String menuCd;
    private String prntMenuCd;
    private int level;
    private String leafYn;
    private int childrenCnt;
    private List<EqptMenuVo> children;

    public static EqptMenuVo of(PartRepository.EqptMenuNativeVo vo) {
        return EqptMenuVo.builder()
                .partCd(vo.getPartCd())
                .menuNm(vo.getMenuNm())
                .menuCd(vo.getMenuCd())
                .prntMenuCd(vo.getPrntMenuCd())
                .level(vo.getLevel())
                .leafYn(vo.getLeafYn())
                .children(new ArrayList<>())
                .build();
    }

    public static EqptMenuVo of(Eqpt eqpt, int level) {
        return EqptMenuVo.builder()
                .partCd(eqpt.getPartCd())
                .menuNm(eqpt.getEqptNm())
                .menuCd(eqpt.getEqptMenuMenuCd())
                .prntMenuCd(eqpt.getEqptMenuPrntMenuCd())
                .level(level)
                .leafYn(null)
                .children(null)
                .build();
    }
}
