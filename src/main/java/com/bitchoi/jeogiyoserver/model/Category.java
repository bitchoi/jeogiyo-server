package com.bitchoi.jeogiyoserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    private String categoryName;

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Order order;
}
