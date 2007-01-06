package org.commonfarm.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Extends Apache Commons BeanUtils
 */
public class BeanUtil extends BeanUtils {

	protected static final Log logger = LogFactory.getLog(BeanUtil.class);

	private BeanUtil() {}

	/**
	 *
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 *
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// up
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 * Ignore private, protected
	 * @throws NoSuchFieldException
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * Ignore private, protected
	 *
	 * @throws NoSuchFieldException
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * Ignore private, protected
	 *
	 * @throws NoSuchMethodException
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
			throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// up
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * Get field list by field type
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * Get field type by field name
	 */
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * Get getter method name by field
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * Get getter method by field
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	private static Map classCache = new HashMap();
	public static Class loadClass(String className){
    	return loadClass(className, null);
    }
	
	/** 
     * Loads a class with the URLUtil's classpath.
     * @param className The name of the class to load
     * @param loader The ClassLoader to su
     * @return The requested class
     * @throws ClassNotFoundException
     */
    public static Class loadClass(String className, ClassLoader loader){

        Class theClass;

        //if (loader == null) loader = URLUtil.classpath.getClassLoader();
        if (loader == null) loader = Thread.currentThread().getContextClassLoader();

        try {
            theClass = loader.loadClass(className);
        } catch (Exception e) {
            theClass = (Class) classCache.get(className);
            if (theClass == null) {
                synchronized (BeanUtil.class) {
                    theClass = (Class) classCache.get(className);
                    if (theClass == null) {
                        try {
                            theClass = Class.forName(className);
                        } catch (ClassNotFoundException e1) {
                            throw new InfrastructureException("Class is not found. [" + className + "]");
                        }
                        if (theClass != null) {
                            if (logger.isInfoEnabled()) {
                            	logger.info("Loaded Class: " + theClass.getName());
                            }
                            classCache.put(className, theClass);
                        }
                    }
                }
            }
        }

        return theClass;
    }
    
    /**
     * Get an instance of the given class name.
     * @param className
     * @return
     * @throws NestedRuntimeException
     */
    public static Object getObject(String className) throws NestedRuntimeException{
        Class clazz;
        try {
            clazz = loadClass(className);
            return clazz.newInstance();
        } catch (InstantiationException e) {
            if (logger.isErrorEnabled())
            	logger.error(className + " : Class is cant instantialized.");
            throw new InfrastructureException(className + " : Class is cant instantialized.");
        } catch (IllegalAccessException e) {
            if (logger.isErrorEnabled())
            	logger.error(className + " : Class is not accessed.");
            throw new InfrastructureException(className + " : Class is not accessed.");
        }
    }

