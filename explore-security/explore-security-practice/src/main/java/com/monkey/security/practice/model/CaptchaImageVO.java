package com.monkey.security.practice.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CaptchaImageVO {

    //验证码文字
    private String code;

    //验证码失效时间
    private LocalDateTime expireTime;

    public CaptchaImageVO(String code, int expireAfterSeconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }
}
