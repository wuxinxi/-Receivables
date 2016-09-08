package com.wizarpos.drivertest.dao.printer;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.wizarpos.drivertest.exception.PrinterException;
import com.wizarpos.drivertest.jniinterface.PrinterInterface;

/*
 * 打印机,必须通过getInstance()获取Printer的实例.
 * 注意: 在最后必须调用flush确认打印
 */
public class Printer {
	private final static String TAG = "Printer";

	private final static Printer printer = new Printer();
	private boolean isInit = false;// 打印机是否初始化标志,默认未初始化
	public static int DEFAULT_PRINT_VALUE = 1024 * 4;// 当缓冲的数据大于4K时输出缓冲区
	private ByteArrayOutputStream bos;// 缓冲用户需要打印的数据

	// 打开打印机
	// 注:打开打印机并不会初始化打印机，所以在进行打印之前需要调用init()先初始化打印机
	public void open() throws PrinterException {

		if (PrinterInterface.PrinterOpen() < 0
				|| PrinterInterface.PrinterBegin() < 0) {
			throw new PrinterException("打印机打开失败！");
		}
	}

	// 关闭打印机
	public void close() throws PrinterException {
		if (PrinterInterface.PrinterEnd() < 0
				|| PrinterInterface.PrinterClose() < 0) {
			throw new PrinterException("打印机关闭失败！");
		}
		// 设置打印机初始化标志为false
		isInit = false;
	}

	public String probe() {
		String str = "";
		PrinterInterface.PrinterOpen();
		int value = PrinterInterface.PrinterProbe();
		PrinterInterface.PrinterBegin();

		if (value == 0) {
			str = "波特率为：9600";
			System.out.println("value=" + value + "------波特率为：9600");
		} else if (value == 1) {
			str = "波特率为：115200";
			System.out.println("value=" + value + "------波特率为：115200");
		}

		PrinterInterface.PrinterEnd();
		PrinterInterface.PrinterClose();
		return str;
	}

	// 初始化打印机,该方法只需调用一次
	public void init() throws PrinterException {
		byte[] bt = PrinterCommand.getCmdEsc_();
		if (PrinterInterface.PrinterWrite(bt, bt.length) < 0) {
			throw new PrinterException("初始化打印机失败！");
		}

		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}

		isInit = true;// 设置打印机已初始化标志位
	}

	// 向前走纸一行,该效果和调用println(1)一样
	public void println() throws PrinterException {
		print(PrinterCommand.getCmdLf());
	}

	// 向前走纸n行
	public void println(int n) throws PrinterException {
		print(PrinterCommand.getCmdEscDN(n));
	}

	/**
	 * 将需要打印的信息放入缓冲区中
	 *
	 * @param cnt
	 * @throws PrinterException
	 */
	public void println(String cnt) throws PrinterException {
		print(cnt);
	}

	// 打印自检
	public void printTestSelf() throws PrinterException {
		print(PrinterCommand.printSelf());

		// return this;

	}

	// 跳到下一个制表位,默认值为8个空格，只对当前行有效
	public Printer jumpNextTab() throws PrinterException {
		print(PrinterCommand.getCmdHt());

		return this;

	}

	// 向前走纸 n 点行
	public Printer jumpNBlankSpace(int n) throws PrinterException {
		print(PrinterCommand.getCmdEscJN(n));

		return this;
	}

	// 左对齐
	public Printer leftAligment() throws PrinterException {
		print(PrinterCommand.getCmdEscAN(0));

		return this;
	}

	// 居中对齐
	public Printer middleAligment() throws PrinterException {
		print(PrinterCommand.getCmdEscAN(1));

		return this;
	}

	// 右对齐
	public Printer rightAligment() throws PrinterException {
		print(PrinterCommand.getCmdEscAN(2));

		return this;
	}

	// 设置字符上下颠倒
	public Printer fontUpsideDown() throws PrinterException {
		print(PrinterCommand.getCmdEsc__N(1));

		return this;
	}

	// 设置字体反白
	public Printer fontInverse() throws PrinterException {
		print(PrinterCommand.getCmdGsBN(1));

		return this;
	}

	public static final int UNDERLINE_HEIGHT_0 = 0;
	public static final int UNDERLINE_HEIGHT_1 = 1;
	public static final int UNDERLINE_HEIGHT_2 = 2;

	/**
	 * 设置下划线高度，如果取消下划线请使用UNDERLINE_HEIGHT_0值
	 * 注:调用restoreDefaultFontStyle()并不会取消已经设置的下划线
	 *
	 * @param n
	 *            有效值 0-2
	 * @return
	 * @throws PrinterException
	 */
	public Printer fontUnderlineHeight(int n) throws PrinterException {
		print(PrinterCommand.getCmdEsc___N(n));

		return this;
	}

	// 恢复默认字体默认样式
	public Printer restoreDefaultFontStyle() throws PrinterException {
		print(PrinterCommand.getCmdEsc_N(0));

		return this;
	}

	// 双倍放大字体高度
	public Printer amplificationFontHeight() throws PrinterException {
		print(PrinterCommand.getCmdEsc_N(0x10));

		return this;
	}

	// 双倍放大字体宽度
	public Printer amplificationFontWidth() throws PrinterException {
		print(PrinterCommand.getCmdEsc_N(0x20));

		return this;
	}

	/**
	 * 同时双倍放大字体的宽度和高度
	 * 注:如果调用this.amplificationFontHeight().amplificationFontWidth()来同时
	 * 放大字体的宽度和高度是无效的。
	 *
	 * @return this
	 * @throws PrinterException
	 */
	public Printer amplificationFontWidthAndHeight() throws PrinterException {
		print(PrinterCommand.getCmdEsc_N(0x30));

		return this;
	}

	// 加粗字体
	public Printer boldFont() throws PrinterException {
		print(PrinterCommand.getCmdEscEN(1));

		return this;
	}

	// 返回一个单例
	public static Printer getInstance() {
		return printer;
	}

	private void print(byte[] ctrlByte) throws PrinterException {
		bufferValidation();
		bos.write(ctrlByte, 0, ctrlByte.length);
	}

	public final static String GB2312 = "GB2312";

	private void print(String msg) throws PrinterException {
		bufferValidation();
		try {
			byte[] bt = msg.getBytes(GB2312);
			bos.write(bt, 0, bt.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送缓冲区中的数据发送到打印机，并清空
	 *
	 * @throws PrinterException
	 */
	public void flush() throws PrinterException {
		isInitPrint();
		byte[] buffer = bos.toByteArray();
		PrinterInterface.PrinterWrite(buffer, buffer.length);
		bos.reset();
	}

	// 如果缓冲的数据大于4K，则先输出缓冲数据并清空缓冲区
	private void bufferValidation() throws PrinterException {
		isInitPrint();
		byte[] bufData = bos.toByteArray();
		if (bufData.length > DEFAULT_PRINT_VALUE) {
			flush();
		}
	}

	private void isInitPrint() throws PrinterException {
		if (!isInit) {
			Log.e(TAG, "打印机未调用init()初始化");
			throw new PrinterException("打印机未初始化");
		}
	}
}
