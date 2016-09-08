package com.tangren.utlis;

import java.text.SimpleDateFormat;

import com.wizarpos.drivertest.dao.printer.Printer;

public class MyUtil {

	static {
		// System.loadLibrary("wizarposHAL");
		System.loadLibrary("wizarpos_printer");
	}

	public void print(String Name, String Cashier, String Deduction,
					   String Payment, String Givechange, String Serialnumber) {

		Printer p = new Printer();
		try {
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String date = sDateFormat.format(new java.util.Date());

			p.open();
			p.init();
			p.println(1);// 锟斤拷印2锟斤拷锟斤拷
			p.middleAligment().boldFont().restoreDefaultFontStyle()
					.println("现金消费");
			p.println(1);
			p.middleAligment().restoreDefaultFontStyle()
					.println("--------------------------------");
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("商户名称: " + Name);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("收银: " + Cashier);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("扣减: " + Deduction);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("支付: " + Payment);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("找零: " + Givechange);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("流水号: " + Serialnumber);
			p.println(1);
			p.restoreDefaultFontStyle().leftAligment()
					.println("时间: " + date);
			p.println(1);
			p.middleAligment().restoreDefaultFontStyle()
					.println("--------------------------------");
			p.flush();
			p.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	};

}
