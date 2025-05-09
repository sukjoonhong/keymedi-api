package kr.co.medipanda.portal.repo.postgresql

import kr.co.medipanda.portal.domain.entity.postgresql.Dealer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DealerRepository: JpaRepository<Dealer, Long> {

}