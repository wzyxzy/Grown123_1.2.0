package com.pndoo.grown123_new.util;

import android.util.Log;

import com.pndoo.grown123_new.annotations.ComplexSerializableType;
import com.pndoo.grown123_new.annotations.NotRecursiveSync;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ObjectHelper {

	public ObjectHelper() {
	}

	/**
	 * 同步对象，把第二个参数对象同步到第一个参数对象中
	 * 
	 * @param original
	 *            原始对象
	 * @param updated
	 *            更新对象
	 * @throws Exception
	 */
	public void syncObjectGraph(Object original, Object updated)
			throws Exception {
		// get all getters
		List<Method> getters = getGetterMethods(original);

		if (updated instanceof Map) {
			Log.d("updated instanceof Map", "updated instanceof Map");
			Iterator entries = ((Map) updated).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				for (Method method : getters) {
					if (method.getName().replace("get", "").toUpperCase()
							.equals(name.toUpperCase())) {
						if (isComplexSerializableType(method)
								&& !isNotRecursiveSynchable(method)) {
							Object valueOrig = method.invoke(original, null);
							if (entry.getValue() instanceof Collection) {
								setValue(
										method, 
										original,
										syncListObject(
												entry.getValue(),
												getComplexSerializableType(method)));
							} else {
								syncObjectGraph(valueOrig, entry.getValue());
							}
						} else {
							setValue(method, original, entry.getValue());
						}
						break;
					}
				}
			}
		} else {
			// start synching
			for (Method method : getters) {
				Object valueOrig = method.invoke(original, null);
				Object valueNew = method.invoke(updated, null);

				if (valueOrig == null || valueNew == null) {
					setValue(method, original, valueNew);
				} else {
					if (isComplexSerializableType(method)) {
						// complex object
						if (valueOrig == null || valueNew == null
								|| isNotRecursiveSynchable(method)) {
							setValue(method, original, valueNew);
						} else {
							syncObjectGraph(valueOrig, valueNew);
						}
					} else {
						boolean equal = (((Comparable) valueOrig)
								.compareTo((Comparable) valueNew) == 0);

						if (!equal) {
							// have to sync: retrieve appropriate setter
							setValue(method, original, valueNew);
						}
					}
				}
			}
		}
	}

	public List syncListObject4Page(Object original, Class clazz)
			throws Exception {
		List list = new ArrayList();
		if (original instanceof Map) {
			Iterator entries = ((Map) original).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				if (entry.getKey().toString().toLowerCase().equals("list")) {
					if (entry.getValue() instanceof Collection) {
						Iterator listObjs = ((Collection) entry.getValue())
								.iterator();
						while (listObjs.hasNext()) {
							Object next = listObjs.next();
							Object obj = Class.forName(clazz.getName())
									.newInstance();
							syncObjectGraph(obj, next);
							list.add(obj);
						}
					}
					break;
				}

			}
		}

		return list;
	}

	public List syncListObject(Object original, Class clazz) throws Exception {
		List list = new ArrayList();
		if (original instanceof Collection) {
			Iterator entries = ((Collection) original).iterator();
			while (entries.hasNext()) {
				Object next = entries.next();
				Object obj = Class.forName(clazz.getName()).newInstance();
				syncObjectGraph(obj, next);
				list.add(obj);
			}
		}
		return list;
	}

	private void setValue(Method method, Object original, Object valueNew)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		String setterName = "set" + method.getName().replace("get", "");
		Method setter = original.getClass().getMethod(setterName,
				method.getReturnType());
		Class returnType = method.getReturnType();
		if (returnType == Double.class || returnType == double.class) {
			setter.invoke(original,
					Double.parseDouble(String.valueOf(valueNew)));
		}
		if (returnType == Date.class) {
			// TODO 如果是日期怎么处理
			// setter.invoke(original,
			// Double.parseDouble(String.valueOf(valueNew)));
		} else {

			setter.invoke(original, valueNew);
		}
	}

	private boolean isNotRecursiveSynchable(Method method) {
		return getNotRecursiveSyncAnnotation(method) != null;
	}

	private Annotation getNotRecursiveSyncAnnotation(Method method) {
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof NotRecursiveSync)
				return annotation;
		}
		return null;
	}

	private boolean isComplexSerializableType(Method method) {
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ComplexSerializableType)
				return true;
		}

		return false;
	}

	private Class getComplexSerializableType(Method method) {
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ComplexSerializableType)
				return ((ComplexSerializableType) annotation).clazz();
		}

		return null;
	}

	/**
	 * Retrieves a list of getter methods for the given object
	 * 
	 * @param object
	 *            the object from which to fetch the getters
	 * @return {@link java.util.List< java.lang.reflect.Method>} of getters
	 */
	private List<Method> getGetterMethods(Object object) {
		List<Method> getters = new ArrayList<Method>();
		for (Method method : object.getClass().getMethods()) {
			if (isValidGetter(method))
				getters.add(method);
		}
		return getters;
	}

	/**
	 * Checks whether the given method is a valid getter. Valid getters are -
	 * belonging to the implemented model - not JRE native getters like
	 * "getClass" - comply with the Java getter standard (i.e. return type, no
	 * parameters)
	 * 
	 * @param method
	 * @return
	 */
	private boolean isValidGetter(Method method) {
		if (method.getName().toUpperCase().equals("GETCLASS"))
			return false;
		if (!method.getName().startsWith("get"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	/*
	 * public static void main(String[] args) { ObjectHelper objectHelper = new
	 * ObjectHelper(); List list = new ArrayList(); Map map = new HashMap();
	 * map.put("name","test"); map.put("phone","123123"); for (int i = 0; i < 2;
	 * i++) { Map message = new HashMap(); message.put("name", "test1");
	 * message.put("content", "time:" + new Date()); list.add(message); }
	 * map.put("messages", list); Map message = new HashMap();
	 * message.put("name", "test1"); message.put("content", "time:" + new
	 * Date()); map.put("message", message); User newUser = new User(); try {
	 * objectHelper.syncObjectGraph(newUser,map); newUser.getMessages(); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

}
