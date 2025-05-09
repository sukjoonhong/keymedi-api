package kr.co.medipanda.portal.repo.postgresql

import kr.co.medipanda.portal.domain.entity.postgresql.PharmaceuticalCompany
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PharmaceuticalCompanyRepository: JpaRepository<PharmaceuticalCompany, Long> {
}