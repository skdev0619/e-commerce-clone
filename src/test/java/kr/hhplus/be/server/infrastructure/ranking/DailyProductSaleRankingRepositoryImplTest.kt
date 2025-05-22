package kr.hhplus.be.server.infrastructure.ranking

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.time.LocalDate

@SpringBootTest
class DailyProductSaleRankingRepositoryImplTest {

    @Autowired
    private lateinit var redis: RedisTemplate<String, String>

    private lateinit var repository: DailyProductSaleRankingRepositoryImpl

    private val baseDate = LocalDate.of(2025, 5, 3)

    @BeforeEach
    fun setUp() {
        clearData(baseDate)
        repository = DailyProductSaleRankingRepositoryImpl(redis)
    }

    @DisplayName("해당 날짜의 상품 판매량을 누적한다")
    @Test
    fun add() {
        repository.add(baseDate, productId = 1L, quantity = 9)
        repository.add(baseDate, productId = 2L, quantity = 10)

        //then
        val result = redis.opsForZSet()
            .rangeWithScores(createKey(baseDate), 0, -1)

        assertThat(result).extracting("score", "value")
            .containsExactly(
                Tuple.tuple(9.0, "1"),
                Tuple.tuple(10.0, "2")
            )
    }


    @DisplayName("해당 날짜의 상품 판매 랭킹 정보를 N개 조회한다")
    @Test
    fun getRanking() {
        repository.add(baseDate, productId = 1L, quantity = 10)
        repository.add(baseDate, productId = 2L, quantity = 9)
        repository.add(baseDate, productId = 3L, quantity = 8)

        //when
        val result = repository.getRanking(baseDate, 2)

        assertThat(result?.baseDate).isEqualTo(baseDate)
        assertThat(result?.ranking).extracting("productId", "salesCount")
            .containsExactly(
                Tuple.tuple(1L, 10L),
                Tuple.tuple(2L, 9L)
            )
    }

    private fun createKey(date: LocalDate): String {
        val keyPrefix = DailyProductSaleRankingRepositoryImpl.KEY_PREFIX
        val dateFormat = DailyProductSaleRankingRepositoryImpl.DATE_FORMATTER
        return "$keyPrefix:${date.format(dateFormat)}"
    }

    private fun clearData(baseDate: LocalDate) {
        redis.delete(createKey(baseDate))
    }
}
