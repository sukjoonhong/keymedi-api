package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.ClientHospital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientHospitalRepository: JpaRepository<ClientHospital, Long> {
}