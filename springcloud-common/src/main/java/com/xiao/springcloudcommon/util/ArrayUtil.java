package com.xiao.springcloudcommon.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayUtil {
    public ArrayUtil() {
    }

    public static <T> List<Map.Entry<String, T>> sortMapByStringKey(Map<String, T> map) {
        List<Map.Entry<String, T>> list = new ArrayList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, T>>() {
            public int compare(Map.Entry<String, T> o1, Map.Entry<String, T> o2) {
                return ((String) o1.getKey()).compareTo((String) o2.getKey());
            }
        });
        return list;
    }

    public static Map<String, String> queryUrl2Map(String qs) {
        if (qs.contains("?")) {
            qs = qs.substring(qs.indexOf("?") + 1, qs.length());
        }

        return queryString2Map(qs);
    }

    public static Map<String, String> queryString2Map(String qs) {
        return Splitter.on("&").withKeyValueSeparator("=").split(qs);
    }

    public static String map2QueryString(Map<String, Object> map) {
        return Joiner.on("&").withKeyValueSeparator("=").join(map);
    }

    public static Integer getMapInt(Map<String, Object> map, String key) throws NumberFormatException {
        String value = getMapString(map, key);
        return !Strings.isNullOrEmpty(value) ? Integer.parseInt(value) : null;
    }

    public static Long getMapLong(Map<String, Object> map, String key) throws NumberFormatException {
        String value = getMapString(map, key);
        return !Strings.isNullOrEmpty(value) ? Long.parseLong(value) : null;
    }

    public static Float getMapFloat(Map<String, Object> map, String key) {
        String value = getMapString(map, key);
        return !Strings.isNullOrEmpty(value) ? Float.parseFloat(value) : null;
    }

    public static Double getMapDouble(Map<String, Object> map, String key) {
        String value = getMapString(map, key);
        return !Strings.isNullOrEmpty(value) ? Double.parseDouble(value) : null;
    }

    public static BigDecimal getMapBigDecimal(Map<String, Object> map, String key) {
        String value = getMapString(map, key);
        return !Strings.isNullOrEmpty(value) ? new BigDecimal(value) : null;
    }

    public static Date getMapDate(Map<String, Object> map, String key, SimpleDateFormat format) {
        String value = getMapString(map, key);
        if (!Strings.isNullOrEmpty(value)) {
            try {
                return format.parse(value);
            } catch (ParseException var5) {
                ;
            }
        }

        return null;
    }

    public static Date getMapDate(Map<String, Object> map, String key, String format) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return getMapDate(map, key, sf);
    }

    public static Date getMapDateByLong(Map<String, Object> map, String key) {
        Long value = getMapLong(map, key);
        return value != null ? new Date(value) : null;
    }

    public static String getMapText(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    public static String getMapString(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    public static String getMapStringVal(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    public static String getMapString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value != null) {
            return value instanceof ArrayList ? JSONUtil.toJson(value) : StringUtil.purify(value.toString());
        } else {
            return null;
        }
    }

    public static String[] getMapStringArr(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? (String[]) JSONUtil.fromJson(value.toString(), String[].class) : new String[0];
    }

    public static boolean getMapBoolean(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? Boolean.parseBoolean(value.toString()) : false;
    }

    public static Boolean getMapBooleanSolo(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? Boolean.parseBoolean(value.toString()) : null;
    }

    public static Map<String, Object> getMapMap(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? (Map) value : null;
    }

    public static List<Object> getMapList(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? (List) value : null;
    }

    public static <T> List<T> getMapObjectList(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? (List) value : null;
    }

    public static List<Long> getMapListLongType(Map<String, Object> map, String key) {
        Object value = map.get(key);
        List<Long> list = Lists.newArrayList();
        if (value != null) {
            List<Object> listo = (List) value;
            listo.forEach((o) -> {
                if (o != null) {
                    if (o instanceof Long) {
                        list.add((Long) o);
                    } else if (o instanceof Integer) {
                        Integer integer = (Integer) o;
                        int i = integer;
                        list.add((long) i);
                    } else if (o instanceof String) {
                        list.add(Long.parseLong(o.toString()));
                    }
                }

            });
        }

        return list;
    }

    public static List<String> getMapListStringType(Map<String, Object> map, String key) {
        Object value = map.get(key);
        List<String> list = Lists.newArrayList();
        if (value != null) {
            List<String> listo = (List) value;
            listo.forEach((o) -> {
                if (o != null) {
                    list.add(String.valueOf(o));
                }

            });
        }

        return list;
    }

    public static <T> List<T> getMapList(Map<String, Object> map, String key, Class<T> c) throws Exception {
        Object value = map.get(key);
        List<Map<String, Object>> list = Lists.newArrayList();
        if (value != null) {
            List<Map<String, Object>> listo = (List) value;
            listo.forEach((o) -> {
                if (o != null) {
                    list.add(o);
                }

            });
        }

        List<T> tList = Lists.newArrayList();
        Iterator var6 = list.iterator();

        while (var6.hasNext()) {
            Map<String, Object> mapInfo = (Map) var6.next();
            T t = Map2Object(mapInfo, c);
            tList.add(t);
        }

        return tList;
    }

    public static List<Map<String, Object>> getMapListFromMapItem(Map<String, Object> map, String key, String childKey) {
        if (!map.containsKey(key)) {
            List<Map<String, Object>> list = Lists.newArrayList();
            return list;
        } else {
            Map<String, Object> childMap = (Map) map.get(key);
            return getMapListFromMapItem(childMap, childKey);
        }
    }

    public static List<Map<String, Object>> getMapListFromMapItem(Map<String, Object> map, String key) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (!map.containsKey(key)) {
            return list;
        } else {
            Object value = map.get(key);
            if (value != null) {
                List<Map<String, Object>> listo = (List) value;
                listo.forEach((o) -> {
                    if (o != null) {
                        list.add(o);
                    }

                });
            }

            return list;
        }
    }

    public static <T> Object fieldValue(T t, String fieldName) throws Exception {
        Object value = null;
        Field[] fields = t.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Field[] var4 = fields;
        int var5 = fields.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (field.equals(fieldName)) {
                value = field.get(t);
            }
        }

        return value;
    }

    public static Map<String, Object> filed2Map(Object obj) {
        Map<String, Object> map = Maps.newHashMap();
        Field[] fs = obj.getClass().getDeclaredFields();
        Field[] var3 = fs;
        int var4 = fs.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Field f = var3[var5];
            f.setAccessible(true);

            try {
                Object val = f.get(obj);
                map.put(f.getName(), val);
            } catch (Exception var8) {
                ;
            }
        }

        return map;
    }

    public static <T> T Map2Object(Map<String, Object> map, Class<T> c) throws Exception {
        T t = c.newInstance();
        Field[] fields = c.getDeclaredFields();
        Field.setAccessible(fields, true);
        Field[] var4 = fields;
        int var5 = fields.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (map.containsKey(field.getName())) {
                field.set(t, map.get(field.getName()));
            }
        }

        return t;
    }

    public static List<Long> transfer2Long(List<String> list) {
        List<Long> regionIdList = Lists.newArrayList();
        Iterator var2 = list.iterator();

        while (var2.hasNext()) {
            String str = (String) var2.next();
            regionIdList.add(Long.parseLong(str));
        }

        return regionIdList;
    }

    public static void addvalidList(List value1, List value2) {
        Iterator var2 = value2.iterator();

        while (var2.hasNext()) {
            Object obj2 = var2.next();
            if (!value1.contains(obj2)) {
                value1.add(obj2);
            }
        }

    }

    public static List<Long> bigInterger2Long(List<BigInteger> list) {
        List<Long> lList = Lists.newArrayList();
        list.forEach((bigInteger) -> {
            lList.add(bigInteger.longValue());
        });
        return lList;
    }

    public static List<Long> string2Long(List<String> list) {
        List<Long> lList = Lists.newArrayList();
        list.forEach((str) -> {
            lList.add(Long.parseLong(str));
        });
        return lList;
    }

    /*public static <T> List<T> removeRepeat(List<T> list) {
        Set<T> set = Sets.newHashSet();
        set.addAll(list);
        List<T> list = Lists.newArrayListWithCapacity(set.size());
        list.addAll(set);
        return list;
    }*/

    public static Integer[] stringToIntegers(String input, String delimiter) {
        if (Strings.isNullOrEmpty(input)) {
            return null;
        } else {
            List<String> strings = Splitter.on(",").omitEmptyStrings().splitToList(input);
            Integer[] result = new Integer[strings.size()];

            for (int i = 0; i < strings.size(); ++i) {
                result[i] = Integer.valueOf((String) strings.get(i));
            }

            return result;
        }
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static int size(List<?> list) {
        return list != null && list.size() != 0 ? list.size() : 0;
    }

    public static boolean isEmpty(Set<?> set) {
        return set == null || set.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static <T> T getFirstElement(List<T> list) {
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
