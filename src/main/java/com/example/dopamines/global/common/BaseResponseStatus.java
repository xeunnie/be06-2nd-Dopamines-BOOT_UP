package com.example.dopamines.global.common;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 3000 : USER 에러 - 동현
     */
    USER_NOT_FOUND(false, 3000, "해당하는 회원을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(false, 3001, "접근 권한이 없습니다."),
    USER_NOT_AUTHENTICATED(false, 3001, "로그인이 필요합니다."),


    /**
     * 3500 : ADMIN 에러 - 동현
     */
    ADMIN_TEST(false, 3500, "해당하는 회원을 찾을 수 없습니다."),


    /**
     * 4000 : COMMUNITY 에러 - 수빈
     */
    COMMUNITY_ERROR_CONVENTION(false, 4000, "해당하는 게시글을 찾을 수 없습니다,"),


    /**
     * 4200 : MARKET 에러 - 송연
     */
    MARKET_ERROR_CONVENTION(false, 4200, "해당하는 게시글을 찾을 수 없습니다,"),


    /**
     * 4400 : PROJECT 에러 - 시현
     */
    PROJECT_ERROR_CONVENTION(false, 4400, "에러 예시"),


    /**
     * 4600 : NOTICE 에러 - 승은
     */
    NOTICE_ERROR_CONVENTION(false, 4600, "에러 예시"),

    //CRUD
    NOTICE_SAVE_FAILED(false, 4601, "게시글 저장에 실패하였습니다."),
    NOTICE_NOT_FOUND(false, 4601, "해당하는 게시글을 찾을 수 없습니다."),
    NOTICE_UPDATE_FAILED(false, 4602, "게시글 수정에 실패하였습니다."),
    NOTICE_DELETE_FAILED(false, 4603, "게시글 삭제에 실패하였습니다."),

    NOTICE_IS_NOT_EXIST(false, 4604, "해당 게시글이 존재하지 않습니다."),
    NOTICE_IS_NOT_AUTHORIZED(false, 4605, "해당 게시글에 대한 권한이 없습니다."),
    NOTICE_IS_NOT_PUBLIC(false, 4606, "해당 게시글은 비공개 상태입니다."),

    NOTICE_EMPTY_TITLE(false, 4604, "제목을 입력해주세요."),
    NOTICE_EMPTY_CONTENT(false, 4605, "내용을 입력해주세요."),
    NOTICE_EMPTY_CATEGORY(false, 4606, "카테고리를 입력해주세요."),
    NOTICE_EMPTY_DATE(false, 4607, "날짜를 입력해주세요."),
    NOTICE_EMPTY_PUBLIC(false, 4608, "공개 여부를 입력해주세요."),

    /**
     * 5000 : STUDY 에러 - 시현
     */
    STUDY_ERROR_CONVENTION(false, 5000, "에러 예시"),


    /**
     * 6000 : ETC
     */
    ETC_ERROR_CONVENTION(false, 6000, "에러 예시"),

    //Todo : 아래 내용은 추후 삭제할 것
    POST_USERS_EMPTY_EMAIL(false, 3002, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 3003, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,3004,"중복된 이메일입니다."),

    POST_USERS_EMPTY_NAME(false,3005,"이름을 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false,3006,"비밀번호를 입력해주세요."),
    POST_USERS_INVALID_USER_INFO(false,3007,"이메일 또는 패스워드를 확인해주세요."),
    POST_NOT_FOUND(false, 4000, "해당하는 게시글을 찾을 수 없습니다,");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
