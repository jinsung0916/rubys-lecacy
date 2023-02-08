package com.benope.apple.api.follow.controller;

import com.benope.apple.api.follow.bean.FollowRequest;
import com.benope.apple.api.follow.bean.FollowSearchRequest;
import com.benope.apple.api.follow.bean.FollowerResponse;
import com.benope.apple.api.follow.bean.FollowingResponse;
import com.benope.apple.api.follow.service.FollowHelperService;
import com.benope.apple.api.follow.service.FollowService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.utils.PagingMetadata;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowHelperService followHelperService;

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.member.follower")
    public ApiResponse getFollower(
            @RequestBody @Valid FollowSearchRequest input
    ) {
        Page<Follow> page = followService.getList(input.toFollowerSearch(), input.toPageable());

        List<FollowerResponse> result = page.stream()
                .map(FollowerResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.member.following")
    public ApiResponse getFollowing(
            @RequestBody @Valid FollowSearchRequest input
    ) {
        Page<Follow> page = followService.getList(input.toFollowingSearch(), input.toPageable());

        List<FollowingResponse> result = page.stream()
                .map(FollowingResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }


    @PostMapping("/reg.member.follow")
    public ApiResponse registFollow(
            @RequestBody @Valid FollowRequest request
    ) {
        Follow follow = followService.regist(request.toEntity());
        return RstCode.SUCCESS.toApiResponse(FollowingResponse.fromEntity(follow));
    }

    @PostMapping("/del.member.follow")
    public ApiResponse deleteFollow(
            @RequestBody @Valid FollowRequest request
    ) {
        Follow follow = followHelperService.unFollow(SessionUtil.getSessionMbNo(), request.getFollowMbNo());
        return RstCode.SUCCESS.toApiResponse(FollowingResponse.fromEntity(follow));
    }

}
