package kr.co.medipanda.portal.domain.entity.postgresql

import jakarta.persistence.*

/**
 * 제품과 성분 관계 테이블
 */
@Entity
@Table(name = "product_ingredient")
data class ProductIngredient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    val ingredient: Ingredient
)

