package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
}