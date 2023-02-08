package com.benope.apple.domain.reaction.repository;

import com.benope.apple.domain.reaction.bean.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReactionJpaRepository extends JpaRepository<Reaction, Long>, JpaSpecificationExecutor<Reaction> {
}
