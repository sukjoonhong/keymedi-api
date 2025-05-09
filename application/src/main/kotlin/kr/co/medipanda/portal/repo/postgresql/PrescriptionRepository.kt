package kr.co.medipanda.portal.repo.postgresql

import kr.co.medipanda.portal.domain.entity.postgresql.Prescription
import kr.co.medipanda.portal.domain.model.PrescriptionStatistics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PrescriptionRepository : JpaRepository<Prescription, Long> {
    @Query(
        value = """
        SELECT
            p.year,
            p.month,
            p.dealer_id AS dealerId,
            d.dealer_name AS dealerName,
            pc.name AS pharmaceuticalCompany,
            h.name AS hospitalName,
            SUM(p.prescription_amount) AS totalPrescriptionAmount,
            SUM(p.approved_amount) AS totalApprovedAmount
        FROM prescription p
            JOIN dealer d ON d.id = p.dealer_id
            JOIN member m ON m.id = d.owner_member_id
            JOIN product pr ON pr.id = p.product_id
            JOIN pharmaceutical_company pc ON pc.id = pr.pharmaceutical_company_id
            JOIN client_hospital h ON h.id = p.hospital_id
        WHERE
            p.status = 'APPROVED'
            AND p.year = :year
            AND p.month = :month
            AND m.id = :ownerId
        GROUP BY
            p.year, p.month, pc.name, h.name, p.dealer_id, d.dealer_name
        ORDER BY
            pc.name, h.name
        """,
        nativeQuery = true
    )
    fun findStatisticsByYearMonthAndOwner(
        @Param("year") year: Int,
        @Param("month") month: Int,
        @Param("ownerId") ownerId: Long
    ): List<PrescriptionStatistics>
}
