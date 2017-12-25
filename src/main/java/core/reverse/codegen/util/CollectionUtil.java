package core.reverse.codegen.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil
{
	public static <T> List<T> copy(List<T> src)
	{
		if(src==null||src.size()==0)
		{
			return null;
		}
		List<T> tar = new ArrayList<T>();
		for(T t:src)
		{
			tar.add(t);
		}
		return tar;
	}
}
