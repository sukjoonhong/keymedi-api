package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.ProductIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface ProductIngredientRepository: JpaRepository<ProductIngredient, Long> {
}