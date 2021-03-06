package com.tangren.font;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class PixelFormat {

	/**
	 * @author TangRen
	 * @param args
	 * @time 2016-7-5
	 */
	/**
	 * 把dip单位转成px单位
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int formatDipToPx(Context context, int dip) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return (int) Math.ceil(dip * dm.density);
	}

	/**
	 * 把px单位转成dip单位
	 * @param context
	 * @param px
	 * @return
	 */
	public static int formatPxToDip(Context context, int px) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return (int) Math.ceil(((px * 160) / dm.densityDpi));
	}

}