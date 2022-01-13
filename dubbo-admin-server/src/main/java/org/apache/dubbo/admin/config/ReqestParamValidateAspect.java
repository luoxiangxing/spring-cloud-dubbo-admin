package org.apache.dubbo.admin.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  created by huaronghao on 2021/9/9 01:54
 *  统一参数检验处理aspect
 */
@Aspect
@Component
@Order(0)
@Slf4j
public class ReqestParamValidateAspect {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * @param pjp
     * @return
     */
    @Around("execution(* org.apache.dubbo.admin.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        List<Object> objectList = Arrays.stream(pjp.getArgs()).filter(o -> !(o instanceof ServletRequest)&& !(o instanceof ServletResponse)).collect(Collectors.toList());
        String reqStr = JSON.toJSONString(objectList);
        log.info("===={} 请求参数: {}====", ms.toShortString(), reqStr);
        Object result;
        try {
            result = pjp.proceed();
            log.info("===={} 耗时: {}ms 返回: {}====", ms.toShortString(), System.currentTimeMillis() - start, JSON.toJSONString(result));
        } catch (Throwable e) {
            //不可能抛异常，已被内层aspect拦截
            log.error("====请求异常 {} 耗时: {}ms====", ms.toShortString(), System.currentTimeMillis() - start, e);
            result = e.getMessage();
        }
        return result;
    }
}
