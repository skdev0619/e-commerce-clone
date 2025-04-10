package kr.hhplus.be.server.domain.coupon

/*
* 발급된 쿠폰의 할인 정보 조회(할인 타입, 할인 값 등..)
* */
interface IssuedCouponQueryRepository {

    /*
    select
        ci.id, c.discount_type, c.discount_value
    from coupon c
    join coupon_issue ci
    on c.id = ci.coupon_id
    where ci.id = ?
    and ci.status = 'ACTIVE'
    */
    fun findCouponDetailsByIssueIdAndStatus(issuedId: Long, status: CouponStatus): CouponIssueDetail?
}