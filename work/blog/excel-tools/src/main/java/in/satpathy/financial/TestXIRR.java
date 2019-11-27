/*
 *  TestXIRR.java
 *  Copyright (C) 2005 Gautam Satpathy
 *  gautam@satpathy.in
 *  www.satpathy.in
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package in.satpathy.financial;

/*
 *  Imports
 */
import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author : gsatpath
 * @version : 1.0.0 Date: Oct 20, 2005, Time: 4:39:25 AM
 */
public class TestXIRR {

	public static  void testX() throws ParseException {
		double[]    values  = new double[142] ;
		double[]    dates   = new double[142] ;
		String []  dataStr = {"2023-10-25", "2023-12-31", "2024-3-31", "2024-6-30", "2024-9-30", "2024-12-31", "2025-3-31", "2025-6-30", "2025-9-30", "2025-12-31", "2026-3-31", "2026-6-30", "2026-9-30", "2026-12-31", "2027-3-31", "2027-6-30", "2027-9-30", "2027-12-31", "2028-3-31", "2028-6-30", "2028-9-30", "2028-12-31", "2029-3-31", "2029-6-30", "2029-9-30", "2029-12-31", "2030-3-31", "2030-6-30", "2030-9-30", "2030-12-31", "2031-3-31", "2031-6-30", "2031-9-30", "2031-12-31", "2032-3-31", "2032-6-30", "2032-9-30", "2032-12-31", "2033-3-31", "2033-6-30", "2033-9-30", "2033-12-31", "2034-3-31", "2034-6-30", "2034-9-30", "2034-12-31", "2035-3-31", "2035-6-30", "2035-9-30", "2035-12-31", "2036-3-31", "2036-6-30", "2036-9-30", "2036-12-31", "2037-3-31", "2037-6-30", "2037-9-30", "2037-12-31", "2038-3-31", "2038-6-30", "2038-9-30", "2038-12-31", "2039-3-31", "2039-6-30", "2039-9-30", "2039-12-31", "2040-3-31", "2040-6-30", "2040-9-30", "2040-12-31", "2041-3-31", "2041-6-30", "2041-9-30", "2041-12-31", "2042-3-31", "2042-6-30", "2042-9-30", "2042-12-31", "2043-3-31", "2043-6-30", "2043-9-30", "2043-12-31", "2044-3-31", "2044-6-30", "2044-9-30", "2044-12-31", "2045-3-31", "2045-6-30", "2045-9-30", "2045-12-31", "2046-3-31", "2046-6-30", "2046-9-30", "2046-12-31", "2047-3-31", "2047-6-30", "2047-9-30", "2047-12-31", "2048-3-31", "2048-6-30", "2048-9-30", "2048-12-31", "2049-3-31", "2049-6-30", "2049-9-30", "2049-12-31", "2050-3-31", "2050-6-30", "2050-9-30", "2050-12-31", "2051-3-31", "2051-6-30", "2051-9-30", "2051-12-31", "2052-3-31", "2052-6-30", "2052-9-30", "2052-12-31", "2053-3-31", "2053-6-30", "2053-9-30", "2053-12-31", "2054-3-31", "2054-6-30", "2054-9-30", "2054-12-31", "2055-3-31", "2055-6-30", "2055-9-30", "2055-12-31", "2056-3-31", "2056-6-30", "2056-9-30", "2056-12-31", "2057-3-31", "2057-6-30", "2057-9-30", "2057-12-31", "2058-3-31", "2058-6-30", "2058-9-30", "2058-12-31" };
		double [] acValues = {2214.64, 53.66, 24.81, 22.66, 1040.63, 22.14, 45.45, 42.82, 1060.30, 41.88, 12.45, 9.38, 1026.43,	-73.23,	-158.29,	-162.95, 852.48,	-168.64,	-145.75,	-150.43, 864.98,	-156.16,	-132.75,	-137.45, 877.94,	-143.23,	-177.00,	-182.24, 832.61,	-189.10,	-162.38,	-167.67, 850.97,	-174.64,	-270.24,	-276.63, 742.19, 461.29, 552.17, 551.96, 1577.04, 555.39, 523.94, 523.45, 1548.25, 526.32, 557.68, 557.46, 1582.52, 560.86, 528.13, 527.61, 1552.38, 530.42, 496.75, 495.93, 1520.39, 498.13, 531.11, 530.57, 3515.31};
		for (int i = 0; i < values.length; i++ )
			values[i] = 0;
		for (int i=0; i < acValues.length;  i++)
			values[i] = acValues[i];
		for (int i=0; i < values.length; i++){
			Date date = DateFormat.getDateFormatter().parse(dataStr[i]);
			Calendar cc = Calendar.getInstance();
			cc.setTime(date);
			dates[i] = XIRRData.getExcelDateValue(cc);
		}
		XIRRData data       = new XIRRData( 142, 0.00000000000000000001, values, dates ) ;
		double xirrValue = XIRR.xirr( data ) ;
		System.out.println(xirrValue);
	}
	/**
	 *
	 *  @param args
	 */
	public static void main( String[] args ) {
		try {
			testX();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log( "Testing XIRR..." ) ;

//		GregorianCalendar dateStart = new GregorianCalendar( 1899, 11, 30 ) ;
		GregorianCalendar dateEnd = new GregorianCalendar( 2005, 9, 20 ) ;
        int daysBetween = XIRRData.getExcelDateValue( dateEnd ) ;
		log( "Days Between = " + daysBetween ) ;

//		"Let us assume that the cells A1:A5 contain the numbers -6000, "
//		"2134, 1422, 1933, and 1422, and the cells B1:B5 contain the "
//		"dates \"1999-01-15\", \"1999-04-04\", \"1999-05-09\", "
//		"\"2000-03-12\", and \"2000-05-1\". Then\n"
//		"XIRR(A1:A5,B1:B5) returns 0.224838. "
		double[]    values  = new double[5] ;
		double[]    dates   = new double[5] ;
		values[0]           = -6000 ;
		values[1]           = 2134 ;
		values[2]           = 1422 ;
		values[3]           = 1933 ;
		values[4]           = 1422 ;
		dates[0]            = XIRRData.getExcelDateValue( new GregorianCalendar(1999, 0, 15) ) ;
		dates[1]            = XIRRData.getExcelDateValue( new GregorianCalendar(1999, 3, 4) ) ;
		dates[2]            = XIRRData.getExcelDateValue( new GregorianCalendar(1999, 4, 9) ) ;
		dates[3]            = XIRRData.getExcelDateValue( new GregorianCalendar(2000, 2, 12) ) ;
		dates[4]            = XIRRData.getExcelDateValue( new GregorianCalendar(2000, 4, 1) ) ;
		XIRRData data       = new XIRRData( 5, 0.3, values, dates ) ;
		double xirrValue = XIRR.xirr( data ) ;
        log( "XIRR = " + xirrValue ) ;

		log( "XIRR Test Completed..." ) ;
	}


	/**
	 *
	 * @param message
	 */
	public static void log( String message ) {
		System.out.println( message ) ;
	}

}   /*  End of the TestXIRR class. */