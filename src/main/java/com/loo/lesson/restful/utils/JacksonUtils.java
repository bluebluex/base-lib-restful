package com.loo.lesson.restful.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanSerializerFactory;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 18:01
 **/
public class JacksonUtils {

    // 公用objectMapper, 提升JSON转化效率
    private static ObjectMapper mapper = new ObjectMapper();
    protected static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    static {
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static List<Map<String, Object>> deepListClone(List<Map<String, Object>> src) {
        List<Map<String, Object>> dest = null;
        try {
            String jsonStr = mapper.writeValueAsString(src);
            dest = mapper.readValue(jsonStr, List.class);
        } catch (IOException e) {
            logger.error("List利用json，进行深拷贝异常！", e);
        }
        return dest;
    }

    /**
     * List 利用json 进行深拷贝
     *
     * @param src
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> deepObjectClone(List<T> src, Class<T> clazz) {
        List<T> dest = null;
        try {
            String jsonStr = mapper.writeValueAsString(src);
            dest = mapper.readValue(jsonStr, getCollectionType(src.getClass(), clazz));
        } catch (IOException e) {
            logger.error("List利用json，进行深拷贝异常！", e);
        }
        return dest;
    }

    /**
     * List 利用json， 序列化
     */
    public static String jsonListSerializer(List<Map<String, Object>> src) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(src);
        } catch (IOException e) {
            logger.error("List利用json， 序列化", e);
        }
        return jsonStr;
    }

    /**
     * List 列表转为 Json 字符串
     */
    public static String listToJsonStr(List src) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(src);
        } catch (IOException e) {
            logger.error("List 李彪转为 json 字符串");
        }
        return jsonStr;
    }

    /**
     * Map 转成json字符串
     */
    public static String mapToJsonStr(Map src) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(src);
        } catch (IOException e) {
            logger.error("List 李彪转为 json 字符串");
        }
        return jsonStr;
    }

    /**
     * List 利用json， 反序列化
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonListUnSerializer(String jsonStr) {
        List<Map<String, Object>> dest = null;
        try {
            dest = mapper.readValue(jsonStr, List.class);
        } catch (IOException e) {
            logger.error("List 利用json, 反序列化", e);
        }

        return dest;
    }

    /**
     * List 利用json，指定List元素类型， 反序列化
     *
     * @param jsonStr
     * @param elementType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonListUnSerializer(String jsonStr, Class<T> elementType) {
        List<T> dest = null;
        try {
            dest = mapper.readValue(jsonStr, getCollectionType(List.class, elementType));
        } catch (IOException e) {
            logger.error("List 利用json， 指定List 元素类型， 反序列化", e);
        }
        return dest;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * List 利用json，反序列化
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonListUnserializer(JsonNode jsonNode) {
        List<Map<String, Object>> dest = null;

        try {
            dest = mapper.readValue(jsonNode, List.class);
        } catch (IOException e) {
            logger.error("List 利用json， 反序列化", e);
        }
        return dest;
    }

    /**
     * Map 利用json，序列化
     */
    public static String jsonMapSerializer(Map<String, String> src) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(src);
        } catch (IOException e) {
            logger.error("Map 利用json， 序列化", e);
        }
        return jsonStr;
    }

    /**
     * Map 利用json， 反序列化
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonMapUnSerializer(String jsonStr) {
        Map<String, Object> dest = null;
        try {
            dest = mapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            logger.error("Map 利用json， 反序列化", e);
        }

        return dest;
    }

    /**
     * Object 利用json，反序列化
     */
    public static <T> T jsonObjUnSerializer(String jsonStr, Class<T> type) {
        try {
            return mapper.readValue(jsonStr, type);
        } catch (IOException e) {
            logger.error("解析json jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * Object 利用json，指定类型引用TypeReference类型反序列化
     * eg:
     * List<A> list = jsonObjUnSerializer(listj, new TypeReference<List<A>>() {});
     * A a = jsonObjUnSerializer(listj, new TypeReference<A>() {});
     */
    public static <T> T jsonObjUnSerializer(String jsonStr, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            logger.error("Object利用json，指定类型引用，new TypeReference<Map<String, ResultValue>>() { } jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * Object利用json，反序列化，支持泛型
     * eg:
     * JavaType type = TypeFactory.defaultInstance().constructParametricType(HeadBodyVo.class, LoginReqVo.class);
     * HeadBodyVo<LoginReqVO> vo = jsonObjUnSerializer("{\"head\":{}, \"body\":{}}, type");
     */
    public static <T> T jsonObjUnSerializer(String jsonStr, JavaType type) {
        try {
            return mapper.readValue(jsonStr, type);
        } catch (IOException e) {
            logger.error("解析json，jsonStr={}", jsonStr, e);
        }
        return null;
    }

    /**
     * Object 利用json, 指定类型引用TypeReference类型反序列化
     * eg:
     * List<A> list = jsonObjUnSerializer(listj, new TypeReference<List<A>>() {});
     * A a = jsonObjUnSerializer(listj, new TypeReference<A>() {});
     *
     * @param jsonNode
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T jsonObjUnSerializer(JsonNode jsonNode, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(jsonNode, typeReference);
        } catch (IOException e) {
            logger.error("Object 利用json，指定类型引用， new TypeReference<Map<String, ResultValue>>() { }", e);
        }
        return null;
    }

    /**
     * Object 利用json， 反序列化
     */
    public static <T> T jsonObjUnSerializer(JsonNode jsonNode, Class<T> type) {
        try {
            return mapper.readValue(jsonNode, type);
        } catch (IOException e) {
            logger.error("解析json， jsonStr={}", jsonNode, e);
        }
        return null;
    }

    /**
     * Object 利用json， 反序列化
     */
    public static <T> T jsonObjUnSerializer(JsonNode jsonNode, JavaType type) {
        try {
            return mapper.readValue(jsonNode, type);
        } catch (IOException e) {
            logger.error("解析json， jsonStr={}", jsonNode, e);
        }
        return null;
    }

    /**
     * Map利用json，反序列化
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonMapUnSerializer(JsonNode jsonNode) {
        Map<String, Object> dest = null;
        try {
            dest = mapper.readValue(jsonNode, Map.class);
        } catch (IOException e) {
            logger.error("Map利用json， 反序列化， jsonStr={}", jsonNode, e);
        }
        return dest;
    }

    /**
     * 讲对象转换为Json字符串
     */
    public static String jsonObjSerializer(Object obj) {
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("将对象转换为json字符串", e);
        }
        return jsonStr;
    }

    /**
     * 配置Json类型转换过滤器
     *
     * @param obj
     * @param excludeItem
     * @param dateFormate
     * @return
     */
    public static String configJsonObjSerializer(Object obj, String[] excludeItem, String dateFormate) {
        String jsonStr = null;
        try {
            if (ArrayUtils.isNotEmpty(excludeItem)) {
                SimpleFilterProvider filter = new SimpleFilterProvider();
                filter.addFilter("executeFilter", SimpleBeanPropertyFilter.serializeAllExcept(excludeItem));
                mapper.setFilters(filter);
                mapper.setSerializerFactory(new BeanSerializerFactory(new BeanSerializerFactory.ConfigImpl()) {
                    @Override
                    protected Object findFilterId(SerializationConfig config, BasicBeanDescription beanDesc) {
                        return "executeFilter";
                    }
                });
            }
            // 设置日期格式化
            if (StringUtils.isNotBlank(dateFormate)) {
                mapper.setDateFormat(new SimpleDateFormat(dateFormate));
            }
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("配置Json类型转换过滤器", e);
        }
        return jsonStr;
    }

    /**
     * 将json字符串转换为对象
     */
    public static Object jsonObject(String jsonStr) {
        Object dest = null;
        try {
            dest = mapper.readValue(jsonStr, Object.class);
        } catch (IOException e) {
            logger.error("将json字符串转换为对象，jsonStr={}", jsonStr, e);
        }
        return dest;
    }

    /**
     * Json对象转换
     * @param obj
     * @param excludeItem
     * @param dateFormate
     * @return
     */
    public static Object configJsonObject(Object obj, String[] excludeItem, String dateFormate) {
        Object dest = null;
        try {
            String jsonStr = configJsonObjSerializer(obj, excludeItem, dateFormate);
            if (jsonStr != null) {
                dest = jsonObject(jsonStr);
            }
        } catch (Exception e) {
            logger.error("Json 对象转换", e);
        }
        return dest;
    }
}
