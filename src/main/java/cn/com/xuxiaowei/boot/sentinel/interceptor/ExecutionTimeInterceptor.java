package cn.com.xuxiaowei.boot.sentinel.interceptor;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class ExecutionTimeInterceptor {

	private static final Logger logger = LogManager.getLogger(ExecutionTimeInterceptor.class);

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
		long start = System.currentTimeMillis();
		Object result = callable.call();

		Class<?> declaringClass = method.getDeclaringClass();
		String declaringClassName = declaringClass.getName();

		String methodName = method.getName();

		StringBuilder parameterTypeNameBuilder = new StringBuilder();
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (Class<?> parameterType : parameterTypes) {
			parameterTypeNameBuilder.append(parameterType.getTypeName());
		}

		logger.info("{}#{}({}) 执行耗时 {} ms", declaringClassName, methodName, parameterTypeNameBuilder.toString(),
				(System.currentTimeMillis() - start));

		return result;
	}

}
