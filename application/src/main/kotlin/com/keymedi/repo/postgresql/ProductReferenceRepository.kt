package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.ProductReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReferenceRepository: JpaRepository<ProductReference, Long> {
}