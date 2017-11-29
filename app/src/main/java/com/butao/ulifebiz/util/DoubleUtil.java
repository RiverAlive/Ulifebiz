package com.butao.ulifebiz.util;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by bodong on 2016/7/21.
 */
public class DoubleUtil {
    public static String KeepTwoDecimal(String data) {//保留两位小数点，有小数点显示，没有，则隐藏
        NumberFormat df = NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false);
        df.setRoundingMode(RoundingMode.FLOOR);
        String money = df.format(Double.valueOf(data));
        return money;
    }

    public static int KeepInteger(float data) {//不保留小数点，四舍五入
        NumberFormat df = NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(0);
        String money = df.format(Double.valueOf(data));
        return Integer.parseInt(money);
    }

    public static String floatTwo(String data) {//最科学保留两位小数点且四舍五入
        BigDecimal bd = new BigDecimal(data);
        BigDecimal bd_half_even = bd.setScale(2, RoundingMode.HALF_EVEN);//传统保留两位小数点且四舍五入
        BigDecimal bd_half_up = bd.setScale(2, RoundingMode.HALF_UP);//最科学保留两位小数点且四舍五入
        return String.valueOf(bd_half_up);
    }

    public static String OnlyOne(String data) {//不保留小数点，四舍五入
        BigDecimal bd = new BigDecimal(data);
        BigDecimal bd_half_even = bd.setScale(0, RoundingMode.HALF_EVEN);//传统保留两位小数点且四舍五入
        BigDecimal bd_half_up = bd.setScale(0, RoundingMode.HALF_UP);//最科学保留两位小数点且四舍五入
        return String.valueOf(bd_half_up);
    }

    public static String DecimalFormat(String data) {//科学计数转换
        DecimalFormat df = new DecimalFormat("0.00");
        Double d = new Double(data);
        return df.format(d);
    }

    public static float Chu(int num) {//科学计数转换
        //这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
        double percent = (double) num / 10D;
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        //最后格式化并输出
        return Float.parseFloat(nt.format(percent));
    }

    /**
     * 转换成百分比
     *
     * @param old
     * @param now now>old
     * @return
     */
    public static String PercentFormat(String now, String old) {//科学计数转换
        double differ = Double.parseDouble(now) - Double.parseDouble(old);
        double divide = 0;
        if (Double.parseDouble(old) > 0 && Double.parseDouble(now) > 0) {
            if (Double.parseDouble(old) > 0) {
                divide = differ / Double.parseDouble(old);
            } else {
                divide = 1;
            }
        } else {
            if (Double.parseDouble(old) > 0 && Double.parseDouble(now) <= 0) {
                divide = 0;
            } else if (Double.parseDouble(old) <= 0 && Double.parseDouble(now) > 0) {
                divide = 1;
            }
        }
//        double divide = differ/Double.parseDouble(old);
//        DecimalFormat df = new DecimalFormat("000.00%");
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(divide);
    }

    /**
     * 转换成百分比
     *
     * @param old
     * @param now now<old
     * @return
     */
    public static String PercentFormat1(String now, String old) {//科学计数转换
        double differ = Double.parseDouble(old) - Double.parseDouble(now);
        double divide = 0;
        if (Double.parseDouble(old) > 0 && Double.parseDouble(now) > 0) {
            if (Double.parseDouble(now) > 0) {
                divide = (differ / Double.parseDouble(old));
            } else {
                divide = 1;
            }
        } else {
            if (Double.parseDouble(old) > 0 && Double.parseDouble(now) <= 0) {
                divide = 0;
            } else if (Double.parseDouble(old) <= 0 && Double.parseDouble(now) > 0) {
                divide = 1;
            }
        }
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(divide);
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
