package kr.co.medipanda.portal.domain.entity.postgresql

import jakarta.persistence.*

@Entity
@Table(name = "terms")
data class Terms(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val version: String,
    val title: String,

    @Column(columnDefinition = "TEXT")
    val content: String,
): BaseEntity()
