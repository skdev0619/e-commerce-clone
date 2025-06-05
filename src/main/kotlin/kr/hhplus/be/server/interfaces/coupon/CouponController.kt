package kr.hhplus.be.server.interfaces.coupon

import kr.hhplus.be.server.application.coupon.CouponQueryService
import kr.hhplus.be.server.application.coupon.FirstComeCouponIssueService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    private val couponQueryService: CouponQueryService,
    private val firstComeCouponIssueService: FirstComeCouponIssueService
) : CouponApiSpecification {

    @PostMapping("/{couponId}/issue")
    override fun issue(@PathVariable couponId: Long, @RequestParam userId: Long): ResponseEntity<TryIssueCouponResponse> {
        val result = firstComeCouponIssueService.issuedCoupon(userId, couponId)
        val response = TryIssueCouponResponse(result.couponId, result.userId)
        return ResponseEntity.ok(response);
    }

    @GetMapping
    override fun coupons(@RequestParam userId: Long): ResponseEntity<List<MyCouponResponse>> {
        val myCoupon = couponQueryService.findMyCoupons(userId)
        val response = myCoupon.map { MyCouponResponse.from(it) }
        return ResponseEntity.ok(response)
    }
}
