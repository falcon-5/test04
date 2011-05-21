package com.example.multitouchsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MainView extends View {
	//タッチイベントの状態
	private String touchState;

	//タッチイベントのX,Y座標値
	private int touchX[] = new int[2];
	private int touchY[] = new int[2];

	//タッチされているかどうか
	private boolean isTouched[] = new boolean[2];

	//コンストラクタ
	public MainView(Context context)
	{
		super(context);

		//メンバを初期化
		touchState = "";
		for(int i=0; i<touchX.length; i++)
		{
			touchX[i] = 0;
			touchY[i] = 0;
			isTouched[i] = false;
		}

		//背景色を白色にセット
		setBackgroundColor(Color.rgb(255, 255, 255));
	}

	//描画のためにシステムから呼び出される
	protected void onDraw(Canvas canvas)
	{
		//描画オブジェクトの生成
		Paint p = new Paint();

		//アンチエイリアスを有効に
		p.setAntiAlias(true);

		//文字サイズをセット
		p.setTextSize(32.0f);

		//文字色を黒色にセット
		p.setColor(Color.rgb(0, 0, 0));

		canvas.drawText("touchState:" + touchState, 8, 40, p);

		for(int i=0; i<2; i++)
		{
			String tmp = "(" + touchX[i] + ", " + touchY[i] + ")";
			if(isTouched[i])
				tmp = "Touched" + tmp;
			else
				tmp = "No Touched" + tmp;
			canvas.drawText(tmp, 8, 80 + i*40, p);
		}
	}

	//座標値をセット
	public void setTouchXY(int idx, int x, int y)
	{
		if(idx<touchX.length && idx>=0)
		{
			touchX[idx] = x;
			touchY[idx] = y;
		}
	}

	//タッチ状態をセット
	public void setTouchFlag(int idx, boolean flag)
	{
		if(idx<touchX.length && idx>=0)
			isTouched[idx] = flag;
	}

	//タッチ状態を表す文字列をセット
	public void setTouchState(String state)
	{
		touchState = state;
	}
}
