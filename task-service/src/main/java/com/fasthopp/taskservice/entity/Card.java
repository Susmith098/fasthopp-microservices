package com.fasthopp.taskservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer maxMembers;

    @CreationTimestamp
    private Timestamp createdAt;

    private String colour;

    private String priority;

    @ManyToOne
    @JoinColumn(name = "board_column_id")
    private BoardColumn boardColumn;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Assignee> assignee;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
