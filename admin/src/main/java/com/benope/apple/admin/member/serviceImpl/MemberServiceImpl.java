package com.benope.apple.admin.member.serviceImpl;

import com.benope.apple.admin.member.service.MemberService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.repository.MemberJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Member> getList(Member member, Pageable pageable) {
        Example<Member> example = Example.of(
                member,
                ExampleMatcher.matchingAll()
                        .withMatcher("nickname", ExampleMatcher.GenericPropertyMatcher::contains)
                        .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
        );

        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Member> getOne(Member member) {
        Example<Member> example = Example.of(member, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Member> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Member update(Member member) {
        Member entity = repository.findById(member.getMbNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        map(member, entity);
        repository.saveAndFlush(entity);

        return em.flushAndRefresh(entity);
    }

    private void map(Member source, Member destination) {
        modelMapper.map(source, destination);

        if (Objects.isNull(source.getExpertCd())) {
            destination.setExpertCd(null);
        }
    }

}
