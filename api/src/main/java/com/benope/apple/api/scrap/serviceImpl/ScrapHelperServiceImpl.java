package com.benope.apple.api.scrap.serviceImpl;

import com.benope.apple.api.scrap.service.ScrapHelperService;
import com.benope.apple.api.scrap.service.ScrapService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapHelperServiceImpl implements ScrapHelperService {

    private final ScrapService scrapService;

    @Override
    public boolean checkScrap(Long mbNo, Long feedNo) {
        return retrieveScrap(mbNo, feedNo).isPresent();
    }

    @Override
    public void unScrap(Long mbNo, Long feedNo) {
        Scrap entity = retrieveScrap(mbNo, feedNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        scrapService.deleteScrapById(entity.getScrapNo());
    }

    private Optional<Scrap> retrieveScrap(Long mbNo, Long feedNo) {
        Scrap cond = Scrap.builder()
                .scrapTypeCd(ScrapTypeCd.SCRAP)
                .mbNo(mbNo)
                .feedNo(feedNo)
                .build();

        return scrapService.getOne(cond);
    }

    @Override
    public void updateMultipleScrap(List<Scrap> scraps) {
        scraps.forEach(scrapService::updateScrap);
    }
}
