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
    override fun issue(@PathVariable couponId: Long, @RequestParam userId: Long): ResponseEntity<IssueCouponResponse> {
        val issuedCoupon = firstComeCouponIssueService.issuedCoupon(userId, couponId)
        val response = IssueCouponResponse.from(issuedCoupon)
        return ResponseEntity.created(URI.create("/api/vi/coupons/issue")).body(response);
    }

    @GetMapping
    override fun coupons(@RequestParam userId: Long): ResponseEntity<List<MyCouponResponse>> {
        val myCoupon = couponQueryService.findMyCoupons(userId)
        val response = myCoupon.map { MyCouponResponse.from(it) }
        return ResponseEntity.ok(response)
    }
}
