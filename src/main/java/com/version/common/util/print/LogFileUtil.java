package com.version.common.util.print;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class LogFileUtil {
	private static final String ERROR="ERROR";
	private static final String INFO="INFO";
	private static final String RESINFO="RESINFO";
	private static final String REQINFO="REQINFO";
	public static void saveError(Exception e){
		PrintWriter out=null;
		try {
		out = new PrintWriter(new FileOutputStream(ERROR+DateUtil.df_ymd.format(DateUtil.todayZero()),true));
		out.append(DateUtil.df_ymd_hms.format(new Date())+"\n");
		e.printStackTrace(out);
		out.flush();
		} catch (FileNotFoundException ex) {
		}finally{
			out.close();
		}
	}
	public static void saveResInfo(String info){
		PrintWriter out=null;
		try {
		out = new PrintWriter(new FileOutputStream(RESINFO+DateUtil.df_ymd.format(DateUtil.todayZero()),true));
		out.append("\n"+DateUtil.df_ymd_hms.format(new Date())+"_:"+info);
		out.flush();
		} catch (FileNotFoundException e) {
		}finally{
			out.close();
		}
	}
	public static void saveReqInfo(String info){
		PrintWriter out=null;
		try {
		out = new PrintWriter(new FileOutputStream(REQINFO+DateUtil.df_ymd.format(DateUtil.todayZero()),true));
		out.append("\n"+DateUtil.df_ymd_hms.format(new Date())+"_:"+info);
		out.flush();
		} catch (FileNotFoundException e) {
		}finally{
			out.close();
		}
	}
	public static void saveInfo(String info){
		PrintWriter out=null;
		try {
		out = new PrintWriter(new FileOutputStream(INFO+DateUtil.df_ymd.format(DateUtil.todayZero()),true));
		out.append("\n"+DateUtil.df_ymd_hms.format(new Date())+":"+info);
		out.flush();
		} catch (FileNotFoundException e) {
		}finally{
			out.close();
		}
	}
	/*public static void saveInfo(String info){
		PrintWriter out=null;
		try {
		out = new PrintWriter(new FileOutputStream("logs"+File.separator+"com-version-guizhou-erbagang."+DateUtil.df_ymd_log.format(DateUtil.todayZero())+".log",true));
		out.append("\n"+DateUtil.df_ymd_hms.format(new Date())+":"+info);
		out.flush();
		} catch (FileNotFoundException e) {
		}finally{
			out.close();
		}
	}*/
}
