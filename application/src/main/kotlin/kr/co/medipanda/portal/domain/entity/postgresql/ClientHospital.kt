package kr.co.medipanda.portal.domain.entity.postgresql

import jakarta.persistence.*

@Entity
@Table(
    name = "client_hospital",
    uniqueConstraints = [
        UniqueConstraint(name = "uq__client_hospital__biz_number", columnNames = ["business_number"])
    ],
)
data class ClientHospital(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "business_number", nullable = false)
    val businessNumber: String,

    @Column(name = "address", nullable = false)
    val address: String,

    @Column(name = "region_sido", nullable = false)
    val regionSido: String,

    @Column(name = "region_sigungu", nullable = false)
    val regionSigungu: String,
)
