/**
 * Copyright 2014  XCL-Charts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */

package com.butao.ulifebiz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;


import org.xclcharts.chart.DialChart;
import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotAttrInfo;
import org.xclcharts.view.GraphicalView;

import java.util.ArrayList;
import java.util.List;

import com.butao.ulifebiz.R;

/**
 * @ClassName DialChart例子
 * @Description  仪表盘例子
 *
 */
public class DialChartView extends GraphicalView {

	private String TAG = "DialChartView";

	private DialChart chart = new DialChart();
	private float mPercentage = 0.6f;
	int color = R.color.colorAccent;
	int bgcolor = R.color.boss_title;
	public DialChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public DialChartView(Context context, AttributeSet attrs){
		super(context, attrs);
		initView();
	}

	public DialChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}


	public void initView()
	{
		chartRender();
	}
	public void setColor(int color){
		this.color = color;
		chart.clearAll();
		chartRender();
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		chart.setChartRange(w ,h );
	}

	public void chartRender()
	{
		try {

			//设置标题背景
			chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor(getResources().getColor(bgcolor) );
//				//绘制边框
//				chart.showRoundBorder();

			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage);

			//设置指针长度
			chart.getPointer().setLength(0.5f);
			chart.setTotalAngle(225);
			chart.setStartAngle(158);
			//增加轴
			addAxis();
			/////////////////////////////////////////////////////////////
			//增加指针
			addPointer();
			//设置附加信息
//				addAttrInfo();
			/////////////////////////////////////////////////////////////

			chart.getPointer().getPointerPaint().setColor(getResources().getColor(color) );
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			chart.getPointer().getBaseCirclePaint().setColor(getResources().getColor(color));
			chart.getPointer().setBaseRadius(10f);
			chart.getPointer().showBaseCircle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}

	}

	public void addAxis()
	{
		List<Float> ringPercentage = new ArrayList<Float>();
		ringPercentage.add( 1f);
		List<Integer> rcolor  = new ArrayList<Integer>();
		rcolor.add(getResources().getColor(color));
		chart.addStrokeRingAxis(0.9f,0.8f, ringPercentage, rcolor);


		List<String> rlabels  = new ArrayList<String>();
		for(int i=0;i<=10;i++)
		{
			if(i%2==0) {
				rlabels.add(Integer.toString(i));
			}
		}
		chart.addInnerTicksAxis(0.7f, rlabels);
		chart.getPlotAxis().get(0).getFillAxisPaint().setColor(getResources().getColor(bgcolor));
		chart.getPlotAxis().get(1).getTickLabelPaint().setColor(Color.WHITE);
		chart.getPlotAxis().get(1).getTickMarksPaint().setColor(Color.TRANSPARENT);
		chart.getPlotAxis().get(1).getTickLabelPaint().setTextSize(20);
		chart.getPlotAxis().get(1).hideAxisLine();
		chart.getPlotAxis().get(1).setDetailModeSteps(3);
	}

	//增加指针
	public void addPointer()
	{

	}


	private void addAttrInfo()
	{
		/////////////////////////////////////////////////////////////
		PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
		//设置附加信息
		Paint paintTB = new Paint();
		paintTB.setColor(Color.WHITE);
		paintTB.setTextAlign(Align.CENTER);
		paintTB.setTextSize(30);
		paintTB.setAntiAlias(true);
//			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, "当前网速", 0.3f, paintTB);

		Paint paintBT = new Paint();
		paintBT.setColor(Color.WHITE);
		paintBT.setTextAlign(Align.CENTER);
		paintBT.setTextSize(35);
		paintBT.setFakeBoldText(true);
		paintBT.setAntiAlias(true);
		plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM,
				Float.toString(MathHelper.getInstance().round(mPercentage * 100,2)), 0.3f, paintBT);

		Paint paintBT2 = new Paint();
		paintBT2.setColor(Color.WHITE);
		paintBT2.setTextAlign(Align.CENTER);
		paintBT2.setTextSize(30);
		paintBT2.setFakeBoldText(true);
		paintBT2.setAntiAlias(true);
		plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "MB/S", 0.4f, paintBT2);
	}

	public void setCurrentStatus(float percentage)
	{
		mPercentage =  percentage;
		chart.clearAll();

		//设置当前百分比
		chart.getPointer().setPercentage(mPercentage);
		addAxis();
		//增加指针
		addPointer();
//		addAttrInfo();
	}


	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		try{
			chart.render(canvas);
		} catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

}