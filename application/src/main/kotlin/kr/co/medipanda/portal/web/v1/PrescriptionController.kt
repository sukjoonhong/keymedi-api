package kr.co.medipanda.portal.web.v1

import kr.co.medipanda.portal.domain.model.PrescriptionStatistics
import kr.co.medipanda.portal.service.PrescriptionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/prescriptions")
@Tag(name = "처방 및 정산 API")
class PrescriptionController(
    private val prescriptionService: PrescriptionService
) {
    @GetMapping("/{memberId}")
    @Operation(summary = "년, 월별 정산 데이터 조회")
    fun get(
        @PathVariable memberId: Long,
        @RequestParam year: Int,
        @RequestParam month: Int,
    ): List<PrescriptionStatistics> {
        return prescriptionService.getPrescriptionByYearMonthAndMember(
            year = year,
            month = month,
            memberId = memberId
        )
    }
}