package com.example.multitouchsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MultiTouchSample extends Activity {

	private MainView mainView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MainViewのインスタンスを生成
        mainView = new MainView(this);

        //Viewオブジェクトとして、MainViewのインスタンスをセット
        //レイアウトのリソースは渡さず直接Viewオブジェクトを渡している
        //setContentView(R.layout.main);
        setContentView(mainView);
    }

    //タッチイベント発生時に呼び出される
    public boolean onTouchEvent(MotionEvent event)
    {
    	//イベントの種類を取得
    	int action_type = event.getAction();

    	//イベントの種類で条件分岐し、MainViewにイベントの種類をセット
    	switch(action_type)
    	{
	    	case MotionEvent.ACTION_DOWN:
	    		mainView.setTouchState("Down");
	    		break;
	    	case MotionEvent.ACTION_UP:
	    		mainView.setTouchState("Up");
	    		break;
	    	case MotionEvent.ACTION_MOVE:
	    		mainView.setTouchState("Move");
	    		break;
    	}

	    //タッチ数を取得
	    int pointer_count = event.getPointerCount();

	    //イベントの種類で条件分岐
	    //タッチされた指がスライドされたことを表すイベント
	    if(action_type == MotionEvent.ACTION_MOVE)
	    {
	    	//MOVEイベントの場合のみ、処理の重さに応じて処理がスキップされヒストリーに保存されている
	    	//ヒストリー数（過去のイベント情報をどれだけ保存しているか）を取得
	    	int history_count = event.getHistorySize() / pointer_count;

	    	//ヒストリーはインデックスが若いものほど古いので古い順に処理
	    	for(int i=0; i<history_count; i++)
	    	{
	    		//2箇所分のタッチイベントを検出する
	    		for(int j=0; j<2; j++)
	    		{
	    			int idx = event.findPointerIndex(j);

	    			//タッチされていなければ、findPointerIndexが-1を返す
	    			if(idx >= 0)
	    			{
	    				//タッチ座標を取得
	    				int x = (int)event.getHistoricalX(idx, i);
	    				int y = (int)event.getHistoricalY(idx, i);
	    				mainView.setTouchXY(j, x, y);
	    				mainView.setTouchFlag(j, true);
	    			}
	    			else
	    			{
	    				mainView.setTouchFlag(j, false);
	    			}
	    		}
	    	}
	    }
	    //タッチされた瞬間かタッチした指がすべて離されたとき
	    else if(action_type == MotionEvent.ACTION_DOWN || action_type == MotionEvent.ACTION_UP)
	    {
    		//2箇所分のタッチイベントを検出する
    		for(int i=0; i<2; i++)
    		{
    			int idx = event.findPointerIndex(i);

    			//タッチされていなければ、findPointerIndexが-1を返す
    			if(idx >= 0)
    			{
    				//タッチ座標を取得
    				int x = (int)event.getHistoricalX(idx, i);
    				int y = (int)event.getHistoricalY(idx, i);
    				mainView.setTouchXY(i, x, y);

    				if(action_type == MotionEvent.ACTION_DOWN)
    				{
        				mainView.setTouchFlag(i, true);
    				}
    				else if(action_type == MotionEvent.ACTION_UP)
    				{
    					mainView.setTouchFlag(i,false);
    				}
    				else
    				{
    					mainView.setTouchFlag(i, false);
    				}
    			}
    		}
	    }
	    //MainViewの再描画を行う
	    mainView.invalidate();

	    return true;
    }
}