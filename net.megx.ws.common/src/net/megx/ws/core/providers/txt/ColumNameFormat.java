package net.megx.ws.core.providers.txt;

public class ColumNameFormat {
	
	
	public static String formatStr(String str, String format) {
		if("camelCase".equals(format)) {
			return formatCamelCase(str);
		}
		//throw unknown format ? 
		return str;
	}

	private static String formatCamelCase(String str) {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(i==0) {
				sb.append(Character.toUpperCase(c));
			} else {
				if (Character.isUpperCase(c)
						&& sb.charAt(sb.length() - 1) != ' ') {
					sb.append(' ');
				}
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
