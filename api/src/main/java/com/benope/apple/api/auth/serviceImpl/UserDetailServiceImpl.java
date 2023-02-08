package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.UserDetailsAdapter;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.config.auth.CustomUserDetailsService;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements CustomUserDetailsService {

    private final MemberService memberService;

    private final UserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUser(Long mbNo) {
        Member member = retrieveMember(mbNo);
        return toUserDetails(member);
    }

    private Member retrieveMember(Long mbNo) {
        return memberService.getOne(
                        Member.builder()
                                .mbNo(mbNo)
                                .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("mbNo: " + mbNo));
    }

    @Override
    public UserDetails loadUser(MemberAuth.MbAuthCd mbAuthCd, String username) {
        Member member = retrieveMember(mbAuthCd, username);
        return toUserDetails(member);
    }

    private Member retrieveMember(MemberAuth.MbAuthCd mbAuthCd, String identifier) {
        return memberService.getByMemberAuth(
                        MemberAuth.builder()
                                .mbAuthCd(mbAuthCd)
                                .identifier(identifier)
                                .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("mbAuthCd: " + mbAuthCd + " & identifier: " + identifier));
    }

    private UserDetails toUserDetails(Member member) {
        UserDetails userDetails = new UserDetailsAdapter(member);
        checker.check(userDetails);
        return userDetails;
    }

}
