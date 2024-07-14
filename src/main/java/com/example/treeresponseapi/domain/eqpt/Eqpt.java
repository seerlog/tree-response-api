package com.example.treeresponseapi.domain.eqpt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eqpt")
public class Eqpt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String partCd;
    private String eqptMenuMenuCd;
    private String eqptMenuPrntMenuCd;
    private String eqptNm;
    private String loc;
}
