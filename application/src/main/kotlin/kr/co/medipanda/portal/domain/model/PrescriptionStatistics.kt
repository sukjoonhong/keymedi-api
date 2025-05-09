package kr.co.medipanda.portal.domain.model

interface PrescriptionStatistics {
    fun getYear(): Int
    fun getMonth(): Int
    fun getDealerId(): Long
    fun getDealerName(): String
    fun getPharmaceuticalCompany(): String
    fun getHospitalName(): String
    fun getTotalPrescriptionAmount(): Long
    fun getTotalApprovedAmount(): Long
}