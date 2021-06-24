package com.bitchoi.jeogiyoserver.model.id;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class UserReviewId implements Serializable {

    private long userId;

    private long reviewId;

    public UserReviewId(long userId, long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
