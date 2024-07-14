package com.example.treeresponseapi.vo;

import com.example.treeresponseapi.domain.part.PartRepository;
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
public class LastEqptMenuVo {
    private String partCd;
    private String menuNm;
    private String menuCd;
    private String prntMenuCd;
    private int level;
    private String leafYn;

    public static LastEqptMenuVo of(PartRepository.EqptMenuNativeVo vo) {
        return LastEqptMenuVo.builder()
                .partCd(vo.getPartCd())
                .menuNm(vo.getMenuNm())
                .menuCd(vo.getMenuCd())
                .prntMenuCd(vo.getPrntMenuCd())
                .level(vo.getLevel())
                .leafYn(vo.getLeafYn())
                .build();
    }
}
