package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository : JpaRepository<Coupon, Long>