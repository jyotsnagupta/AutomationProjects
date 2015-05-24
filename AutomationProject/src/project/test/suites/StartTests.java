package project.test.suites;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import framework.com.util.*;

public class StartTests 
{
	public static void main(String[] args) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		ComDriverScript obj_driverScript=new ComDriverScript();
		obj_driverScript.comStartTest();
	}

}
