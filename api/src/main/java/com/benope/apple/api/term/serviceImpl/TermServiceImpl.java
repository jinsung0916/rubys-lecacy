package com.benope.apple.api.term.serviceImpl;

import com.benope.apple.api.term.service.TermService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.domain.term.bean.TermDetail;
import com.benope.apple.domain.term.bean.TermDetailId;
import com.benope.apple.domain.term.repository.TermDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermDetailJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Term> getList(Term term) {
        List<Term.TermCd> list
                = Objects.nonNull(term.getTermCd()) ? List.of(term.getTermCd()) : List.of(Term.TermCd.values());

        return list.stream()
                .map(this::buildEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Term> getByTermCd(Term.TermCd termCd) {
        return Optional.ofNullable(buildEntity(termCd));
    }

    private Term buildEntity(Term.TermCd termCd) {
        TermDetail cond = TermDetail.builder()
                .termDetailId(
                        TermDetailId.builder()
                                .termCd(termCd)
                                .build()
                )
                .build();
        Example<TermDetail> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("termDetailId.termVersion"))); // 최신 약관만 조회한다.

        List<TermDetail> results = repository.findAll(example, pageable)
                .stream()
                .filter(TermDetail::isValid)
                .collect(Collectors.toUnmodifiableList());

        return Term.builder()
                .termCd(termCd)
                .termDetails(results)
                .build();
    }

    @Override
    public void requireMandatoryTerms(List<MemberTermAgree> memberTermAgrees) {
        List<Term.TermCd> mandatoryTerms = getMandatoryTerms();

        List<Term.TermCd> memberAgreeTerms = memberTermAgrees.stream()
                .filter(MemberTermAgree::isAgree)
                .map(MemberTermAgree::getTermCd)
                .collect(Collectors.toUnmodifiableList());

        for (Term.TermCd termCd : mandatoryTerms) {
            if (!memberAgreeTerms.contains(termCd)) {
                throw new BusinessException(RstCode.MANDATORY_TERM_DISAGREE);
            }
        }
    }

    private List<Term.TermCd> getMandatoryTerms() {
        return Arrays.stream(Term.TermCd.values())
                .filter(Term.TermCd::isMandatory)
                .collect(Collectors.toUnmodifiableList());
    }

}
