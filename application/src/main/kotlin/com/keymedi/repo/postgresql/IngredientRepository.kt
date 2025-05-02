package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository: JpaRepository<Ingredient, Long> {
}