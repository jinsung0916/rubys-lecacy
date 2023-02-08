package com.benope.apple.domain.member.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.member.bean.Member;

public class MemberDeleteEvent extends BenopeEvent<Member> {
    public MemberDeleteEvent(Member source) {
        super(source);
    }
}
