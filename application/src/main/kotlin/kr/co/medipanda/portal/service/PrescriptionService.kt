package kr.co.medipanda.portal.service


import kr.co.medipanda.portal.domain.model.PrescriptionStatistics
import kr.co.medipanda.portal.repo.postgresql.PrescriptionRepository
import org.springframework.stereotype.Service

@Service
class PrescriptionService(
    private val prescriptionRepository: PrescriptionRepository
) {
    fun getPrescriptionByYearMonthAndMember(
        year: Int,
        month: Int,
        memberId: Long,
    ): List<PrescriptionStatistics> {
       return prescriptionRepository.findStatisticsByYearMonthAndOwner(year, month, memberId)
    }
}