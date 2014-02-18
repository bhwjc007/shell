package shell.framework.util;

/**
 * Copyright (c) 2011-2012, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Convert Object to json string.
 * 
 * Json     		java
 * string			java.lang.String
 * number			java.lang.Number
 * true|false		java.lang.Boolean
 * null				null
 * array			java.util.List
 * object			java.util.Map
 */
@SuppressWarnings( { "rawtypes", "unchecked" })
public class JsonUtil {

	private static final int DEFAULT_DEPTH = 8;

	/**
     * convert map to json string.
     *
	 * @param map
	 * @param depth
	 * @return
	 */
	public static String mapToJson(Map map, int depth) {
		if (map == null)
			return "null";

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		Iterator iter = map.entrySet().iterator();
		sb.append('[');
		while (iter.hasNext()) {
			if (first) 
				first = false;
			else
				sb.append(',');
			Map.Entry entry = (Map.Entry) iter.next();
			toKeyValue(String.valueOf(entry.getKey()), entry.getValue(), sb,depth);
		}
		sb.append(']');
		return sb.toString();
	}

	
	/**
	 * convert key and value to javascript object
     *
	 * @param key    key
	 * @param value  value
	 * @param sb     stringBuilder
	 * @param depth  rescure length
	 * @return   {name:xxx,value:xxx}
	 */
	private static String toKeyValue(String key, Object value,StringBuilder sb, int depth) {
		sb.append('{').append("name:");
		sb.append('\"');
		if (key == null)
			sb.append("null");
		else
			escape(key, sb);
		sb.append('\"').append(',');
		sb.append(toJson(value, depth));
		sb.append("}");
		return sb.toString();
	}

	
	/**
	 * 自定义转换json格式 - by changming.y
     *
	 * @param value
	 * @return  data: [370.0,162.0,13,13,370.0,162.0,13,13]
	 */
	private static String toLocalJson(Object value , int depth){
		String[] xAxisCategories = new String[]{"2013-01","2013-02","2013-03","2013-04",
												"2013-05","2013-06","2013-07","2013-08",
												"2013-09","2013-10","2013-11","2013-12"};
		StringBuilder sb = new StringBuilder();
		String _str = "";
		if(value==null || (depth--) <= 0){
			return "null";
		}
		if(value instanceof Map){
			Map _mapResult = (Map)value;
			sb.append("data:[");
			
			for(String xAxis : xAxisCategories){
				if(_mapResult.containsKey(xAxis)){
					sb.append(_mapResult.get(xAxis)).append(",");
				}else{
					sb.append("null,");
				}
			}
			
			_str = sb.toString().substring(0,sb.toString().length()-1);
			_str = _str + "]";
		}
		return _str;
	}
	
	
	
