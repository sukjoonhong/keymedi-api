package kr.co.medipanda.portal.repo.postgresql

import kr.co.medipanda.portal.domain.entity.postgresql.ProductIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface ProductIngredientRepository: JpaRepository<ProductIngredient, Long> {
}