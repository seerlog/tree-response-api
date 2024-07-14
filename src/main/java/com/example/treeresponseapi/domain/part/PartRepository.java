package com.example.treeresponseapi.domain.part;

import com.example.treeresponseapi.vo.EqptMenuVo;
import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    @Query(value = "SELECT " +
            "    part_cd AS PART_CD, " +
            "    part_nm AS MENU_NM, " +
            "    '' AS MENU_CD, " +
            "    '' AS PRNT_MENU_CD, " +
            "    '0' AS LEVEL, " +
            "    'N' AS LEAF_YN " +
            "FROM " +
            "    part " +
            "UNION ALL " +
            "SELECT " +
            "    em.PART_CD, " +
            "    em.MENU_NM, " +
            "    em.MENU_CD, " +
            "    em.PRNT_MENU_CD, " +
            "    em.LEVEL, " +
            "    CASE " +
            "        WHEN e.leaf_yn IS NULL THEN 'N' " +
            "        ELSE leaf_yn " +
            "    END AS LEAF_YN " +
            "FROM " +
            "    eqpt_menu em " +
            "        LEFT OUTER JOIN " +
            "    (SELECT *, 'Y' AS LEAF_YN FROM eqpt WHERE eqpt.loc LIKE '%문화관%') e " +
            "        ON em.menu_cd = e.eqpt_menu_prnt_menu_cd " +
            "GROUP BY em.MENU_NM ", nativeQuery = true)
    List<EqptMenuNativeVo> getEqptMenuList();

    interface EqptMenuNativeVo {
        String getPartCd();
        String getMenuNm();
        String getMenuCd();
        String getPrntMenuCd();
        int getLevel();
        String getLeafYn();
    }
}
