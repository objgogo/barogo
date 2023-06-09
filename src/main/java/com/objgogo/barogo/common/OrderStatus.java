package com.objgogo.barogo.common;

public enum OrderStatus {
    WAIT, // 라이더 주문 접수 전
    ACCEPT, // 라이더 주문 접수
    COMPLETE, // 배달 완료
    FAIL, // 배달 실패
    CANCEL, // 배달 취소
    MODIFY // 주문 수정
}
