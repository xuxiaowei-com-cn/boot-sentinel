package cn.com.xuxiaowei.boot.sentinel;

import java.lang.instrument.Instrumentation;

/**
 * 探针监控项目
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class BootSentinelAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("BootSentinelAgent is running.");
	}

}
