package kr.hhplus.be.server.application.cache

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GlobalCachePut(
    //캐시 이름
    val name: String,
    //캐시 키
    val key : String,
    //캐시 만료 시간 (분 단위)
    val expireMinute: Long
)