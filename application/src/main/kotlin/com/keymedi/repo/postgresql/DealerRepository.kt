package com.keymedi.repo.postgresql

import com.keymedi.domain.entity.postgresql.Dealer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DealerRepository: JpaRepository<Dealer, Long> {

}