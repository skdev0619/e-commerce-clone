package kr.hhplus.be.server.application.cache

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GlobalCacheEvict(
    //캐시 이름
    val name: String,
    //캐시 키
    val key : String
)