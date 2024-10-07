package ex03;

import java.util.HashMap;

import java.util.Map;

class Car{}
class SportCar extends Car{}
class Truck extends Car{}
class Engine{}

class AppContext{
	Map map; // 객체 저장소
	
	public AppContext() {
		map = new HashMap();
		map.put("car", new SportCar());
		map.put("engine", new Engine());
	}
	
	Object getBean(String key) {
		ClassLoader classLoader = AppContext.class.getClassLoader();
		ClassPath classpath = ClassPath.from(classLoader);
		return map.get(key);
	}
}


public class Main {
	public static void main(String[] args)  {
		AppContext ac = new AppContext();
		
		Car car = (Car)ac.getBean("car");
		System.out.println("car = " +car);
		Engine engine = (Engine)ac.getBean("engine");
		System.out.println("engine = "+ engine);
		
	}
	

}
