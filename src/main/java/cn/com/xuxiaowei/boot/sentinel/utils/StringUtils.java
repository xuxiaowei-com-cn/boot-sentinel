package cn.com.xuxiaowei.boot.sentinel.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 探针监控项目
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class StringUtils {

	/**
	 * Spring 转 Map
	 * @param input Spring 字符串
	 * @return Map
	 */
	public static Map<String, String> stringToMap(String input) {
		if (input == null) {
			return new HashMap<>();
		}
		return Arrays.stream(input.split(","))
			.map(pair -> pair.split("="))
			.filter(keyValue -> keyValue.length == 2)
			.collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
	}

}
