package kr.co.medipanda.portal.domain.entity.postgresql

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 상품별 성분 저장을 위한 테이블
 * string 비교 연산으로는 동일 성분 제품 검색이 정확하지 않을 것 같아서 생성함
 */
@Entity
@Table(name = "ingredient")
data class Ingredient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String
)
