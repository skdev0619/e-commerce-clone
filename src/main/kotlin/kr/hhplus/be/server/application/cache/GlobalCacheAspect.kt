package kr.hhplus.be.server.application.cache

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.time.Duration

@Aspect
@Component
class GlobalCacheAspect(
    private val cacheTemplate: CacheTemplate<String, Any>,
) {

    @Around("@annotation(GlobalCacheable)")
    fun cacheable(joinPoint: ProceedingJoinPoint): Any {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(GlobalCacheable::class.java)

        val key = parseSpEL(annotation.key, method, joinPoint.args)
        val cacheName = "${annotation.name}:${key}"

        return cacheTemplate.get(cacheName)
            ?: cacheTemplate.get("${cacheName}-failover")
            ?: run {
                val data = joinPoint.proceed()
                cacheTemplate.put(cacheName, data, Duration.ofMinutes(annotation.expireMinute))
                return data
            }
    }


    @Around("@annotation(GlobalCacheEvict)")
    fun cacheEvict(joinPoint: ProceedingJoinPoint): Any {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(GlobalCacheEvict::class.java)

        val key = parseSpEL(annotation.key, method, joinPoint.args)
        val cacheName = "${annotation.name}:${key}"

        cacheTemplate.remove(cacheName)
        return joinPoint.proceed()
    }

    @Around("@annotation(GlobalCachePut)")
    fun cachePut(joinPoint: ProceedingJoinPoint): Any {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(GlobalCachePut::class.java)

        val key = parseSpEL(annotation.key, method, joinPoint.args)
        val cacheName = "${annotation.name}:${key}"

        val methodReturnValue = joinPoint.proceed()
        cacheTemplate.put(cacheName, methodReturnValue, Duration.ofMinutes(annotation.expireMinute))
        return methodReturnValue
    }

    private fun parseSpEL(
        keyExpression: String,
        method: Method,
        args: Array<Any>
    ): String {
        val parser = SpelExpressionParser()
        val context = StandardEvaluationContext()

        val paramNames = method.parameters.map { it.name }
        paramNames.forEachIndexed { i, name ->
            context.setVariable(name, args[i])
        }
        return parser.parseExpression(keyExpression).getValue(context, String::class.java) ?: ""
    }
}
