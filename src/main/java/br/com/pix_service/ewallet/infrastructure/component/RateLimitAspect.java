package br.com.pix_service.ewallet.infrastructure.component;

import br.com.pix_service.ewallet.infrastructure.annotation.RateLimit;
import br.com.pix_service.ewallet.infrastructure.handler.exceptions.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        String key = generateKey(pjp);
        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket(rateLimit));

        if (bucket.tryConsume(1)) {
            return pjp.proceed();
        }

        throw new RateLimitExceededException("Too Many Requests — wait before retrying");
    }

    private Bucket createBucket(RateLimit rateLimit) {
        Refill refill = Refill.intervally(rateLimit.value(), Duration.ofSeconds(rateLimit.duration()));
        Bandwidth limit = Bandwidth.classic(rateLimit.value(), refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private String generateKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.toLongString();
    }
}
