package com.benope.apple.domain.member.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.member.bean.Member;

public class MemberRegistEvent extends BenopeEvent<Member> {
    public MemberRegistEvent(Member source) {
        super(source);
    }
}
