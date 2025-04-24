package kr.hhplus.be.server.infrastructure.coupon

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.coupon.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface CouponJpaRepository : JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Coupon c where c.id  =:id")
    fun findByIdWithLock(id: Long): Coupon?
}