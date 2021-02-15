package com.raiden.utils;
import com.raiden.model.RoutingRules;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * dubbo 路由工具
 */
@Slf4j
public final class RoutingRulesUtils {

    private static final String RULE_TEMPLATE = "=> host != ";
    private static final String URL_TEMPLATE = "condition://0.0.0.0/%1s?category=routers&dynamic=false&runtime=true&rule=%2s";
    private static final String UTF_8 = "UTF-8";
    private static Pattern ROUTE_PATTERN = Pattern.compile("([&!=,]*)\\s*([^&!=,\\s]+)");

    public static final String createRule(String path,String... hosts){
        //dubbo 中路由多个可以使用逗号隔开
        String rule = "method = getAll" + RULE_TEMPLATE + String.join(",", hosts);
        try {
            rule = String.format(URL_TEMPLATE, path, URLEncoder.encode(rule, UTF_8));
            return URLEncoder.encode(rule, UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error("RuleUtils.createRule url encode is error", e);
            return rule;
        }
    }

    public static Map<String, RoutingRules> parseRule(String rule)
            throws ParseException {
        Map<String, RoutingRules> condition = new HashMap<>();
        if (StringUtils.isBlank(rule)) {
            return condition;
        }
        // Key-Value pair, stores both match and mismatch conditions
        RoutingRules pair = null;
        // Multiple values
        Set<String> values = null;
        final Matcher matcher = ROUTE_PATTERN.matcher(rule);
        while (matcher.find()) { // Try to match one by one
            String separator = matcher.group(1);
            String content = matcher.group(2);
            // Start part of the condition expression.
            if (separator == null || separator.length() == 0) {
                pair = new RoutingRules();
                condition.put(content, pair);
            }
            // The KV part of the condition expression
            else if ("&".equals(separator)) {
                if (condition.get(content) == null) {
                    pair = new RoutingRules();
                    condition.put(content, pair);
                } else {
                    pair = condition.get(content);
                }
            }
            // The Value in the KV part.
            else if ("=".equals(separator)) {
                if (pair == null)
                    throw new ParseException("Illegal route rule \""
                            + rule + "\", The error char '" + separator
                            + "' at index " + matcher.start() + " before \""
                            + content + "\".", matcher.start());

                values = pair.getMatches();
                values.add(content);
            }
            // The Value in the KV part.
            else if ("!=".equals(separator)) {
                if (pair == null)
                    throw new ParseException("Illegal route rule \""
                            + rule + "\", The error char '" + separator
                            + "' at index " + matcher.start() + " before \""
                            + content + "\".", matcher.start());

                values = pair.getMismatches();
                values.add(content);
            }
            // The Value in the KV part, if Value have more than one items.
            else if (",".equals(separator)) { // Should be seperateed by ','
                if (values == null || values.isEmpty())
                    throw new ParseException("Illegal route rule \""
                            + rule + "\", The error char '" + separator
                            + "' at index " + matcher.start() + " before \""
                            + content + "\".", matcher.start());
                values.add(content);
            } else {
                throw new ParseException("Illegal route rule \"" + rule
                        + "\", The error char '" + separator + "' at index "
                        + matcher.start() + " before \"" + content + "\".", matcher.start());
            }
        }
        return condition;
    }
}
