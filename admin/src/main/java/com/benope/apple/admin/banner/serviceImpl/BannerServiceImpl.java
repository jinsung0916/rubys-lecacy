package com.benope.apple.admin.banner.serviceImpl;

import com.benope.apple.admin.banner.service.BannerService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.domain.banner.repository.BannerJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Banner> getList(Banner banner, Pageable pageable) {
        Example<Banner> example = Example.of(banner, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Banner> getOne(Banner banner) {
        Example<Banner> example = Example.of(banner, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Banner> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Banner regist(Banner banner) {
        Banner entity = repository.save(banner);
        return em.flushAndRefresh(entity);
    }

    @Override
    public Banner update(Banner banner) {
        Banner entity = repository.findById(banner.getBannerNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        map(banner, entity);

        return em.flushAndRefresh(entity);
    }


    private void map(Banner source, Banner destination) {
        modelMapper.map(source, destination);

        if (Objects.isNull(source.getBannerImgNo())) {
            destination.setBannerImgNo(null);
        }
    }

    @Override
    public void deleteById(Long bannerNo) {
        Banner entity = repository.findById(bannerNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
