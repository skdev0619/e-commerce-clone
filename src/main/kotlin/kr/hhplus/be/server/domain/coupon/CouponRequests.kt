package kr.hhplus.be.server.domain.coupon

/*
* 쿠폰 요청을 관리하는 대기열 클래스
*/
class CouponRequests(
    val couponId: Long,
    var userIds: List<Long>
) {
}
