package com.example.treeresponseapi.domain.epqtMenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eqpt_menu")
public class EqptMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String partCd;
    private String menuCd;
    private String prntMenuCd;
    private String menuNm;
    private int level;
}
