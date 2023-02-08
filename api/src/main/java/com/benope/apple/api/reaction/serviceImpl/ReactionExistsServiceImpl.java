package com.benope.apple.api.reaction.serviceImpl;

import com.benope.apple.api.reaction.service.ReactionExistsService;
import com.benope.apple.domain.reaction.bean.Reaction;
import com.benope.apple.domain.reaction.repository.ReactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactionExistsServiceImpl implements ReactionExistsService {

    private final ReactionJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isExists(Reaction reaction) {
        Reaction cond = Reaction.builder()
                .mbNo(reaction.getMbNo())
                .reactionTypeCd(reaction.getReactionTypeCd())
                .reactionTargetCd(reaction.getReactionTargetCd())
                .objectNo(reaction.getObjectNo())
                .build();

        Example<Reaction> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
