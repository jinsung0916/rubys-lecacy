package com.benope.apple.domain.member.mapper;

import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    Optional<Member> selectQuitMember(MemberAuth memberAuth);

}