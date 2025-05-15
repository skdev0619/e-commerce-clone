package kr.hhplus.be.server.infrastructure.ranking

import kr.hhplus.be.server.domain.ranking.DailyProductSaleRanking
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingRepository
import kr.hhplus.be.server.domain.ranking.ProductSale
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Repository
class DailyProductSaleRankingRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : DailyProductSaleRankingRepository {

    companion object {
        const val KEY_PREFIX = "product_sales"
        val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
    }

    override fun add(baseDate: LocalDate, productId: Long, quantity: Long) {
        redisTemplate.opsForZSet()
            .incrementScore(createKey(baseDate), productId.toString(), quantity.toDouble())
    }

    override fun getRanking(baseDate: LocalDate, count: Long): DailyProductSaleRanking? {
        return redisTemplate.opsForZSet()
            .reverseRangeWithScores(createKey(baseDate), 0, count - 1)
            ?.mapNotNull { item ->
                val productId = item.value?.toLongOrNull()
                productId?.let { id ->
                    val salesCount = item.score?.toLong() ?: 0
                    ProductSale(id, salesCount)
                }
            }
            ?.let { productSales -> DailyProductSaleRanking(baseDate, productSales) }
    }

    override fun getRanking(baseDate: LocalDate): DailyProductSaleRanking? {
        return getRanking(baseDate, 0)
    }

    private fun createKey(date: LocalDate): String {
        return "$KEY_PREFIX:${date.format(DATE_FORMATTER)}"
    }
}