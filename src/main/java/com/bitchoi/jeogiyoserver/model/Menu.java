package com.bitchoi.jeogiyoserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuId;

    private String foodName;

    private int price;

    private String option1;

    private String option2;

    private String option3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(name = "FK_menu_restaurant"))
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY)
    private Order order;
}
