package com.benope.apple.domain.member.repository;

import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    @Query("SELECT m FROM Member m JOIN m.container.memberAuths auth WHERE auth.mbAuthCd = :#{#memberAuth.mbAuthCd} AND auth.identifier = :#{#memberAuth.identifier}")
    Optional<Member> findByMemberAuth(MemberAuth memberAuth);

    // ElementCollection 삭제 방지
    @Modifying
    @Query("UPDATE Member SET quitDate = :#{#member.quitDate}, rowStatCd = 'D' WHERE mbNo = :#{#member.mbNo}")
    void delete(Member member);

}
