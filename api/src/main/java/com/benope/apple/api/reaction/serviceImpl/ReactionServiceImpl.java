package com.benope.apple.api.reaction.serviceImpl;

import com.benope.apple.api.reaction.service.FeedReactionService;
import com.benope.apple.api.reaction.service.ReactionExistsService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.reaction.bean.FeedReaction;
import com.benope.apple.domain.reaction.bean.Reaction;
import com.benope.apple.domain.reaction.bean.ReactionTargetCd;
import com.benope.apple.domain.reaction.bean.ReactionTypeCd;
import com.benope.apple.domain.reaction.repository.ReactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactionServiceImpl implements FeedReactionService {

    private final ReactionJpaRepository repository;

    private final ReactionExistsService reactionExistsService;

    @Override
    public boolean isFeedLike(Long mbNo, Long feedNo) {
        return getByMbNoAndFeedNo(mbNo, feedNo, ReactionTypeCd.LIKE).isPresent();
    }

    private Optional<Reaction> getByMbNoAndFeedNo(Long mbNo, Long feedNo, ReactionTypeCd reactionTypeCd) {
        Reaction cond = Reaction.builder()
                .mbNo(mbNo)
                .objectNo(feedNo)
                .reactionTypeCd(reactionTypeCd)
                .reactionTargetCd(ReactionTargetCd.FEED)
                .build();
        Example<Reaction> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.findOne(example);
    }

    @Override
    public boolean toggleFeedLike(Long mbNo, Long feedNo) {
        Optional<Reaction> optional = getByMbNoAndFeedNo(mbNo, feedNo, ReactionTypeCd.LIKE);
        if (optional.isEmpty()) {
            Reaction reaction = FeedReaction.getLikeInstance(mbNo, feedNo);
            Reaction entity = repository.save(reaction);

            if(reactionExistsService.isExists(entity)) {
                throw new BusinessException(RstCode.REGISTRATION_FAILED);
            }

            return true;
        } else {
            repository.delete(optional.get());
            return false;
        }
    }

}
