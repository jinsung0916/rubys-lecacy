package com.benope.apple.api.follow.serviceImpl;

import com.benope.apple.api.follow.service.FollowHelperService;
import com.benope.apple.api.follow.service.FollowService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.follow.bean.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowHelperServiceImpl implements FollowHelperService {

    private final FollowService followService;

    @Override
    public boolean checkFollow(Long mbNo, Long followMbNo) {
        return retrieveFollow(mbNo, followMbNo).isPresent();
    }

    @Override
    public Follow unFollow(Long mbNo, Long followMbNo) {
        Optional<Follow> optional = retrieveFollow(mbNo, followMbNo);

        if (optional.isPresent()) {
            Follow follow = optional.get();
            followService.deleteById(follow.getFollowNo());
            return follow;
        } else {
            throw new BusinessException(RstCode.NOT_FOUND);
        }
    }

    private Optional<Follow> retrieveFollow(Long mbNo, Long followMbNo) {
        Follow cond = Follow.builder()
                .mbNo(mbNo)
                .followMbNo(followMbNo)
                .build();

        return followService.getOne(cond);
    }

}
