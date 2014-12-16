/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 

 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package net.megx.ws.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	final private static int minute = 1000 * 60;
	final private static int hour = minute * 60;
	final private static int day = hour * 24;
	final private static float month = day * 30.4368499f;
	final private static float quarter = month * 3;

	/*
	  'microseconds',
	  'milliseconds',
	  'second',
	  'minute',
	  'hour',
	  'day',
	  'week',
	  'month',
	  'quarter',
	  'year',
	  'decade',
	  'century',
	  'millennium'
	 */

	public static String getDateRes(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		//SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(f.format(c1.getTime()));
		//System.out.println(f.format(c2.getTime())); 

		long intervalMs = c2.getTimeInMillis() - c1.getTimeInMillis();
		int years = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		if (!d2.before(d1)) {
 
			if (years > 1000) {
				return "millennium";
			} else {
				if (years > 100) {
					return "century";
				} else {
					if (years > 10) {
						return "decade";
					} else {
						if (intervalMs/(month*12) > 1 ) {
							return "year";
						} else {
							if ((intervalMs / quarter) > 1) {
								return "quarter";
							} else {
								if ((intervalMs / month) > 1) {
									return "month";
								} else {
									if ((intervalMs / day) > 7) {
										return "week";
									} else {
										if ((intervalMs / day) > 1) {
											return "day";
										} else {
											if ((intervalMs / hour) > 1) {
												return "hour";
											} else {
												if ((intervalMs / minute) > 1) {
													return "minute";
												} else {
													if (intervalMs > 1000) {
														return "second";
													} else {
														if (intervalMs / 1 > 1) {
															return "milliseconds";
														} else {
															if (intervalMs < 1) {
																return "microseconds";
															} else {
																return "wrong dates";
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

			}
		} else {
			return "wrong dates";
		}

	}

	public static String getFormatedDate(Date datStart, String dateRes) {
		String returnString = null;
		if ("year".equalsIgnoreCase(dateRes)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			returnString = sdf.format(datStart);
		} else if ("month".equalsIgnoreCase(dateRes)
				|| "quarter".equalsIgnoreCase(dateRes)
				|| "week".equalsIgnoreCase(dateRes)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			returnString = sdf.format(datStart);
		} else if ("day".equalsIgnoreCase(dateRes)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			returnString = sdf.format(datStart);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			returnString = sdf.format(datStart);
		}

		return returnString;
	}
	/*
	 * public static String getDateRes(Date startDate, Date stopDate) { Calendar
	 * cal1 = Calendar.getInstance(); cal1.setTime(startDate); Calendar cal2 =
	 * Calendar.getInstance(); cal2.setTime(stopDate);
	 * 
	 * int sY = startDate.getYear(); int eY = stopDate.getYear(); int sM =
	 * startDate.getMonth(); int eM = stopDate.getMonth(); int sD =
	 * startDate.getDay(); int eD = stopDate.getDay(); int sH =
	 * startDate.getHours(); int eH = stopDate.getHours(); int sMin =
	 * startDate.getMinutes(); int eMin = stopDate.getMinutes(); int sSec =
	 * startDate.getSeconds(); int eSec = stopDate.getSeconds(); if (sY != eY) {
	 * if (eY - sY >= 1000) { return "millennium"; } else if (eY - sY >= 100) {
	 * return "century"; } else if (eY - sY >= 10) { return "decade"; } else if
	 * (eY - sY > 1) { return "year"; } else { long monthDif =
	 * monthsBetween(cal1, cal2); if (monthDif > 3) { return "year"; } else if
	 * (monthDif == 3) { return "quarter"; } else if (monthDif >= 1) { return
	 * "month"; } else { long hourDif = hoursBetween(cal1, cal2); if (hourDif >=
	 * 7 * 24) { return "week"; } else if (hourDif >= 24) { return "day"; } else
	 * if (hourDif >= 1) { return "hour"; } else { long secondsDif =
	 * secondsBetween(cal1, cal2); if (secondsDif >= 60 * 60) { return "hour"; }
	 * else if (secondsDif >= 60) { return "minute"; } else if (secondsDif >= 1)
	 * { return "second"; } else { long miliSecondsDif =
	 * miliSecondsBetween(cal1, cal2); if (miliSecondsDif >= 1000) { return
	 * "second"; } else if (miliSecondsDif >= 1) { return "milliseconds"; } else
	 * { return "microseconds"; } } } } } } else if (sM != eM) { long monthDif =
	 * monthsBetween(cal1, cal2); if (monthDif > 3) { return "year"; } else if
	 * (monthDif == 3) { return "quarter"; } else if (monthDif >= 1) { return
	 * "month"; } else { long hourDif = hoursBetween(cal1, cal2); if (hourDif >=
	 * 7 * 24) { return "week"; } else if (hourDif >= 24) { return "day"; } else
	 * if (hourDif >= 1) { return "hour"; } else { long secondsDif =
	 * secondsBetween(cal1, cal2); if (secondsDif >= 60 * 60) { return "hour"; }
	 * else if (secondsDif >= 60) { return "minute"; } else if (secondsDif >= 1)
	 * { return "second"; } else { long miliSecondsDif =
	 * miliSecondsBetween(cal1, cal2); if (miliSecondsDif >= 1000) { return
	 * "second"; } else if (miliSecondsDif >= 1) { return "milliseconds"; } else
	 * { return "microseconds"; } } } } } else if ((sD != eD) || (sH != eH)) {
	 * long hourDif = hoursBetween(cal1, cal2); if (hourDif >= 7 * 24) { return
	 * "week"; } else if (hourDif >= 24) { return "day"; } else if (hourDif >=
	 * 1) { return "hour"; } else { long secondsDif = secondsBetween(cal1,
	 * cal2); if (secondsDif >= 60 * 60) { return "hour"; } else if (secondsDif
	 * >= 60) { return "minute"; } else if (secondsDif >= 1) { return "second";
	 * } else { long miliSecondsDif = miliSecondsBetween(cal1, cal2); if
	 * (miliSecondsDif >= 1000) { return "second"; } else if (miliSecondsDif >=
	 * 1) { return "milliseconds"; } else { return "microseconds"; } } } } else
	 * if ((sMin != eMin) || (sSec != eSec)) { long secondsDif =
	 * secondsBetween(cal1, cal2); if (secondsDif >= 60 * 60) { return "hour"; }
	 * else if (secondsDif >= 60) { return "minute"; } else if (secondsDif >= 1)
	 * { return "second"; } else { long miliSecondsDif =
	 * miliSecondsBetween(cal1, cal2); if (miliSecondsDif >= 1000) { return
	 * "second"; } else if (miliSecondsDif >= 1) { return "milliseconds"; } else
	 * { return "microseconds"; } } } else { long miliSecondsDif =
	 * miliSecondsBetween(cal1, cal2); if (miliSecondsDif >= 1000) { return
	 * "second"; } else if (miliSecondsDif >= 1) { return "milliseconds"; } else
	 * { return "microseconds"; } } }
	 */

	

	/*
	 * public static long monthsBetween(Calendar startDate, Calendar endDate) {
	 * Calendar date = (Calendar) startDate.clone(); long monthsBetween = 0;
	 * while (date.before(endDate)) { date.add(Calendar.MONTH, 1);
	 * monthsBetween++; } return monthsBetween; }
	 * 
	 * public static long hoursBetween(Calendar startDate, Calendar endDate) {
	 * Calendar date = (Calendar) startDate.clone(); long hoursBetween = 0;
	 * while (date.before(endDate)) { date.add(Calendar.HOUR, 1);
	 * hoursBetween++; } return hoursBetween; }
	 * 
	 * public static long secondsBetween(Calendar startDate, Calendar endDate) {
	 * Calendar date = (Calendar) startDate.clone(); long secondsBetween = 0;
	 * while (date.before(endDate)) { date.add(Calendar.SECOND, 1);
	 * secondsBetween++; } return secondsBetween; }
	 * 
	 * public static long miliSecondsBetween(Calendar startDate, Calendar
	 * endDate) { Calendar date = (Calendar) startDate.clone(); long
	 * miliSecondsBetween = 0; while (date.before(endDate)) {
	 * date.add(Calendar.MILLISECOND, 1); miliSecondsBetween++; } return
	 * miliSecondsBetween; }
	 */

}
