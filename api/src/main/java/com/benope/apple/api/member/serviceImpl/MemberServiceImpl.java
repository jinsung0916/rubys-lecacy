package com.benope.apple.api.member.serviceImpl;

import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import com.benope.apple.domain.member.event.MemberDeleteEvent;
import com.benope.apple.domain.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository repository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> getOne(Member member) {
        Example<Member> example = Example.of(member, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> getByMemberAuth(MemberAuth memberAuth) {
        return repository.findByMemberAuth(memberAuth);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isNicknameDuplicated(String nickname) {
        if(Objects.nonNull(nickname)) {
            return getOne(
                    Member.builder()
                            .nickname(nickname)
                            .build()
            )
                    .isPresent();
        } else {
            return false;
        }
    }

    @Override
    public Member regist(Member member) {
        if (isNicknameDuplicated(member.getNickname())) {
            throw new BusinessException(RstCode.DUPLICATED_NICKNAME);
        }

        return repository.save(member);
    }

    @Override
    public Member update(Member member) {
        if (isNicknameDuplicated(member.getNickname())) {
            throw new BusinessException(RstCode.DUPLICATED_NICKNAME);
        }

        Member entity = repository.findById(member.getMbNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(member, entity);
        repository.saveAndFlush(entity);

        return entity;
    }

    @Override
    public Member deleteById(Long mbNo) {
        Member entity = repository.findById(mbNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity.toQuitMember());

        applicationEventPublisher.publishEvent(new MemberDeleteEvent(entity));

        return entity;
    }

}
