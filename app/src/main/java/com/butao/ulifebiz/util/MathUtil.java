package com.butao.ulifebiz.util;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class MathUtil {
    public static int getMax(int[] arr)
    {
        int max = arr[0];

        for(int x=1; x<arr.length; x++)
        {
            if(arr[x]>max)
                max = arr[x];
        }
        return max;
    }

    public static String  getMax(String[] arr)
    {
        int max = Integer.parseInt(arr[0]);

        for(int x=1; x<arr.length; x++)
        {
            if("Sun".equals(arr[x])){
                arr[x]="7";
            }
            if(Integer.parseInt(arr[x])>max)
                max = Integer.parseInt(arr[x]);
        }
        return max+"";
    }

    public static int getMin(int[] arr)
    {
        int min = 0;
        for(int x=1; x<arr.length; x++)
        {
            if(arr[x]<arr[min])
                min = x;
        }
        return arr[min];
    }

    public static String getMin(String[] arr)
    {
        int min = 0;
        for(int x=1; x<arr.length; x++)
        {
            if("Sun".equals(arr[x])){
                arr[x]="7";
            }
            if(Integer.parseInt(arr[x])<Integer.parseInt(arr[min]))
                min = x;
        }
        return arr[min];
    }

    public static String Week(String[] weeks){
        String max = MathUtil.getMax(weeks);
        String min = MathUtil.getMin(weeks);
        String weektxt = "";
        if(weeks.length==7){
            weektxt="周一至周日";
        }else if(weeks.length==6){
            if(Integer.parseInt(max)==7&&Integer.parseInt(min)==2){
                weektxt="周二至周日";
            }else if(Integer.parseInt(max)==6&&Integer.parseInt(min)==1){
                weektxt="周一至周六";
            }else{
                weektxt =getWeek(weeks);
            }
        }else if(weeks.length==5){
            if(Integer.parseInt(max)==7&&Integer.parseInt(min)==3){
                weektxt="周三至周日";
            }else if(Integer.parseInt(max)==6&&Integer.parseInt(min)==2){
                weektxt="周二至周六";
            }else if(Integer.parseInt(max)==5&&Integer.parseInt(min)==1){
                weektxt="周一至周五";
            }else{
                weektxt =getWeek(weeks);
            }
        }else if(weeks.length==4){
            if(Integer.parseInt(max)==7&&Integer.parseInt(min)==4){
                weektxt="周四至周日";
            }else if(Integer.parseInt(max)==6&&Integer.parseInt(min)==3){
                weektxt="周三至周六";
            }else if(Integer.parseInt(max)==5&&Integer.parseInt(min)==2){
                weektxt="周二至周五";
            }else if(Integer.parseInt(max)==4&&Integer.parseInt(min)==1){
                weektxt="周一至周四";
            }else{
                weektxt =getWeek(weeks);
            }
        }
        else if(weeks.length==3){
            if(Integer.parseInt(max)==7&&Integer.parseInt(min)==5){
                weektxt="周五至周日";
            }else if(Integer.parseInt(max)==6&&Integer.parseInt(min)==4){
                weektxt="周四至周六";
            }else if(Integer.parseInt(max)==5&&Integer.parseInt(min)==3){
                weektxt="周三至周五";
            }else if(Integer.parseInt(max)==4&&Integer.parseInt(min)==2){
                weektxt="周二至周四";
            }else if(Integer.parseInt(max)==3&&Integer.parseInt(min)==1){
                weektxt="周一至周三";
            }else{
                weektxt =getWeek(weeks);
            }
        }else if(weeks.length==2){
            if(Integer.parseInt(max)==7&&Integer.parseInt(min)==6){
                weektxt="周六至周日";
            }else if(Integer.parseInt(max)==6&&Integer.parseInt(min)==5){
                weektxt="周五至周六";
            }else if(Integer.parseInt(max)==5&&Integer.parseInt(min)==4){
                weektxt="周四至周五";
            }else if(Integer.parseInt(max)==4&&Integer.parseInt(min)==3){
                weektxt="周三至周四";
            }else if(Integer.parseInt(max)==3&&Integer.parseInt(min)==2){
                weektxt="周二至周三";
            }else if(Integer.parseInt(max)==2&&Integer.parseInt(min)==1){
                weektxt="周一至周二";
            }else{
                weektxt =getWeek(weeks);
            }
        }else if(weeks.length==1){
            for (int i = 0; i < weeks.length; i++) {
                if (weeks[i].equals("1")) {
                    weektxt = "周一";
                } else if (weeks[i].equals("2")) {
                    weektxt = "周二";
                } else if (weeks[i].equals("3")) {
                    weektxt = "周三";
                } else if (weeks[i].equals("4")) {
                    weektxt = "周四";
                } else if (weeks[i].equals("5")) {
                    weektxt ="周五";
                } else if (weeks[i].equals("6")) {
                    weektxt = "周六";
                } else if (weeks[i].equals("Sun")||weeks[i].equals("7")) {
                    weektxt = "周日";
                }
            }
        }

        return weektxt;
    }
    private static String getWeek(String[] weeks){
        String weektxt = "";
        for (int i = 0; i < weeks.length; i++) {
            if (weeks[i].equals("1")) {
                weektxt =weektxt+ ","+"周一";
            } else if (weeks[i].equals("2")) {
                weektxt =weektxt+ ","+"周二";
            } else if (weeks[i].equals("3")) {
                weektxt =weektxt+","+ "周三";
            } else if (weeks[i].equals("4")) {
                weektxt =weektxt+","+ "周四";
            } else if (weeks[i].equals("5")) {
                weektxt =weektxt+","+ "周五";
            } else if (weeks[i].equals("6")) {
                weektxt =weektxt+","+ "周六";
            } else if (weeks[i].equals("Sun")||weeks[i].equals("7")) {
                weektxt =weektxt+","+ "周日";
            }
        }
        weektxt = weektxt.substring(1,weektxt.length());
        return weektxt;
    }
}
