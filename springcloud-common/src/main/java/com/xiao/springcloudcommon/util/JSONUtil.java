package com.xiao.springcloudcommon.util;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONUtil {
    private static Logger log = LoggerFactory.getLogger(JSONUtil.class);
    public static final String TAG = "JSONUtils";
    public static final String EMPTY_JSON = "{}";
    public static final String EMPTY_JSON_ARRAY = "[]";
    public static final double SINCE_VERSION_10 = 1.0D;
    public static final double SINCE_VERSION_11 = 1.1D;
    public static final double SINCE_VERSION_12 = 1.2D;
    public static final double UNTIL_VERSION_10 = 1.0D;
    public static final double UNTIL_VERSION_11 = 1.1D;
    public static final double UNTIL_VERSION_12 = 1.2D;

    public JSONUtil() {
    }

    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose, Class<?> typeClass, Object typeAdapter) {
        if (target == null) {
            return "{}";
        } else {
            GsonBuilder builder = new GsonBuilder();
            if (typeAdapter != null) {
                builder.registerTypeAdapter(typeClass, typeAdapter);
            }

            if (isSerializeNulls) {
                builder.serializeNulls();
            }

            if (version != null) {
                builder.setVersion(version);
            }

            if (Strings.isNullOrEmpty(datePattern)) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            if (excludesFieldsWithoutExpose) {
                builder.excludeFieldsWithoutExposeAnnotation();
            }

            return toJson(target, targetType, builder);
        }
    }

    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, isSerializeNulls, version, datePattern, excludesFieldsWithoutExpose, (Class)null, (Object)null);
    }

    public static String toJson(Object target) {
        return toJson(target, (Type)null, false, (Double)null, (String)null, true);
    }

    public static String toJson(Object target, Class<?> typeClass, Object typeAdapter) {
        return toJson(target, (Type)null, false, (Double)null, (String)null, true, typeClass, typeAdapter);
    }

    public static String toJson(Object target, String datePattern) {
        return toJson(target, (Type)null, false, (Double)null, datePattern, true);
    }

    public static String toJson(Object target, Double version) {
        return toJson(target, (Type)null, false, version, (String)null, true);
    }

    public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
        return toJson(target, (Type)null, false, (Double)null, (String)null, excludesFieldsWithoutExpose);
    }

    public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
        return toJson(target, (Type)null, false, version, (String)null, excludesFieldsWithoutExpose);
    }

    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, false, (Double)null, (String)null, false);
    }

    public static String toJson(Object target, Type targetType, Double version) {
        return toJson(target, targetType, false, version, (String)null, true);
    }

    public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, (Double)null, (String)null, excludesFieldsWithoutExpose);
    }

    public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, version, (String)null, excludesFieldsWithoutExpose);
    }

    public static <T> T fromJsonObject(JsonObject json, Class<T> clazz) {
        return fromJsonObject(json, clazz, (String)null, (Class)null, (Object)null);
    }

    public static <T> T fromJsonObject(JsonObject json, Class<T> clazz, String datePattern) {
        return fromJsonObject(json, clazz, datePattern, (Class)null, (Object)null);
    }

    public static <T> T fromJsonObject(JsonObject json, Class<T> clazz, String datePattern, Class<?> typeClass, Object typeAdapter) {
        if (json != null && !json.isJsonNull()) {
            GsonBuilder builder = new GsonBuilder();
            if (Strings.isNullOrEmpty(datePattern)) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            Gson gson = typeAdapter != null ? builder.registerTypeAdapter(typeClass, typeAdapter).create() : builder.create();

            try {
                return gson.fromJson(json, clazz);
            } catch (Exception var8) {
                log.error("JSONUtils", json + " 无法转换为 " + clazz.getName() + " 对象!", var8);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        } else {
            GsonBuilder builder = new GsonBuilder();
            if (Strings.isNullOrEmpty(datePattern)) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = builder.create();

            try {
                return gson.fromJson(json, token.getType());
            } catch (Exception var6) {
                log.error("JSONUtils", json + " 无法转换为 " + token.getRawType().getName() + " 对象!", var6);
                return null;
            }
        }
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, (String)null);
    }

    public static <T> T fromJson(String json, Class<T> clazz, String datePattern, Class<?> typeClass, Object typeAdapter) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        } else {
            GsonBuilder builder = new GsonBuilder();
            if (Strings.isNullOrEmpty(datePattern)) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = typeAdapter != null ? builder.registerTypeAdapter(typeClass, typeAdapter).create() : builder.create();

            try {
                return gson.fromJson(json, clazz);
            } catch (Exception var8) {
                log.error(String.format("json convert fail : json[%s], class[%s]", json, clazz.getName()), var8);
                return null;
            }
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, (String)null, (Class)null, (Object)null);
    }

    public static <T> T fromJson(String json, Class<T> clazz, Class<?> typeClass, Object typeAdapter) {
        return fromJson(json, clazz, (String)null, typeClass, typeAdapter);
    }

    public static String toJson(Object target, Type targetType, GsonBuilder builder) {
        if (target == null) {
            return "{}";
        } else {
            Gson gson = null;
            if (builder == null) {
                gson = new Gson();
            } else {
                gson = builder.create();
            }

            String result = "{}";

            try {
                if (targetType == null) {
                    result = gson.toJson(target);
                } else {
                    result = gson.toJson(target, targetType);
                }
            } catch (Exception var6) {
                log.error("JSONUtils", "目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！", var6);
                if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray()) {
                    result = "[]";
                }
            }

            return result;
        }
    }

    public static JsonElement toJsonElement(Object target) {
        return toJsonElement(target, (Type)null, false, (Double)null, (String)null, false);
    }

    public static JsonElement toJsonElement(Object target, Type targetType) {
        return toJsonElement(target, targetType, false, (Double)null, (String)null, false);
    }

    public static JsonElement toJsonElement(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose) {
        return toJsonElement(target, targetType, isSerializeNulls, version, datePattern, excludesFieldsWithoutExpose, (Class)null, (Object)null);
    }

    public static JsonElement toJsonElement(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose, Class<?> typeClass, Object typeAdapter) {
        if (target == null) {
            return JsonNull.INSTANCE;
        } else {
            GsonBuilder builder = new GsonBuilder();
            if (typeAdapter != null) {
                builder.registerTypeAdapter(typeClass, typeAdapter);
            }

            if (isSerializeNulls) {
                builder.serializeNulls();
            }

            if (version != null) {
                builder.setVersion(version);
            }

            if (Strings.isNullOrEmpty(datePattern)) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            if (excludesFieldsWithoutExpose) {
                builder.excludeFieldsWithoutExposeAnnotation();
            }

            return toJsonElement(target, targetType, builder);
        }
    }

    public static JsonElement toJsonElement(Object target, Type targetType, GsonBuilder builder) {
        if (target == null) {
            return JsonNull.INSTANCE;
        } else {
            Gson gson = null;
            if (builder == null) {
                gson = new Gson();
            } else {
                gson = builder.create();
            }

            try {
                return targetType == null ? gson.toJsonTree(target) : gson.toJsonTree(target, targetType);
            } catch (Exception var5) {
                log.error("JSONUtils", "目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！", var5);
                return (JsonElement)(!(target instanceof Collection) && !(target instanceof Iterator) && !(target instanceof Enumeration) && !target.getClass().isArray() ? JsonNull.INSTANCE : new JsonArray());
            }
        }
    }

    public static JsonObject parse(String json) {
        return (new JsonParser()).parse(json).getAsJsonObject();
    }

    public static JsonArray parseArray(String json) {
        return (new JsonParser()).parse(json).getAsJsonArray();
    }

    public static JsonArray getElementJsonArray(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsJsonArray() : null;
        }
    }

    public static JsonObject getElementJsonObject(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsJsonObject() : null;
        }
    }

    public static JsonObject getElementJsonObject(JsonObject jo, String name, String subName) {
        JsonObject joNext = getElementJsonObject(jo, name);
        return joNext == null ? null : getElementJsonObject(joNext, subName);
    }

    public static String getElementString(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsString() : null;
        }
    }

    public static String getElementString(JsonObject jo, String name, String subName) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? getElementString(je.getAsJsonObject(), subName) : null;
        }
    }

    public static BigDecimal getElementDecimal(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsBigDecimal() : null;
        }
    }

    public static BigDecimal getElementDecimal(JsonObject jo, String name, String subName) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? getElementDecimal(je.getAsJsonObject(), subName) : null;
        }
    }

    public static Integer getElementInt(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsInt() : null;
        }
    }

    public static Integer getElementInt(JsonObject jo, String name, String subName) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? getElementInt(je.getAsJsonObject(), subName) : null;
        }
    }

    public static Long getElementLong(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsLong() : null;
        }
    }

    public static Long getElementLong(JsonObject jo, String name, String subName) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? getElementLong(je.getAsJsonObject(), subName) : null;
        }
    }

    public static Double getElementDouble(JsonObject jo, String name) {
        if (jo == null) {
            return null;
        } else {
            JsonElement je = jo.get(name);
            return je != null && !je.isJsonNull() ? je.getAsDouble() : null;
        }
    }

    public static JsonObject toJsonObject(Object target) {
        JsonElement je = toJsonElement(target);
        return je != null && !je.isJsonNull() ? je.getAsJsonObject() : null;
    }

    public static JsonArray toJsonArray(Object target) {
        JsonElement je = toJsonElement(target);
        return je != null && !je.isJsonNull() ? je.getAsJsonArray() : null;
    }

    public static JsonArray toJsonArray(List<String> list) {
        if (list.size() <= 0) {
            return null;
        } else {
            JsonArray ja = new JsonArray();
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                String item = (String)var2.next();
                ja.add(toJsonElement(item));
            }

            return ja;
        }
    }

    public static HashMap<String, Object> toHashMap(JsonElement jeRoot) {
        HashMap<String, Object> result = Maps.newHashMap();
        if (jeRoot.isJsonObject()) {
            JsonObject jo = jeRoot.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> set = jo.entrySet();
            Iterator var4 = set.iterator();

            while(var4.hasNext()) {
                Map.Entry<String, JsonElement> kv = (Map.Entry)var4.next();
                JsonElement kvValue = (JsonElement)kv.getValue();
                if (kvValue.isJsonPrimitive()) {
                    JsonPrimitive jp = kvValue.getAsJsonPrimitive();
                    if (jp.isNumber()) {
                        BigDecimal vd = kvValue.getAsBigDecimal();
                        if ((new BigDecimal(vd.intValue())).compareTo(vd) == 0) {
                            result.put(kv.getKey(), kvValue.getAsInt());
                        } else {
                            result.put(kv.getKey(), vd);
                        }
                    } else if (jp.isBoolean()) {
                        result.put(kv.getKey(), kvValue.getAsBoolean());
                    } else {
                        result.put(kv.getKey(), kvValue.getAsString());
                    }
                }
            }
        }

        return result;
    }

    public static ArrayList<Map<String, Object>> toHashMapList(JsonElement jeRoot) {
        ArrayList<Map<String, Object>> result = Lists.newArrayList();
        if (jeRoot.isJsonArray()) {
            Iterator var2 = jeRoot.getAsJsonArray().iterator();

            while(var2.hasNext()) {
                JsonElement je = (JsonElement)var2.next();
                result.add(toHashMap(je));
            }
        }

        return result;
    }
}
