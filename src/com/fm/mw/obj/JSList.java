package com.fm.mw.obj;

import java.util.Iterator;
import java.util.List;

import com.fm.mw.JSON;

public class JSList implements JSON{
	List<JSON> list;
	
	public JSList() {
	}
	
	public JSList(List<JSON> l){
		list = l;
	}
	
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		boolean isFirst = true;
		sb.append("[");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			JSON json = (JSON) iterator.next();
			if (!isFirst)
				sb.append(" , ");
			sb.append(json.toJSON());
			isFirst = false;
		}
		sb.append("]");
		return sb.toString();
	}

	public List<JSON> getList() {
		return list;
	}

	public void setList(List<JSON> list) {
		this.list = list;
	}
	

}
