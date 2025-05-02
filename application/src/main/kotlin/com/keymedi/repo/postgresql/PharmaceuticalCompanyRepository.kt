package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.PharmaceuticalCompany
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PharmaceuticalCompanyRepository: JpaRepository<PharmaceuticalCompany, Long> {
}