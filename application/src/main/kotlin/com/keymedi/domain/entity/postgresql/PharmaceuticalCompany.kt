package com.keymedi.domain.entity.postgresql

import jakarta.persistence.*

/**
 * 제약사
 */
@Entity
@Table(name = "pharmaceutical_company")
data class PharmaceuticalCompany(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String
)

