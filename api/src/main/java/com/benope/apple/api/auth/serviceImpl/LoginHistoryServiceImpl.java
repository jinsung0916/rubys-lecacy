package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.loginHistory.repository.LoginHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryJpaRepository repository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<LoginHistory> getList(LoginHistory loginHistory, Pageable pageable) {
        Example<LoginHistory> example = Example.of(loginHistory, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<LoginHistory> getOne(LoginHistory loginHistory) {
        Example<LoginHistory> example = Example.of(loginHistory, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Override
    public LoginHistory regist(LoginHistory loginHistory) {
        return repository.save(loginHistory);
    }

    @Override
    public LoginHistory update(LoginHistory loginHistory) {
        LoginHistory entity = repository.findById(loginHistory.getLoginHistoryNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(loginHistory, entity);
        return entity;
    }

    @Override
    public void deleteByRefreshToken(String refreshToken) {
        LoginHistory cond = LoginHistory.builder()
                .refreshToken(refreshToken)
                .build();

        getOne(cond)
                .ifPresent(repository::delete);
    }

    @Override
    public void deleteByMbNo(Long mbNo) {
        repository.deleteByMbNo(mbNo);
    }

}
