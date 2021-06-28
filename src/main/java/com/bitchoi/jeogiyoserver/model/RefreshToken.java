package com.bitchoi.jeogiyoserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    private long refreshTokenId;

    private String refreshToken;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_refresh_token_user"))
    private User user;

    private Date expiredOn;

    @Builder
    public RefreshToken(String refreshToken, User user, Date expiredOn){
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiredOn = expiredOn;
    }
}
