package kr.hhplus.be.server.interfaces.ranking.event

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingService
import kr.hhplus.be.server.domain.ranking.ProductSale
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RankingOrderEventKafkaListener(
    private val objectMapper: ObjectMapper,
    private val dailyProductSaleRankingService: DailyProductSaleRankingService
) {

    @KafkaListener(
        topics = ["order.completed"],
        groupId = "ranking-product_sales",
    )
    fun consume(message: ConsumerRecord<String, ByteArray>, ack: Acknowledgment) {
        val event = objectMapper.readValue(message.value(), OrderEvent.Completed::class.java)

        val productSales = event.orderInfo.orderItems.map {
            ProductSale(it.productId, it.quantity.toLong())
        }

        dailyProductSaleRankingService.accumulate(LocalDate.now(), productSales)
        ack.acknowledge()
    }
}
