package com.keymedi.domain.entity.postgresql

import com.keymedi.domain.PrescriptionStatus
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * 처방 및 정산 테이블
 * EDI OCR 인식 후 저장되며 FK 로 dealer, hospital, product 걸려 있음
 */
@Entity
@Table(
    name = "prescription",
    indexes = [
        Index(name = "idx__prescription__dealer_id", columnList = "dealer_id"),
        Index(name = "idx__prescription__hospital_id", columnList = "hospital_id"),
        Index(name = "idx__prescription__product_id", columnList = "product_id"),
        Index(name = "idx__prescription__year__month", columnList = "year, month")
    ]
)
data class Prescription(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id", nullable = false)
    val dealer: Dealer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    val clientHospital: ClientHospital,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @Column(nullable = false)
    val year: Int,

    @Column(nullable = false)
    val month: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: PrescriptionStatus = PrescriptionStatus.PENDING,

    @Column(nullable = false)
    val prescriptionAmount: Long,

    @Column(nullable = false)
    val filePath: String,

    @Column(nullable = true)
    val approvedAmount: Long? = null,

    @Column(nullable = true)
    val requestedAt: LocalDateTime?

) : BaseEntity()

