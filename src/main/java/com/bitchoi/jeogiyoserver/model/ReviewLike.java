package com.bitchoi.jeogiyoserver.model;

import com.bitchoi.jeogiyoserver.model.id.UserReviewId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ReviewLike {

    @EmbeddedId
    private UserReviewId reviewLikeId;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "FK_review_like_review"))
    private Review review;

    public ReviewLike(long userId, long reviewId) {
        this.reviewLikeId = new UserReviewId(userId, reviewId);
        this.review = new Review(reviewId);
    }
}
