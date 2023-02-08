package com.benope.apple.api.scrap.serviceImpl;

import com.benope.apple.api.scrap.service.ScrapExistsService;
import com.benope.apple.api.scrap.service.ScrapService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapSpecification;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.domain.scrap.repository.ScrapJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ScrapExistsService scrapExistsService;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Scrap> getList(Scrap scrap, Pageable pageable) {
        Specification<Scrap> spec = ScrapSpecification.toSpec(scrap)
                .and(ScrapSpecification.toSpecFeedExists());

        return repository.findAll(spec, pageable)
                .map(this::setChildScrapFirstFeed);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Scrap> getOne(Scrap scrap) {
        Specification<Scrap> spec = ScrapSpecification.toSpec(scrap)
                .and(ScrapSpecification.toSpecFeedExists());

        return repository.findOne(spec)
                .map(this::setChildScrapFirstFeed);
    }

    private Scrap setChildScrapFirstFeed(Scrap scrap) {
        if (scrap.isDirectory()) {
            doSetChildScrapFirstFeed(scrap);
        }
        return scrap;
    }

    private void doSetChildScrapFirstFeed(Scrap scrap) {
        Scrap cond = Scrap.builder()
                .parentDirectoryNo(scrap.getScrapNo())
                .scrapTypeCd(ScrapTypeCd.SCRAP)
                .build();

        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "scrapNo"));
        Optional<Scrap> optional = getList(cond, pageable).stream().findFirst();
        scrap.setFeed(optional.map(Scrap::getFeed).orElse(null));
    }

    @Override
    public Scrap registScrap(Scrap scrap) {
        if (!scrap.isScrap()) {
            throw new IllegalArgumentException();
        }

        Scrap entity = repository.save(scrap);

        if (scrapExistsService.isExists(scrap)) {
            throw new BusinessException(RstCode.REGISTRATION_FAILED);
        }

        return em.flushAndRefresh(entity);
    }

    @Override
    public Scrap registDirectory(Scrap directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException();
        }

        return em.flushAndRefresh(repository.save(directory));
    }

    @Override
    public Scrap updateScrap(Scrap scrap) {
        Scrap entity = getScrapById(scrap.getScrapNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(scrap, entity);
        return em.flushAndRefresh(entity);
    }

    private Optional<Scrap> getScrapById(Long scrapNo) {
        Scrap cond = Scrap.builder()
                .scrapNo(scrapNo)
                .scrapTypeCd(ScrapTypeCd.SCRAP)
                .build();

        Example<Scrap> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Override
    public Scrap updateDirectory(Scrap directory) {
        Scrap entity = getDirectoryById(directory.getScrapNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(directory, entity);
        return em.flushAndRefresh(entity);
    }

    private Optional<Scrap> getDirectoryById(Long directoryNo) {
        Scrap cond = Scrap.builder()
                .scrapNo(directoryNo)
                .scrapTypeCd(ScrapTypeCd.DIRECTORY)
                .build();

        Example<Scrap> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Override
    public void deleteScrapById(Long scrapNo) {
        Scrap entity = getScrapById(scrapNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        delete(entity);
    }

    @Override
    public int deleteDirectoryById(Long directoryNo) {
        Scrap entity = getDirectoryById(directoryNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        return delete(entity);
    }

    private int delete(Scrap scrapOrDirectory) {
        repository.delete(scrapOrDirectory);

        if (scrapOrDirectory.isDirectory()) {
            return 1 + deleteChildElements(scrapOrDirectory);
        } else {
            return 1;
        }
    }

    private int deleteChildElements(Scrap directory) {
        return getChildElements(directory)
                .stream()
                .map(this::delete)
                .reduce(0, Integer::sum);
    }

    private List<Scrap> getChildElements(Scrap directory) {
        Scrap cond = Scrap.builder()
                .parentDirectoryNo(directory.getScrapNo())
                .build();

        Example<Scrap> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example);
    }

}
