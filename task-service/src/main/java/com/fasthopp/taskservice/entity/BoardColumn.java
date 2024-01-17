package com.fasthopp.taskservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_column")
public class BoardColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "column_order")
    private Integer columnOrder;

    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
