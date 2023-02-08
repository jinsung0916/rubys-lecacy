package com.benope.apple.api.member.serviceImpl;

import com.benope.apple.api.member.service.QuitMemberService;
import com.benope.apple.domain.member.bean.MemberAuth;
import com.benope.apple.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuitMemberServiceImpl implements QuitMemberService {

    private final MemberMapper mapper;

    private final Environment env;

    @Override
    public boolean isQuitMember(MemberAuth memberAuth) {
        return !isDevProfile() && mapper.selectQuitMember(memberAuth).isPresent();
    }

    private boolean isDevProfile() {
        return env.acceptsProfiles(Profiles.of("dev"));
    }

}
