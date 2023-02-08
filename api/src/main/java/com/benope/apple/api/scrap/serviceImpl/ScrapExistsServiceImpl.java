package com.benope.apple.api.scrap.serviceImpl;

import com.benope.apple.api.scrap.service.ScrapExistsService;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.repository.ScrapJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapExistsServiceImpl implements ScrapExistsService {

    private final ScrapJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isExists(Scrap scrap) {
        Scrap cond = Scrap.builder()
                .mbNo(scrap.getMbNo())
                .feedNo(scrap.getFeedNo())
                .build();

        Example<Scrap> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
