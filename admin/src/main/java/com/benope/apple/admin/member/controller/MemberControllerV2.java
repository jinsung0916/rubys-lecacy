package com.benope.apple.admin.member.controller;

import com.benope.apple.admin.member.bean.MemberRequestV2;
import com.benope.apple.admin.member.bean.MemberResponseV2;
import com.benope.apple.admin.member.service.LoginHistoryService;
import com.benope.apple.admin.member.service.MemberService;
import com.benope.apple.admin.member.service.MemberSummaryService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/member", headers = V2_HEADER)
@RequiredArgsConstructor
public class MemberControllerV2 extends AbstractRestController<MemberRequestV2, MemberResponseV2> {

    private final MemberService memberService;
    private final MemberSummaryService memberSummaryService;
    private final LoginHistoryService loginHistoryService;

    @Override
    protected Page<MemberResponseV2> findAll(MemberRequestV2 input, Pageable pageable) {
        return memberService.getList(input.toEntity(null), pageable)
                .map(MemberResponseV2::fromEntity);
    }

    @Override
    protected MemberResponseV2 findById(Long id) {
        Member member = memberService.getOne(
                        Member.builder()
                                .mbNo(id)
                                .build()
                )
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));


        return MemberResponseV2.fromEntityToDetail(member)
                .setTotalSummary(getTotalSummaries(id))
                .setMonthSummary(getMonthSummaries(id))
                .setLoginHistory(getLastLoginHistory(id));
    }

    private MemberSummaryComposite getTotalSummaries(Long mbNo) {
        return memberSummaryService.getMemberSummaries(mbNo, null, null);
    }

    private MemberSummaryComposite getMonthSummaries(Long mbNo) {
        YearMonth yearMonth = YearMonth.from(DateTimeUtil.getCurrentDate());
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return memberSummaryService.getMemberSummaries(mbNo, start, end);
    }

    private LoginHistory getLastLoginHistory(Long mbNo) {
        LoginHistory cond = LoginHistory.builder()
                .mbNo(mbNo)
                .build();
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("lastAccessAt")));

        return loginHistoryService.getList(cond, pageable).stream().findFirst().orElse(null);
    }

    @Override
    protected List<MemberResponseV2> findByIds(List<Long> ids) {
        return memberService.getByIds(ids)
                .stream()
                .map(MemberResponseV2::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected MemberResponseV2 create(MemberRequestV2 input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected MemberResponseV2 update(Long id, MemberRequestV2 input) {
        Member entity = memberService.update(input.toEntity(id));
        return MemberResponseV2.fromEntity(entity);
    }

    @Override
    protected void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

}
