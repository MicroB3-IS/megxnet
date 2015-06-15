package net.megx.ui.table.demo;

import java.util.ArrayList;
import java.util.List;

public class MyDataProvider {
	private static final int DATA_SIZE = 50;

	public static List<MyData> getDataRows() {
		List<MyData> ls = new ArrayList<MyData>();
		for(int i=0; i<DATA_SIZE; i++) {
			MyData obj = new MyData();
			obj.setId(i+1);
			obj.setName("name " + i);
			obj.setDescription("description bla blaa blad fs " + i);
			ls.add(obj);
		}
		return ls;
	}
}
