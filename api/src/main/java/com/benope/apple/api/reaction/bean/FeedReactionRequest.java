package com.benope.apple.api.reaction.bean;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedReactionRequest {

    @NotNull
    private Long feedNo;

}
