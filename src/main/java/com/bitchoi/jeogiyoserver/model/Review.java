package com.bitchoi.jeogiyoserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_review_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(name = "FK_review_restaurant"))
    private Restaurant restaurant;

    private String content;

    private int score;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", foreignKey = @ForeignKey(name = "FK_review_comment"))
    private Comment comment;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<ReviewLike> likes;

    public Review(long reviewId) {
        this.reviewId = reviewId;
    }
}
