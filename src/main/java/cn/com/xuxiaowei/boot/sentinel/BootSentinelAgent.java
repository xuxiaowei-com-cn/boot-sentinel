package cn.com.xuxiaowei.boot.sentinel;

import cn.com.xuxiaowei.boot.sentinel.interceptor.ExecutionTimeInterceptor;
import cn.com.xuxiaowei.boot.sentinel.utils.StringUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.Instrumentation;

/**
 * 探针监控项目
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class BootSentinelAgent {

	private static final Logger logger = LogManager.getLogger(BootSentinelAgent.class);

	public static void premain(String agentArgs, Instrumentation inst) {
		logger.info("BootSentinelAgent 已启动");

		String bootSentinelPrintlnArgs = System.getenv("BOOT_SENTINEL_PRINTLN_ARGS");
		if (Boolean.TRUE.toString().equalsIgnoreCase(bootSentinelPrintlnArgs)) {
			logger.info("BootSentinelAgent 参数：{}", StringUtils.stringToMap(agentArgs));
		}

		AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule,
				protectionDomain) -> builder.method(ElementMatchers.any())
					.intercept(MethodDelegation.to(ExecutionTimeInterceptor.class));

		new AgentBuilder.Default().type(ElementMatchers.nameStartsWith("com.example.demo.controller"))
			.transform(transformer)
			.installOn(inst);
	}

}
