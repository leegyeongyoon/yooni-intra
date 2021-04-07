package com.yooni.intra.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tbl")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    @Column(nullable = false,unique = true,length = 30)
    private String id;

    @Column(nullable = false,length = 100)
    private String name;

}
