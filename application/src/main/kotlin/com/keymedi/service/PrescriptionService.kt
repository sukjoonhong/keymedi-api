package com.keymedi.service


import com.keymedi.domain.model.PrescriptionStatistics
import com.keymedi.repo.postgresql.PrescriptionRepository
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