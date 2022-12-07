package com.ketai.reader;

import com.ketai.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

public class DrawWaterWave extends View implements Runnable
{
    boolean bRunning = false;
    int BACKWIDTH;
    int BACKHEIGHT;
    short[] buf2;
    short[] buf1;
    int[] Bitmap2;
    int[] Bitmap1;
    
    public DrawWaterWave(Context context)
    {
    	super(context);
    
    	Bitmap image = BitmapFactory.decodeResource(this.getResources(),R.drawable.loginimage2);
    	BACKWIDTH = 320;
    	BACKHEIGHT = 480;
    	
    	System.out.println(BACKWIDTH);
    	System.out.println(BACKHEIGHT);
        buf2 = new short[BACKWIDTH * BACKHEIGHT];
        buf1 = new short[BACKWIDTH * BACKHEIGHT];
        Bitmap2 = new int[BACKWIDTH * BACKHEIGHT];
        Bitmap1 = new int[BACKWIDTH * BACKHEIGHT];

        image.getPixels(Bitmap1, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT); 
        start();
    }
    
    
    void DropStone(int x,//x����
            	   int y,//y����
            	   int stonesize,//��Դ�뾶
            	   int stoneweight)//��Դ����
    {
        if ((x + stonesize) > BACKWIDTH || (y + stonesize) > BACKHEIGHT
                || (x - stonesize) < 0 || (y - stonesize) < 0)
            return;

        for (int posx = x - stonesize; posx < x + stonesize; posx++)
            for (int posy = y - stonesize; posy < y + stonesize; posy++)
                if ((posx - x) * (posx - x) + (posy - y) * (posy - y) < stonesize
                        * stonesize)
                    buf1[BACKWIDTH * posy + posx] = (short)-stoneweight;
    }

    
    protected void onDraw(Canvas canvas)
    {
    	canvas.drawBitmap(Bitmap2, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT, false, null);
    }

    public void start() 
    {
        bRunning = true;
        Thread t = new Thread(this);
        t.start();
    }
    public void stop() 
    {
        bRunning = false;
    }
    public void run() 
    { 
        while (bRunning) 
        {
			try
			{
				//Thread.sleep(10);
				RippleSpread();
		        render();
		        postInvalidate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
        }
    }
    void RippleSpread()
    {
    	for (int i=BACKWIDTH; i<BACKWIDTH*BACKHEIGHT-BACKWIDTH; i++)
    	{
    		//������ɢ
    		buf2[i] = (short)(((buf1[i-1]+buf1[i+1]+buf1[i-BACKWIDTH]+buf1[i+BACKWIDTH])>>1)- buf2[i]);
    		//����˥��
    		buf2[i] -= buf2[i]>>5;
    	}

    	//�����������ݻ�����
    	short []ptmp =buf1;
    	buf1 = buf2;
    	buf2 = ptmp;
    }
    void render() {
    	int xoff, yoff;
    	int k = BACKWIDTH;
    	for (int i=1; i<BACKHEIGHT-1; i++)
    	{
    		for (int j=0; j<BACKWIDTH; j++)
    		{
    			//����ƫ����
    			xoff = buf1[k-1]-buf1[k+1];
    			yoff = buf1[k-BACKWIDTH]-buf1[k+BACKWIDTH];

    			//�ж������Ƿ��ڴ��ڷ�Χ��
    			if ((i+yoff )< 0 ) {k++; continue;}
    			if ((i+yoff )> BACKHEIGHT) {k++; continue;}
    			if ((j+xoff )< 0 ) {k++; continue;}
    			if ((j+xoff )> BACKWIDTH ) {k++; continue;}

    			//�����ƫ�����غ�ԭʼ���ص��ڴ��ַƫ����
    			int pos1, pos2;
    			pos1=BACKWIDTH*(i+yoff)+ (j+xoff);
    			pos2=BACKWIDTH*i+ j;    			
    			Bitmap2[pos2++]=Bitmap1[pos1++];
    			k++;
    		}
    	}
    }

}
