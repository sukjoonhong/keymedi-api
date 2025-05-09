package kr.co.medipanda.portal.domain.entity.postgresql

import jakarta.persistence.*

@Entity
@Table(
    name = "product",
    uniqueConstraints = [
        UniqueConstraint(name = "uq__product__product_code", columnNames = ["product_code"])
    ]
)
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    //TODO: enum?
    @Column(nullable = false)
    val status: String,

    @Column(name = "product_code", nullable = false)
    val productCode: String,

    @Column(nullable = false)
    val insurancePrice: Int,

    @Column(nullable = false)
    val baseFeeRate: Double,

    @Column(nullable = false)
    val isStepFeeExcluded: Boolean = false,

    val note: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmaceutical_company_id", nullable = false)
    val pharmaceuticalCompany: PharmaceuticalCompany,
)