    /**
     * @param value
     * @return
     */
    public static boolean isEmpty(Object bean) {
        if (bean == null) return true;
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(bean.getClass());
        
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String propertyName = propertyDescriptors[i].getName();
            Class type = propertyDescriptors[i].getPropertyType();
            if (type.getName().equals("java.lang.String") || 
                    type.getName().equals("java.lang.Integer") || 
                    type.getName().equals("java.lang.Long") || 
                    type.getName().equals("java.lang.Double") || 
                    type.getName().equals("java.util.Date")) {
                try {
                	Object value = PropertyUtils.getProperty(bean, propertyName);
                	if (value != null && !value.equals("")) {
                	    return false;
                	}
                } catch (IllegalAccessException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Class is not accessed.");
                    throw new InfrastructureException(propertyName + " : Class is not accessed.");
                } catch (InvocationTargetException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Invocation error.");
                    throw new InfrastructureException(propertyName + " : Invocation error.");
                } catch (NoSuchMethodException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Class has no such method.");
                    throw new InfrastructureException(propertyName + " : Class has no such method.");
                }
            }
        }    
        return true;
    }

    /**
     * @param destObj
     * @param origObj
     */
    public static void copyProperties(Object destObj, Object origObj) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(destObj.getClass());
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String propertyName = propertyDescriptors[i].getName();
            Class type = propertyDescriptors[i].getPropertyType();
            if ("class".equals(propertyName)) {
                continue; // No point in trying to set an object's class
            }
            
            if (hasProperty(origObj, propertyName)) {
                Object origValue;
                try {
                    origValue = PropertyUtils.getProperty(origObj, propertyName);
                } catch (IllegalAccessException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Class is not accessed.");
                    throw new InfrastructureException(propertyName + " : Class is not accessed.");
                } catch (InvocationTargetException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Invocation error.");
                    throw new InfrastructureException(propertyName + " : Invocation error.");
                } catch (NoSuchMethodException e) {
                    if (logger.isErrorEnabled())
                    	logger.error(propertyName + " : Class has no such method.");
                    throw new InfrastructureException(propertyName + " : Class has no such method.");
                }
                setProperty(destObj, propertyName, origValue, type.getName());
                
            }         
        }
    }
    
    /**
     * @param bean
     * @param propertyName
     * @return
     */
    private static boolean hasProperty(Object bean, String propertyName) {
        try {
            Field field = bean.getClass().getDeclaredField(propertyName);
            if (field != null) {
                return true;
            }
        } catch (SecurityException e) {
            if (logger.isErrorEnabled())
            	logger.error(propertyName + " : Class is not Security.");
            throw new InfrastructureException(propertyName + " : Class is not Security.");
        } catch (NoSuchFieldException e) {
            return false;
        }
        return false;
    }
    
    /**
     * get object from original object by property name
     * @param object
     * @param name
     * @return
     */
    public static Object getObjectProperty(Object object, String name) {
        try {
            return PropertyUtils.getProperty(object, name);
        } catch (IllegalAccessException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class is not accessed.");
            throw new InfrastructureException(name + " : Class is not accessed.");
        } catch (InvocationTargetException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Invocation error.");
            throw new InfrastructureException(name + " : Invocation error.");
        } catch (NoSuchMethodException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class has no such method.");
            throw new InfrastructureException(" Class has no such method for property: "+name+".");
        }
    }
    /**
     * set property by property type
     * @param object
     * @param name
     * @param value
     * @param type
     */
    public static void setProperty(Object object, String name, Object value, String type) {
    	String origType = null;
    	if (value != null) {
    		origType = value.getClass().getName();
    		if (origType.indexOf("Date") != -1) {
    			value = DateUtil.convert((Date) value);
    		}
    		if (origType.indexOf("Timestamp") != -1) {
    			value = DateUtil.convert((Timestamp) value);
    		}
    	}
        try {
	        if (type.indexOf("String") != -1 && !isNull(value)) {
	        	PropertyUtils.setProperty(object, name, value.toString()); 
	        } else if (type.indexOf("Long") != -1 && !isNull(value)) {
	        	value = new Long(value.toString());
	        	PropertyUtils.setProperty(object, name, value);
	        } else if (type.indexOf("Integer") != -1 && !isNull(value)) {
	        	value = new Integer(value.toString());
	        	PropertyUtils.setProperty(object, name, value);
	        } else if (type.indexOf("Double") != -1 && !isNull(value)) {
	        	value = new Double(value.toString());
	        	PropertyUtils.setProperty(object, name, value);
	        } else if (type.indexOf("Date") != -1 && !isNull(value)) {
	        	value = DateUtil.convert((String) value);
	        	PropertyUtils.setProperty(object, name, value);
	        }
        } catch (IllegalAccessException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class is not accessed.");
            throw new InfrastructureException(name + " : Class is not accessed.");
        } catch (InvocationTargetException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Invocation error.");
            throw new InfrastructureException(name + " : Invocation error.");
        } catch (NoSuchMethodException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class has no such method.");
            throw new InfrastructureException(name + " : Class has no such method.");
        }
    }
    
    /**
     * 
     * @param object
     * @param name    property name
     * @param value   property value
     */
	public static void setProperty(Object object, String name, Object value) {
		try {
			PropertyUtils.setProperty(object, name, value);
		} catch (IllegalAccessException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class is not accessed.");
            throw new InfrastructureException(name + " : Class is not accessed.");
        } catch (InvocationTargetException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Invocation error.");
            throw new InfrastructureException(name + " : Invocation error.");
        } catch (NoSuchMethodException e) {
            if (logger.isErrorEnabled())
            	logger.error(name + " : Class has no such method.");
            throw new InfrastructureException(name + " : Class has no such method.");
        }
	}

	public static Field getField(Object object, String fieldName) {
		Field field = null;
		try {
			field = object.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if (logger.isErrorEnabled())
				logger.error(fieldName + " : Class has no such Field." + e.toString());
		}
		return field;
	}
	
	public static Method getMethod(Object object, String methodName, Class[] argClasses) {
		Method method = null;
		try {
			method = object.getClass().getDeclaredMethod(methodName, argClasses);
		} catch (SecurityException e) {
			if (logger.isErrorEnabled())
				logger.error(methodName + " : Security!" + e.toString());
		} catch (NoSuchMethodException e) {
			if (logger.isErrorEnabled())
				logger.error(methodName + " : Class has no such method." + e.toString());
		}
		
		return method;
	}
	/**
	 * object is not String
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
	    if (object == null) {
	        return true;
	    }
	    return false;
	}

    /**
     * @param formObject
     * @return
     */
    public static Object getModel(Object origObj) {
        String className = (String) getObjectProperty(origObj, "className");
        Object destObj = getObject(className);
        if (destObj != null) copyProperties(destObj, origObj);
        return destObj;
    }
    /**
     * 
     * @param destObject
     * @param property
     * @param value
     */
	public static void addProperty(Object destObject, String property, Object value) {
		String methodName = "add" + StringUtil.swapFirstLetterCase(property);
		Class[] classes = new Class[]{value.getClass()};
		Method method = getMethod(destObject, methodName, classes);
		try {
			method.invoke(destObject, new Object[]{value});
		} catch (IllegalArgumentException e) {
			if (logger.isErrorEnabled())
				logger.error("Argument is illegal.");
            throw new InfrastructureException("Argument is illegal.");
		} catch (IllegalAccessException e) {
			if (logger.isErrorEnabled())
				logger.error(property + " : Class is not accessed.");
            throw new InfrastructureException(property + " : Class is not accessed.");
        } catch (InvocationTargetException e) {
            if (logger.isErrorEnabled())
            	logger.error(property + " : Invocation error.");
            throw new InfrastructureException(property + " : Invocation error.");
        }
	}

	public static void flushObject(Object obj) {
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(obj.getClass());
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String propertyName = propertyDescriptors[i].getName();
            Class type = propertyDescriptors[i].getPropertyType();
            if ("class".equals(propertyName)) {
                continue; // No point in trying to set an object's class
            }
            if (type.getName().equals("java.lang.String")) {
            	String value = (String) getObjectProperty(obj, propertyName);
            	if (value == null) {
            		setProperty(obj, propertyName, "");
            	} else {
            		setProperty(obj, propertyName, value.trim());
            	}
            } else if (type.getName().equals("java.lang.Integer")) {
            	Integer value = (Integer) getObjectProperty(obj, propertyName);
            	if (value == null) {
            		setProperty(obj, propertyName, new Integer(0));
            	}
            } else if (type.getName().equals("java.lang.Double")) {
            	Double value = (Double) getObjectProperty(obj, propertyName);
            	if (value == null) {
            		setProperty(obj, propertyName, new Double(0));
            	}
            }
        }
	}
}
