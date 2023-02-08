package com.benope.apple.admin.member.serviceImpl;

import com.benope.apple.admin.member.service.LoginHistoryService;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.loginHistory.repository.LoginHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryJpaRepository repository;

    @Override
    public Page<LoginHistory> getList(LoginHistory loginHistory, Pageable pageable) {
        Example<LoginHistory> example = Example.of(loginHistory, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

}
