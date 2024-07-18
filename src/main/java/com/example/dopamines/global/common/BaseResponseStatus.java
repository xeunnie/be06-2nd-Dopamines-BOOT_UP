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
    SUCCESS_OK(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_CREATED(true, 1001, "생성에 성공하였습니다."),
    SUCCESS_ACCEPTED(true, 1002, "수락에 성공하였습니다."), //업데이트 성공
    SUCCESS_NO_CONTENT(true, 1003, "내용이 없습니다."), //삭제 성공
    SUCCESS_RESET_CONTENT(true, 1004, "수정에 성공하였습니다."), //수정 성공
    SUCCESS_PARTIAL_CONTENT(true, 1005, "부분적으로 성공하였습니다."), // 페이징 단위 조회 성공
    SUCCESS_MULTI_STATUS(true, 1006, "다중 상태에 성공하였습니다."), // 여러 리소스 상태 변경 성공

    /**
     * 3000 : USER 에러 - 동현
     */
    USER_NOT_FOUND(false, 3000, "해당하는 회원을 찾을 수 없습니다."),
    USER_INVALID_SINGUP_REQUEST(false, 3001, "회원가입 요청이 정상적이지 않습니다."),
    USER_INVALID_MAIL_INFO(false,3002,"메일 저장 정보가 정상적이지 않습니다."),
    USER_UNABLE_USER_ACCESS(false,3003,"이메일 혹은, 요청값을 확인해주세요."),

    UNAUTHORIZED_ACCESS(false, 3001, "접근 권한이 없습니다."),
    USER_NOT_AUTHENTICATED(false, 3002, "로그인이 필요합니다."),

    //SignUp
    USER_EXISTS_EMAIL(false, 3003, "중복된 이메일입니다."),
    USER_EXISTS_NAME(false, 3004, "중복된 이름입니다."),
    USER_EXISTS_NICKNAME(false, 3005, "중복된 닉네임입니다."),
    USER_EXISTS_PHONE(false, 3006, "중복된 전화번호입니다."),
    USER_EXISTS_ADDRESS(false, 3007, "중복된 계정입니다."),

    SIGNUP_REDIRECT_ERROR(false, 3008, "회원가입 중 오류가 발생하였습니다."),
    SIGNUP_EMAIL_AUTHENTICATION_ERROR(false, 3009, "이메일 인증 중 오류가 발생하였습니다."),

    //Login
    USER_EMPTY_EMAIL(false, 3010, "이메일을 입력해주세요."),
    USER_EMPTY_PASSWORD(false, 3011, "비밀번호를 입력해주세요."),

    USER_WRONG_EMAIL(false, 3012, "이메일 형식을 확인해주세요."),
    USER_WRONG_PASSWORD(false, 3013, "비밀번호가 일치하지 않습니다."),

    USER_INACTIVE_ACCOUNT(false, 3014, "사용할 수 없는 계정입니다."),
    USER_NOT_EXIST(false, 3015, "존재하지 않는 계정입니다."),
    USER_NOT_AUTHENTICATED_ACCOUNT(false, 3016, "인증되지 않은 계정입니다."),
    USER_TOKEN_EXPIRED(false, 3017, "토큰이 만료되었습니다."),

    /**
     * 3500 : ADMIN 에러 - 동현
     */
    ADMIN_NOT_FOUND(false, 3500, "해당하는 회원을 찾을 수 없습니다."),
    ADMIN_NOT_AUTHORIZED(false, 3501, "접근 권한이 없습니다."),
    ADMIN_NOT_AUTHENTICATED(false, 3502, "로그인이 필요합니다."),
    ADMIN_NOT_EXIST(false, 3503, "존재하지 않는 계정입니다."),
    ADMIN_NOT_AUTHENTICATED_ACCOUNT(false, 3504, "인증되지 않은 계정입니다."),
    ADMIN_TOKEN_EXPIRED(false, 3505, "토큰이 만료되었습니다."),

    /**
     * 4000 : COMMUNITY 에러 - 수빈
     */

    //CRUD
    //create
    COMMUNITY_BOARD_SAVE_FAILED(false, 4000, "게시글 저장에 실패하였습니다."),
    //read
    COMMUNITY_BOARD_NOT_FOUND(false, 4001, "해당하는 게시글을 찾을 수 없습니다,"),
    COMMUNITY_BOARD_NOT_AUTHORIZED(false, 4002, "해당 게시글에 대한 권한이 없습니다."),
    //update
    COMMUNITY_BOARD_UPDATE_FAILED(false, 4003, "게시글 수정에 실패하였습니다."),
    //delete
    COMMUNITY_BOARD_DELETE_FAILED(false, 4004, "게시글 삭제에 실패하였습니다."),

    //get
    COMMUNITY_BOARD_EMPTY_TITLE(false, 4005, "제목을 입력해주세요."),
    COMMUNITY_BOARD_EMPTY_CONTENT(false, 4006, "내용을 입력해주세요."),

    /**
     * 4200 : MARKET_BOARD 에러 - 송연
     */

    // CRUD
    // CREATE
    MARKET_SAVE_FAILED(false, 4201, "게시글 저장에 실패하였습니다."),
    // READ
    MARKET_NOT_FOUND(false, 4202, "해당하는 게시글을 찾을 수 없습니다."),
    // UPDATE
    MARKET_UPDATE_FAILED(false, 4203, "게시글 수정에 실패하였습니다."),
    // DELETE
    MARKET_DELETE_FAILED(false, 4204, "게시글 삭제에 실패하였습니다."),

    // GET
    MARKET_EMPTY_TITLE(false, 4205, "제목을 입력해주세요."),
    MARKET_EMPTY_CONTENT(false, 4206, "내용을 입력해주세요."),
    MARKET_EMPTY_PRICE(false, 4207, "가격을 입력해주세요."),
    MARKET_EMPTY_IMAGE(false, 4208, "이미지를 등록해주세요."),

    /**
     * 4300 : MARKET_CHAT 에러 - 송연
     */
    //CRUD
    //create
    CHAT_NOT_SEND(false, 4300, "메시지 전송에 실패하였습니다."),
    //read
    CHAT_LOAD_FAILED(false, 4301, "메시지 불러오기에 실패하였습니다."),
    CHAT_FILE_LOAD_FAILED(false, 4302, "파일 불러오기에 실패하였습니다."),

    //delete 관리자 전용
    CHAT_DELETE_FAILED(false, 4303, "메시지 삭제에 실패하였습니다."),

    //get
    CHAT_EMPTY_CONTENT(false, 4304, "내용을 입력해주세요."),
    CHAT_IMAGE_UPLOAD_FAILED(false, 4305, "이미지 업로드에 실패하였습니다."),

    //chatroom
    CHATROOM_EXPIRED(false, 4306, "채팅방이 만료되었습니다."),
    CHATROOM_LOAD_FAILED(false, 4307, "채팅방 불러오기에 실패하였습니다."),
    CHATROOM_NOT_FOUND(false, 4308, "해당하는 채팅방을 찾을 수 없습니다."),

    /**
     * 4400 : PROJECT 에러 - 시현
     */
    PROJECT_ERROR_CONVENTION(false, 4400, "에러 예시"),
    SEAT_NOT_FOUND(false, 4401, "해당하는 좌석을 찾을 수 없습니다."),

    // CRUD
    // CREATE
    PROJECT_SAVE_FAILED(false, 4401, "프로젝트 저장에 실패하였습니다."),
    // READ
    PROJECT_NOT_FOUND(false, 4402, "해당하는 프로젝트를 찾을 수 없습니다."),
    // UPDATE
    PROJECT_UPDATE_FAILED(false, 4403, "프로젝트 수정에 실패하였습니다."),
    // DELETE
    PROJECT_DELETE_FAILED(false, 4404, "프로젝트 삭제에 실패하였습니다."),

    // GET
    PROJECT_EMPTY_TITLE(false, 4405, "제목을 입력해주세요."),
    PROJECT_EMPTY_CONTENT(false, 4406, "내용을 입력해주세요."),


    /**
     * 4600 : NOTICE 에러 - 승은
     */

    //CRUD
    NOTICE_SAVE_FAILED(false, 4600, "게시글 저장에 실패하였습니다."),
    NOTICE_NOT_FOUND(false, 4601, "해당하는 게시글을 찾을 수 없습니다."),
    NOTICE_UPDATE_FAILED(false, 4602, "게시글 수정에 실패하였습니다."),
    NOTICE_DELETE_FAILED(false, 4603, "게시글 삭제에 실패하였습니다."),

    NOTICE_IS_NOT_EXIST(false, 4604, "해당 게시글이 존재하지 않습니다."),
    NOTICE_IS_NOT_AUTHORIZED(false, 4605, "해당 게시글에 대한 권한이 없습니다."),
    NOTICE_IS_NOT_PUBLIC(false, 4606, "해당 게시글은 비공개 상태입니다."),

    NOTICE_EMPTY_TITLE(false, 4607, "제목을 입력해주세요."),
    NOTICE_EMPTY_CONTENT(false, 4608, "내용을 입력해주세요."),
    NOTICE_EMPTY_CATEGORY(false, 4609, "카테고리를 입력해주세요."),
    NOTICE_EMPTY_DATE(false, 4610, "날짜를 입력해주세요."),
    NOTICE_EMPTY_PUBLIC(false, 4611, "공개 여부를 입력해주세요."),

    /**
     * 5000 : RESERVE 에러 - 시현
     */
    // CRUD
    // CREATE
    RESERVE_SAVE_FAILED(false, 5000, "예약 저장에 실패하였습니다."),

    // DELETE
    RESERVE_DELETE_FAILED(false, 5001, "예약 삭제에 실패하였습니다."),

    // GET
    RESERVE_EMPTY_TIME(false, 5002, "시간을 입력해주세요."),
    RESERVE_EMPTY_SEAT(false, 5003, "좌석을 입력해주세요."),

    RESERVE_BLOCKED_TIME(false, 5004, "예약이 불가능한 시간입니다."),
    RESERVE_BLOCKED_DATE(false, 5005, "예약이 불가능한 날짜입니다."),

    RESERVE_SEAT_TAKEN(false, 5006, "이미 예약된 좌석입니다."),
    RESERVE_SEAT_NOT_POSSIBLE(false, 5007, "예약이 불가능한 좌석입니다."),

    RESERVE_ONLY_ONE_TABLE(false, 5008, "한 테이블만 예약 가능합니다."),
    RESERVE_ONLY_ONCE(false, 5009, "한 번만 예약 가능합니다."),

    RESERVE_NOT_OPEN_YET(false, 5010, "예약이 아직 불가능한 시간입니다."),
    RESERVE_ALREADY_CLOSED(false, 5011, "예약이 마감되었습니다."),


    /**
     * 6000 : ETC
     */
    // 일시적 서버 오류
    INTERNAL_SERVER_ERROR(false, 6000, "서버 오류입니다. 잠시 후 다시 시도해주세요."),

    // 공통
    // 잘못된 요청
    INVALID_REQUEST(false, 6001, "잘못된 요청입니다."),
    // 잘못된 경로
    INVALID_PATH(false, 6002, "잘못된 경로입니다."),
    // 잘못된 입력
    INVALID_INPUT(false, 6003, "잘못된 입력입니다."),
    // 잘못된 파일
    INVALID_FILE(false, 6004, "잘못된 파일입니다."),
    // 잘못된 이미지
    INVALID_IMAGE(false, 6005, "잘못된 이미지입니다."),

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
