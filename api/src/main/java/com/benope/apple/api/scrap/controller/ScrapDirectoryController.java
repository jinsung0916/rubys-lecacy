package com.benope.apple.api.scrap.controller;

import com.benope.apple.api.scrap.bean.DirectoryRequest;
import com.benope.apple.api.scrap.bean.DirectoryResponse;
import com.benope.apple.api.scrap.service.ScrapService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.scrap.bean.Scrap;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ScrapDirectoryController {

    private final ScrapService scrapService;

    @PostMapping("/reg.scrap.directory")
    public ApiResponse registDirectory(
            @RequestBody @Validated({Default.class, Create.class}) DirectoryRequest input
    ) {
        Scrap directory = scrapService.registDirectory(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(DirectoryResponse.fromEntity(directory));
    }

    @PostMapping("/mod.scrap.directory")
    public ApiResponse modifyDirectory(
            @RequestBody @Validated({Default.class, Update.class}) DirectoryRequest input
    ) {
        Scrap directory = scrapService.updateDirectory(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(DirectoryResponse.fromEntity(directory));
    }

    @PostMapping("/del.scrap.directory")
    public ApiResponse deleteDirectory(
            @RequestBody @Validated({Default.class, Delete.class}) DirectoryRequest input
    ) {
        int result = scrapService.deleteDirectoryById(input.getDirectoryNo());

        if (result > 0) {
            return RstCode.SUCCESS.toApiResponse(Map.of("count", result));
        } else {
            throw new BusinessException(RstCode.FAILURE);
        }
    }

}
