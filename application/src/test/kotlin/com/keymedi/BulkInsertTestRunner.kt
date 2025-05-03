package com.keymedi


import com.keymedi.domain.MemberType
import com.keymedi.domain.PrescriptionStatus
import com.keymedi.domain.entity.postgresql.*
import com.keymedi.domain.entity.postgresql.Member.MarketingAgreement
import com.keymedi.repo.postgresql.*
import com.keymedi.security.RsaUtil
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import kotlin.random.Random

@SpringBootTest
@ActiveProfiles("local", "local-db")
class BulkInsertWithRepositoryTest @Autowired constructor(
    private val pharmaceuticalCompanyRepository: PharmaceuticalCompanyRepository,
    private val ingredientRepository: IngredientRepository,
    private val memberRepository: MemberRepository,
    private val dealerRepository: DealerRepository,
    private val clientHospitalRepository: ClientHospitalRepository,
    private val productRepository: ProductRepository,
    private val productIngredientRepository: ProductIngredientRepository,
    private val productReferenceRepository: ProductReferenceRepository,
    private val prescriptionRepository: PrescriptionRepository,
    private val rsaUtil: RsaUtil,
) : StringSpec({

    "insert dummy data using repositories" {
        val companies = (1..10).map {
            pharmaceuticalCompanyRepository.save(PharmaceuticalCompany(name = "제약사$it"))
        }

        val ingredients = (1..10).map {
            ingredientRepository.save(Ingredient(name = "성분$it"))
        }

        val members = (1..10).map {
            memberRepository.save(
                Member(
                    authId = "user$it",
                    name = "사용자$it",
                    nickname = "닉네임$it",
                    password = BCryptPasswordEncoder().encode("pass$it"),
                    phoneNumber = "0100000$it",
                    email = "user$it@test.com",
                    marketingAgreement = MarketingAgreement(push = true, sms = true, email = false),
                    memberType = MemberType.INDIVIDUAL
                )
            )
        }

        val dealers = members.flatMapIndexed { index, member ->
            (1..5).map {
                dealerRepository.save(
                    Dealer(
                        owner = member.copy(id = 1),
                        dealerName = "딜러${index}_$it",
                        bankName = "은행$it",
                        accountNumber = "12345${index}000$it",
                    )
                )
            }
        }

        val hospitals = (1..50).map {
            clientHospitalRepository.save(
                ClientHospital(
                    name = "병원$it",
                    businessNumber = "110-00-$it",
                    address = "서울 어딘가 $it",
                    regionSido = "서울특별시",
                    regionSigungu = "강남구"
                )
            )
        }

        val products = (1..100).map {
            productRepository.save(
                Product(
                    name = "제품$it",
                    status = if (it % 2 == 0) "자사" else "타사",
                    productCode = "PROD$it",
                    insurancePrice = Random.nextInt(1000, 5000),
                    baseFeeRate = Random.nextDouble(0.1, 0.5),
                    isStepFeeExcluded = it % 2 == 0,
                    note = "비고$it",
                    pharmaceuticalCompany = companies.random()
                )
            )
        }

        products.forEach { product ->
            (1..3).forEach {
                productIngredientRepository.save(
                    ProductIngredient(
                        product = product,
                        ingredient = ingredients.random()
                    )
                )
            }
        }

        products.shuffled().chunked(2).forEach { pair ->
            if (pair.size == 2) {
                productReferenceRepository.save(
                    ProductReference(
                        product = pair[0],
                        referenceProduct = pair[1]
                    )
                )
            }
        }

        PrescriptionStatus.values()
            .filter {
                it in listOf(
                    PrescriptionStatus.PENDING,
                    PrescriptionStatus.SUBMITTED,
                    PrescriptionStatus.APPROVED
                )
            }
            .forEach { status ->
                repeat(100) { i ->
                    val product = products.random()
                    prescriptionRepository.save(
                        Prescription(
                            dealer = dealers.random(),
                            clientHospital = hospitals.random(),
                            product = product,
                            year = 2025,
                            month = Random.nextInt(1, 13),
                            status = status,
                            filePath = "/files/${status.name.lowercase()}_$i.jpg",
                            prescriptionAmount = Random.nextLong(500_000, 2_000_000),
                            approvedAmount = Random.nextLong(500_000, 2_000_000),
                            requestedAt = null
                        )
                    )
                }
            }

        memberRepository.count() shouldBe 10
        productRepository.count() shouldBe 100
        prescriptionRepository.count() shouldBe 300
    }
})
