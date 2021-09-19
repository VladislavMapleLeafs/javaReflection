package info;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import handler.Client;

public class ClassDetail {

	private ClassDetail() {
	}
	
	public static List<String> getInfo(Class clazz){
		List<String> list = new ArrayList();
		String s;
		if (clazz.isAnnotation())
			s = "Annotation ";
		else if(clazz.isInterface())
			s = "Interface ";
		else if(clazz.isEnum())
			s = "Enum ";
		else 
			s = "Class ";
		s += clazz.getName() + " ";
		if (clazz.isArray())
			s += "array ";
		if (clazz.isLocalClass())
			s += "local";
		list.add(s);
		list.add(" ");
		list.addAll(getFieldNames(clazz));
		list.addAll(getMethods(clazz));
		list.addAll(getConstructors(clazz));
		list.addAll(getInterfaces(clazz));  
		return list;
	}
	
	public static List<String> getFieldNames(Class clazz) {
		List<String> list = new ArrayList();
				Field[] publicFields = clazz.getDeclaredFields();
				if(publicFields.length > 0) {
					list.add("Fields:");
					for (int i = 0; i < publicFields.length; i++) {
						try{
							String fieldName = publicFields[i].getName();
							Class typeClass = publicFields[i].getType();
							String fieldType = typeClass.getName();
							list.add("Name: " + fieldName +", Type: " + fieldType);
						}catch(Exception e){
							list.add(" ");
						}
					}
					list.add(" ");
				}	
	    return list;
	}
	
	public static List<String> getMethods(Class clazz) {
		List<String> list = new ArrayList();
		StringBuffer buf = new StringBuffer();
		if(clazz.getDeclaredMethods().length > 0)
			list.add("Methods:");
		for (Method m : clazz.getDeclaredMethods()) {
			Client.modifiers(m.getModifiers(), buf);
			buf.delete(buf.length() - 1, buf.length());
			list.add("Name: " + m.getName() + ", Modifiers: " + buf
			+ ", Return type: " +  m.getReturnType());
			buf.delete(0, buf.length());
		}
		list.add(" ");	
		return list;
	}
	
	public static List<String> getConstructors(Class clazz) {
		List<String> list = new ArrayList();
		StringBuffer buf = new StringBuffer();
		if(clazz.getConstructors().length > 0)
		{
			list.add("Constructors:");
			for (Constructor c : clazz.getConstructors()) {
				Client.modifiers(c.getModifiers(), buf);
				list.add("Name: " + c.getName() + ", Modifiers: " + buf);
				buf.delete(0, buf.length());
			}
			list.add(" ");
		}
		return list;
	}
	
	public static List<String> getInterfaces(Class clazz) {
		List<String> list = new ArrayList();
		StringBuffer buf = new StringBuffer();
		if(clazz.getInterfaces().length > 0)
		{
			list.add("Interfaces:");
			for (Class i : clazz.getInterfaces()) {
				Client.modifiers(i.getModifiers(), buf);
				list.add("Name: " + i.getName() + ", Modifiers: " + buf);
				buf.delete(0, buf.length());
			}
			list.add(" ");
		}
			Class[] InnerClasses = clazz.getDeclaredClasses();
			list.add("Inner Classes: ");
			for (int i = 0; i < InnerClasses.length; i++) {
					list.add("Name: " + InnerClasses[i].getName());
			}
			list.add("\n");
			list.add("Superclass:");
			if(clazz.getSuperclass() != null) {
			Class superclass = clazz.getSuperclass();				
			list.add("Name:" + superclass.getName());
			}
		return list;
	}
	
}
