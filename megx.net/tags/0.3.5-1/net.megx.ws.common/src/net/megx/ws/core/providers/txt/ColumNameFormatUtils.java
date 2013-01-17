package net.megx.ws.core.providers.txt;

public class ColumNameFormatUtils {
	
	
	public static String formatStr(String str, ColumnNameFormat format) throws Exception {
		switch (format) {
		case NONE:
			return str;
		case FROM_CAMEL_CASE:
			return hrTextFromCamelCase(str);
			
		default:
			//should not get here, error
			throw new Exception("Unknown format " + format);
		}
	}

	private static String hrTextFromCamelCase(String str) {
		StringBuffer sb = new StringBuffer();
		Character p = 0;
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(i==0) {
				sb.append(Character.toUpperCase(c));
			} else {
				if (Character.isUpperCase(c) && Character.isLowerCase(p)) {
					sb.append(' ');
				}
				sb.append(c);
			}
			p = c;
		}
		return sb.toString();
	}
}
