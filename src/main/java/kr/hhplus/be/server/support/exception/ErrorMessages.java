package kr.hhplus.be.server.support.exception;

public class ErrorMessages {
    // 공통 에러 메시지
    public static final String INVALID_REQUEST = "잘못된 요청입니다.";
    public static final String INVALID_ID = "유효하지 않은 ID 입니다.";

    // 잔고 관련 에러 메시지
    public static final String INSUFFICIENT_BALANCE = "잔고가 부족합니다.";
    public static final String NEGATIVE_BALANCE_NOT_ALLOWED = "잔액은 음수일 수 없습니다.";

    // 결제 관련 에러 메시지
    public static final String PAYMENT_FAILED = "결제에 실패했습니다.";

    // 예약 관련 에러 메시지
    public static final String RESERVATION_NOT_FOUND = "예약 정보를 찾을 수 없습니다.";
    public static final String RESERVATION_EXPIRED = "예약 시간이 만료되었습니다.";
    public static final String UNAUTHORIZED_RESERVATION = "예약자와 다른 유저입니다.";
    public static final String ERROR_PAYMENT_NOT_ALLOWED = "결제할 수 있는 상태가 아닙니다.";

    // 토큰 관련 에러 메시지
    public static final String TOKEN_EXPIRED = "토큰이 만료되었습니다.";
    public static final String UNAUTHORIZED_ACCESS = "토큰이 유효하지 않습니다.";
    public static final String TOKEN_NOT_FOUND = "토큰이 존재하지 않습니다.";

    // 유저 관련 에러 메시지
    public static final String MEMBER_NOT_FOUND = "사용자를 찾을 수 없습니다.";
    public static final String DUPLICATED_MEMBER = "중복된 이름은 사용할 수 없습니다.";

    // 콘서트 관련 에러 메시지
    public static final String CONCERT_NOT_FOUND = "콘서트를 찾을 수 없습니다.";


    // 좌석 관련 에러 메시지
    public static final String SEAT_INVALID = "좌석이 유효하지 않습니다.";
    public static final String SEAT_NOT_FOUND = "좌석 조회에 실패했습니다.";
}
