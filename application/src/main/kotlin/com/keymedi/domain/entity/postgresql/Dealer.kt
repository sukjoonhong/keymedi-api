package com.keymedi.domain.entity.postgresql

import jakarta.persistence.*


/**
 * 딜러 각각은 member 와 자식관계에 있으며 딜러: member 는 N:1
 */
@Entity
@Table(
    name = "dealer",
    indexes = [
        Index(name = "idx__dealer__owner_member_id", columnList = "owner_member_id")
    ]
)
data class Dealer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id", nullable = false)
    val owner: Member,

    @Column(nullable = false)
    val dealerName: String,

    @Column(nullable = false)
    val bankName: String,

    @Column(nullable = false)
    val accountNumber: String,

) : BaseEntity()
