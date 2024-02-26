package cn.com.xuxiaowei.boot.sentinel.interceptor;

import cn.com.xuxiaowei.utils.unit.DataSize;
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
		// 开始：时间
		long startTime = System.currentTimeMillis();
		// 开始：虚拟机运行时对象
		Runtime startRuntime = Runtime.getRuntime();
		// 开始：总内存
		long totalMemory = startRuntime.totalMemory();
		// 开始：最大内存
		long maxMemory = startRuntime.maxMemory();
		// 开始：空闲内存
		long startFreeMemory = startRuntime.freeMemory();
		// 开始：使用内存
		long startUsedMemory = totalMemory - startFreeMemory;

		Object result = callable.call();

		// 结束：时间
		long endTime = System.currentTimeMillis();
		// 结束：虚拟机运行时对象
		Runtime endRuntime = Runtime.getRuntime();
		// 结束：空闲内存
		long endFreeMemory = endRuntime.freeMemory();
		// 结束：使用内存
		long endUsedMemory = totalMemory - endFreeMemory;

		Class<?> declaringClass = method.getDeclaringClass();
		String declaringClassName = declaringClass.getName();

		String methodName = method.getName();

		StringBuilder parameterTypeNameBuilder = new StringBuilder();
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (Class<?> parameterType : parameterTypes) {
			parameterTypeNameBuilder.append(parameterType.getTypeName());
		}

		logger.info("执行耗时: {} ms，开始内存：{}，结束内存: {}，使用内存: {}", (endTime - startTime),
				DataSize.ofBytes(startUsedMemory).toStringLongUnit(),
				DataSize.ofBytes(endUsedMemory).toStringLongUnit(),
				DataSize.ofBytes(endFreeMemory - startFreeMemory).toStringLongUnit());

		return result;
	}

}
