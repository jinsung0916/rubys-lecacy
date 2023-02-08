package com.benope.apple.domain.image.repository;

import com.benope.apple.domain.image.bean.UploadImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UploadImgJpaRepository extends JpaRepository<UploadImg, Long>, JpaSpecificationExecutor<UploadImg> {
}
