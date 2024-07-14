package com.example.treeresponseapi.domain.part;

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
@Table(name = "eqpt_menu")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String partCd;
    private String partNm;
}