	/**
	 * convert list to json
     *
	 * @param list
	 * @param depth
	 * @return    [aa,bb,cc]
	 */
	public static String listToJson(List list, int depth) {
		if (list == null)
			return "null";

		boolean first = true;
		StringBuilder sb = new StringBuilder();
		Iterator iter = list.iterator();

		sb.append('[');
		while (iter.hasNext()) {
			if (first)
				first = false;
			else
				sb.append(',');

			Object value = iter.next();
			if (value == null) {
				sb.append("null");
				continue;
			}
			sb.append(toJson(value, depth));
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
	 */
	private static String escape(String s) {
		if (s == null)
			return null;
		StringBuilder sb = new StringBuilder();
		escape(s, sb);
		return sb.toString();
	}

	
	/**
	 * escape quote. and other control characters
     *
	 * @param s
	 * @param sb
	 */
	private static void escape(String s, StringBuilder sb) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if ((ch >= '\u0000' && ch <= '\u001F')
						|| (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')) {
					String str = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - str.length(); k++) {
						sb.append('0');
					}
					sb.append(str.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
	}

	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String toJson(Object value) {
		return toJson(value, DEFAULT_DEPTH);
	}


	/**
	 * 转为json数据，判断value值的类型不同，调用不同的转json方式
     *
	 * @param value
	 * @param depth
	 * @return   字符串
	 */
	public static String toJson(Object value, int depth) {
		if (value == null || (depth--) <= 0)
			return "null";

		if (value instanceof String)
			return "\"" + escape((String) value) + "\"";

		if (value instanceof Double) {
			if (((Double) value).isInfinite() || ((Double) value).isNaN())
				return "null";
			else
				return value.toString();
		}

		if (value instanceof Float) {
			if (((Float) value).isInfinite() || ((Float) value).isNaN())
				return "null";
			else
				return value.toString();
		}

		if (value instanceof Number)
			return value.toString();

		if (value instanceof Boolean)
			return value.toString();

		if (value instanceof Map) {
			return mapToJson((Map) value, depth);
		}

		if (value instanceof List) {
			return listToJson((List) value, depth);
		}

		String result = otherToJson(value, depth);
		if (result != null)
			return result;

		// 类型无法处理时当作字符串处理,否则ajax调用返回时js无法解析
		// return value.toString();
		return "\"" + escape(value.toString()) + "\"";
	}

	
	/**
	 * 其他格式数据转为json，非java提供的内置类型
     *
	 * @param value
	 * @param depth
	 * @return
	 */
	private static String otherToJson(Object value, int depth) {
		if (value instanceof java.util.Date || value instanceof Character)
			return null;

//		if (value instanceof Model) {
//			Map map = com.jfinal.plugin.activerecord.CPI
//					.getAttrs((Model) value);
//			return mapToJson(map, depth);
//		}
//		if (value instanceof Record) {
//			Map map = ((Record) value).getColumns();
//			return mapToJson(map, depth);
//		}
		if (value instanceof Object[]) {
			Object[] arr = (Object[]) value;
			List list = new ArrayList(arr.length);
			for (int i = 0; i < arr.length; i++)
				list.add(arr[i]);
			return listToJson(list, depth);
		}
		return beanToJson(value, depth);
	}


    /**
     * convert bean to json
     *
     * @param model  bean
     * @param depth  deep length
     * @return    string
     */
	private static String beanToJson(Object model, int depth) {
		Map map = new HashMap();
		Method[] methods = model.getClass().getMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			int indexOfGet = methodName.indexOf("get");
			if (indexOfGet == 0 && methodName.length() > 3) { // Only getter
				String attrName = methodName.substring(3);
				if (!attrName.equals("Class")) { // Ignore Object.getClass()
					Class<?>[] types = m.getParameterTypes();
					if (types.length == 0) {
						try {
							Object value = m.invoke(model);
							map.put(attrName,value);
						} catch (Exception e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					}
				}
			} else {
				int indexOfIs = methodName.indexOf("is");
				if (indexOfIs == 0 && methodName.length() > 2) {
					String attrName = methodName.substring(2);
					Class<?>[] types = m.getParameterTypes();
					if (types.length == 0) {
						try {
							Object value = m.invoke(model);
							map.put(attrName,value);
						} catch (Exception e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					}
				}
			}
		}
		return mapToJson(map, depth);
	}


    /**
     * test
     *
     * @param args
     */
	public static void main(String[] args){
		Map staticsMap = new HashMap();
		Map monthMap =  null;
		
		monthMap = new HashMap();
		monthMap.put("2013-01", 2);
		staticsMap.put("李海霞", monthMap);
		
		monthMap = new HashMap();
		monthMap.put("2013-11", 22);
		monthMap.put("2013-09", 44);
		monthMap.put("2013-04", 29);
		staticsMap.put("吴颖", monthMap);
		
		monthMap = new HashMap();
		monthMap.put("2014-01", 2);
		monthMap.put("2013-01", 10);
		staticsMap.put("李磊", monthMap);
		
		
		String jsonStr = JsonUtil.mapToJson(staticsMap, DEFAULT_DEPTH);
		
		System.out.println(jsonStr);

	}

}
