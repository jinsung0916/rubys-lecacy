package com.benope.apple.domain.member.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.member.bean.Member;

public class MemberUpdateEvent extends BenopeEvent<Member> {
    public MemberUpdateEvent(Member source) {
        super(source);
    }
}
