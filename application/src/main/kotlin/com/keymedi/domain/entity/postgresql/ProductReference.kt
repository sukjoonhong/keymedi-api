package com.keymedi.domain.entity.postgresql

import jakarta.persistence.*

/**
 * 제품, 대조약 관계 테이블
 */
@Entity
@Table(
    name = "product_reference",
    indexes = [
        Index(name = "idx__product_reference__product_id", columnList = "product_id")
    ],
)
data class ProductReference(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_product_id", nullable = false)
    val referenceProduct: Product
)
