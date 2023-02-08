package com.benope.apple.config.webMvc;

import com.benope.apple.utils.PagingMetadata;
import lombok.Getter;

@Getter
public enum RstCode {

    SUCCESS("100", "성공"),
    FAILURE("900", "실패"),
    REGISTRATION_FAILED("901", "등록 실패"),
    UPDATE_FAILED("902", "갱신 실패"),
    DELETE_FAILED("903", "삭제 실패"),
    // 공통
    NOT_FOUND("910", "조회 결과가 없습니다."),
    INVALID_JSON_TYPE("911", "잘못된 json 형식입니다."),
    INVALID_APPROACH("912", "잘못된 접근입니다."),
    UNAUTHORIZED("913", "인증이 필요합니다."),
    FORBIDDEN("914", "권한이 불충분합니다."),
    START_DATE_AFTER_END_DATE("915", "시작일은 종료일을 초과할 수 없습니다."),
    DATE_BETWEEN_EXCEEDED_MAX_VALUE("916", "최대 조회 기간을 초과할 수 없습니다."),
    // 인증
    MEMBER_NOT_EXISTS("920", "회원정보가 존재하지 않습니다."),
    ALREADY_REGISTERED_MEMBER("921", "등록된 사용자가 있습니다."),
    DUPLICATED_NICKNAME("922", "해당 닉네임은 이미 등록되었습니다."),
    MANDATORY_TERM_DISAGREE("923", "필수 약관에 모두 동의해야 합니다."),
    QUIT_MEMBER("924", "탈퇴 후 30일 동안 동일한 SNS계정으로\n재가입이 불가능 합니다."),
    // 전문가
    POLICY_EXPIRED("930", "정책이 만료되었습니다."),
    EXP_ALREADY_REGISTERED("931", "경험치가 이미 적립되었습니다."),
    EXPERT_EXP_MIN_REQUIRED("932", "전문가 신청을 위한 경험치가 부족합니다."),
    EXPERT_ALREADY_REGISTERED("933", "이미 전문가로 등록되었습니다."),
    EXPERT_APPLY_ALREADY_REGISTERED("934", "처리중인 전문가 신청이 존재합니다."),
    // 카테고리
    CHILD_CATEGORY_EXISTS("940", "자식 카테고리가 존재합니다."),
    FOOD_EXISTS("941", "등록된 식품 DB가 있어 삭제할 수 없습니다."),
    // 식품
    UNIT_CD_NOT_MATCHED("950", "단위의 종류를 통일시켜주세요."),
    // 테마
    FEED_ALREADY_REGISTERED("960", "이미 등록된 피드입니다."),
    // 앱 버전
    VERSION_ALREADY_REGISTERED("971", "이미 등록된 버전입니다.");

    private final String code;
    private final String message;

    RstCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse toApiResponse() {
        return ApiResponse.builder()
                .rstCode(this)
                .build();
    }

    public ApiResponse toApiResponse(Object rstContent) {
        return ApiResponse.builder()
                .rstCode(this)
                .rstContent(rstContent)
                .build();
    }

    public ApiResponse toApiResponse(Object rstContent, PagingMetadata pagingMetadata) {
        return ApiResponse.builder()
                .rstCode(this)
                .rstContent(rstContent)
                .pagingMetadata(pagingMetadata)
                .build();
    }
}
