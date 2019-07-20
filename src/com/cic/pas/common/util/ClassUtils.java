package com.cic.pas.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.cic.pas.common.classLoader.ClassLoad;

/**
 * Class工具类
 * 
 * @author Eric
 */
public class ClassUtils {

	/**
	 * "set"
	 */
	public static final String SET = "set";

	/**
	 * "get"
	 */
	public static final String GET = "get";

	// ------------------------------------------------//

	/**
	 * 判断对象是否为空
	 * 
	 * @param object
	 *            对象
	 * @return 为空-true 否则-false
	 */
	public static boolean isNull(Object object) {

		return object == null ? true : false;
	}

	/**
	 * 对象为空时抛出运行时异常
	 * 
	 * @param object
	 *            对象
	 * @param message
	 *            异常消息
	 */
	public static void isNullToRuntimeException(Object object, String message) {

		if (isNull(object))
			throw new RuntimeException(message);
	}

	// ------------------------------------------------//

	/**
	 * 根据类全名称获取该类Class对象
	 * 首先使用系统默认加载器加载，如果没有找到，则使用自定义加载器加载目录外部jar
	 * @param className 类全名称
	 * @param url 外部目录
	 * @param isFile 是否为文件
	 * @return Class对象
	 */
	public static Class getClass(String className, String url, boolean isFile){
		Class clazz = null;

		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			try {
				clazz = ClassLoad.getURLClassLoader(url, isFile).loadClass(className);
			} catch (ClassNotFoundException e1) {
				throw new RuntimeException(e1);
			}
		}
		return clazz;
	}
	
	/**
	 * 根据类全名称获取该类Class对象
	 * 首先使用系统默认加载器加载，如果没有找到，则使用自定义加载器加载目录外部jar
	 * 默认为文件夹
	 * @param className 类全名称
	 * @param url 外部目录
	 * @return Class对象
	 */
	public static Class getClass(String className, String url){
		Class clazz = null;

		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			try {
				clazz = ClassLoad.getURLClassLoader(url).loadClass(className);
			} catch (ClassNotFoundException e1) {
				throw new RuntimeException(e1);
			}
		}
		return clazz;
	}
	
	/**
	 * 根据类全名称获取该类Class对象
	 * 首先使用系统默认加载器加载，如果没有找到，则使用自定义加载器加载默认目录外部jar，目录在config.xml中配置，key : defaultLoadClassPath
	 * @param className 类全名称
	 * @param isFile 是否是文件
	 * @return Class对象
	 */
	public static Class getClass(String className, boolean isFile){
		Class clazz = null;

		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			try {
				clazz = ClassLoad.getURLClassLoader(isFile).loadClass(className);
			} catch (ClassNotFoundException e1) {
				throw new RuntimeException(e1);
			}
		}
		return clazz;
	}
	
	/**
	 * 根据类全名称获取该类Class对象
	 * 首先使用系统默认加载器加载，如果没有找到，则使用自定义加载器加载默认目录外部jar，目录在config.xml中配置，key : defaultLoadClassPath
	 * 默认目录为文件夹
	 * @param className 类全名称：包.类名
	 * @return 该类Class对象
	 */
	public static Class getClass(String className) {

		Class clazz = null;

		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			try {
				clazz = ClassLoad.getURLClassLoader().loadClass(className);
			} catch (ClassNotFoundException e1) {
				throw new RuntimeException(e1);
			}
		}
		return clazz;
	}

	/**
	 * 获取对象Class对象
	 * 
	 * @param object
	 *            对象
	 * @return 对象Class对象
	 */
	public static Class getClass(Object object) {
		isNullToRuntimeException(object, "getClass : object is null");
		return object.getClass();
	}
	// ------------------------------------------------//

	/**
	 * 获取对象类全名称
	 * 
	 * @param obj
	 *            对象
	 * @return 类全名称：包.类名
	 */
	public static String getClassName(Object obj) {

		return obj.getClass().getName();
	}

	/**
	 * 获取类全名称
	 * @param clazz
	 * @return
	 */
	public static String getClassName(Class clazz) {

		return clazz.getName();
	}

	/**
	 * 获取对象类短名称
	 * 
	 * @param obj
	 *            对象
	 * @return 类短名称：类名
	 */
	public static String getSimpleClassName(Object obj) {

		return obj.getClass().getSimpleName();
	}

	/**
	 * 获取类短名称
	 * @param clazz
	 * @return
	 */
	public static String getSimpleClassName(Class clazz) {

		return clazz.getSimpleName();
	}

	// ------------------------------------------------//

	/**
	 * 获取正在使用的方法的方法名
	 * 
	 * @param thread
	 *            正在使用的线程 Thread.currentThread()
	 * @return 正在使用的方法的方法名
	 */
	public static String getCurrentMethodName(Thread thread) {

		return thread.getStackTrace()[2].getMethodName();
	}

	/**
	 * 获取方法返回值类型
	 * 
	 * @param method
	 *            方法对象
	 * @return 返回值类型
	 */
	public static Class getRuntionType(Method method) {

		return method.getReturnType();
	}

	/**
	 * 获取方法返回值类型的Type对象
	 * 
	 * @param method
	 *            方法对象
	 * @return 方法返回值类型的Type对象
	 */
	public static Type getGenericReturnType(Method method) {

		return method.getGenericReturnType();
	}

	// ------------------------------------------------//

	/**
	 * 获取属性泛型
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性
	 * @return 泛型
	 */
	public static Type[] getGenericAll(Object object, String property) {

		if (property.contains(".")) {

			String beforeProp = property.substring(0, property.lastIndexOf("."));
			String afterProp = property.substring(property.lastIndexOf(".") + 1);

			Object obj = getPropertyValue(object, beforeProp);
			property = afterProp;
		}

		Method method = getGetterMethod(object, property);
		Type type = getGenericReturnType(method);
		return ((ParameterizedType) type).getActualTypeArguments();
	}

	/**
	 * 获取单一属性泛型
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性
	 * @return 泛型
	 */
	public static Type getGenericSingle(Object object, String property) {

		Type[] types = getGenericAll(object, property);
		return types.length > 0 ? types[0] : null;
	}

	// ------------------------------------------------//

	/**
	 * 判断某类是否实现某接口
	 * 
	 * @param c
	 *            类
	 * @param szInterface
	 *            接口
	 * @return 结果
	 */
	public static boolean isInterface(Class c, String szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getName().equals(szInterface)) {
						return true;
					} else if (isInterface(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return isInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}

	// ------------------------------------------------//

	/**
	 * 创建类实例化对象
	 * 
	 * @param clazz
	 *            类对象
	 * @return 类实例化对象
	 */
	public static Object newInstance(Class clazz) {

		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return object;
	}

	/**
	 * 创建类实例对象
	 * 
	 * @param className
	 *            类全名称
	 * @param parameterTypes
	 *            构造方法参数类型列表
	 * @param initargs
	 *            构造方法参数列表
	 * @return 类实例对象
	 */
	public static Object newInstance(Class clazz, Class[] parameterTypes, Object[] initargs) {

		Object object = null;
		try {
			object = clazz.getConstructor(parameterTypes).newInstance(initargs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return object;
	}

	/**
	 * 创建类实例对象，有无参构造方法时使用
	 * 
	 * @param className
	 *            类全名称
	 * @return 类实例对象
	 */
	public static Object newInstance(String className) {
		return newInstance(getClass(className));
	}

	/**
	 * 创建类实例对象
	 * 
	 * @param className
	 *            类全名称
	 * @param parameterTypes
	 *            构造方法参数类型列表
	 * @param initargs
	 *            构造方法参数列表
	 * @return 类实例对象
	 */
	public static Object newInstance(String className, Class[] parameterTypes, Object[] initargs) {

		return newInstance(getClass(className), parameterTypes, initargs);
	}

	// ------------------------------------------------//

	/**
	 * 满足JavaBean规范，返回属性Setter方法名
	 * 
	 * @param property
	 *            属性名
	 * @return 属性Setter方法名
	 */
	public static String getSetterMethedName(String property) {

		if (StringUtils.isNullOrEmpty(property))
			return property;
		return SET + StringUtils.toPascal(property);
	}

	/**
	 * 满足JavaBean规范，返回属性Getter方法名
	 * 
	 * @param property
	 *            属性名
	 * @return 属性Getter方法名
	 */
	public static String getGetterMethedName(String property) {

		if (StringUtils.isNullOrEmpty(property))
			return property;
		return GET + StringUtils.toPascal(property);
	}

	// ------------------------------------------------//

	/**
	 * 获得某类的某方法对象
	 * 
	 * @param clazz
	 *            该类Class对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型列表
	 * @return 方法对象
	 */
	public static Method getMethodFromClass(Class clazz, String methodName, Class... parameterTypes) {

		isNullToRuntimeException(clazz, "getMethodFromClass: Class is null");
		isNullToRuntimeException(methodName, "getMethodFromClass: MethodName is null");

		Method method = null;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return method;
	}

	/**
	 * 获取某对象的某方法对象
	 * 
	 * @param object
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型列表
	 * @return 方法对象
	 */
	public static Method getMethodFromObject(Object object, String methodName, Class... parameterTypes) {

		isNullToRuntimeException(object, "getMethodFromObject: Object is null");
		return getMethodFromClass(object.getClass(), methodName, parameterTypes);
	}

	// ------------------------------------------------//

	/**
	 * 调用某对象某方法
	 * 
	 * @param object
	 *            对象
	 * @param method
	 *            方法
	 * @param parameterValues
	 *            方法参数值列表
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object object, Method method, Object... parameterValues) {

		isNullToRuntimeException(object, "invokeMethod: Object is null");
		isNullToRuntimeException(method, "invokeMethod: Method is null");

		Object value = null;
		try {
			value = method.invoke(object, parameterValues);
		}catch (InvocationTargetException e1) {
			// TODO: handle exception
			Throwable targetException = e1.getTargetException();
			targetException.getCause().printStackTrace();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return value;
	}

	/**
	 * 调用某对象某方法
	 * 
	 * @param object
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型列表
	 * @param parameterValues
	 *            方法参数值列表
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object object, String methodName, Class[] parameterTypes, Object[] parameterValues) {

		Method method = getMethodFromObject(object, methodName, parameterTypes);
		return invokeMethod(object, method, parameterValues);
	}

	// ------------------------------------------------//

	/**
	 * 获取得到属性值的方法
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性名
	 * @return 属性值
	 */
	public static Method getGetterMethod(Object object, String property) {

		String methodName = getGetterMethedName(property);
		Method method = getMethodFromObject(object, methodName, null);
		return method;
	}

	/**
	 * 获取设置属性值的方法
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性名
	 * @param parameterType
	 *            参数类型
	 * @param parameterValue
	 *            参数值
	 * @return
	 */
	public static Method getSetterMethod(Object object, String property, Class parameterType, Object parameterValue) {

		String methodName = getSetterMethedName(property);
		Method method = getMethodFromObject(object, methodName, parameterType);
		return method;
	}

	// ------------------------------------------------//

	/**
	 * 满足JavaBean规范，获得属性值
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性名
	 * @return 属性值
	 */
	public static Object getPropertyValue(Object object, String property) {

		isNullToRuntimeException(property, "getPropertyValue: Property is null");

		if (property.contains(".")) {
			Object obj = object;
			for (String prop : property.split("\\.")) {
				obj = getPropertyValue(obj, prop.trim());
			}
			return obj;
		} else {
			Method method = getGetterMethod(object, property);
			return invokeMethod(object, method, null);
		}
	}

	// ------------------------------------------------//

	/**
	 * 满足JavaBean规范，设置属性值
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性名
	 * @param parameterType
	 *            方法参数类型
	 * @param parameterValue
	 *            方法参数值
	 */
	public static void setPropertyValue(Object object, String property, Class parameterType, Object parameterValue) {

		isNullToRuntimeException(property, "setPropertyValue: Property is null");

		if (property.contains(".")) {

			String beforeProp = property.substring(0, property.lastIndexOf("."));
			String afterProp = property.substring(property.lastIndexOf(".") + 1);

			Object obj = getPropertyValue(object, beforeProp);
			setPropertyValue(obj, afterProp, parameterType, parameterValue);
		} else {
			Method method = getSetterMethod(object, property, parameterType, parameterValue);
			invokeMethod(object, method, parameterValue);
		}
	}

	/**
	 * 满足JavaBean规范，设置属性值
	 * 
	 * @param object
	 *            对象
	 * @param property
	 *            属性名
	 * @param parameterValue
	 *            方法参数值
	 */
	public static void setPropertyValue(Object object, String property, Object parameterValue) {

		isNullToRuntimeException(property, "setPropertyValue: Property is null");

		Object obj = null;
		String prop = null;

		if (property.contains(".")) {

			String beforeProp = property.substring(0, property.lastIndexOf("."));
			obj = getPropertyValue(object, beforeProp);
			prop = property.substring(property.lastIndexOf(".") + 1);
		} else {
			obj = object;
			prop = property;
		}

		String methodName = getGetterMethedName(prop);
		Method method = getMethodFromObject(obj, methodName, null);
		Class returnType = getRuntionType(method);
		setPropertyValue(obj, prop, returnType, parameterValue);
	}
}
