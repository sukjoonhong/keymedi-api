package com.keymedi.domain

enum class PrescriptionStatus {
    PENDING,        // 접수대기
    SUBMITTED,      // 접수완료
    APPROVED,       // 승인완료
    SETTLE_REQUEST, // 정산요청
    SETTLED         // 정산완료
}