package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.CouponRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val jpaRepository: CouponJpaRepository
) : CouponRepository {

    override fun save(coupon: Coupon): Coupon {
        return jpaRepository.save(coupon)
    }

    override fun findById(id: Long): Coupon? {
        return jpaRepository.findByIdOrNull(id)
    }

    override fun findByIdWithLock(id: Long): Coupon? {
        return jpaRepository.findByIdWithLock(id)
    }
}
