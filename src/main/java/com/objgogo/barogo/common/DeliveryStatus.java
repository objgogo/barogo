package com.objgogo.barogo.common;

public enum DeliveryStatus {
    RECEIPT, //접수
    START, // 출발지 출발
    TAKE, // 배송품 인계
    DELIVERY, // 목적지 배달 중
    ARRIVE, // 목적지 도착
    END // 배송품 전달 완료
}
