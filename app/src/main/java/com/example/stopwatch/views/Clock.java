package com.example.stopwatch.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.stopwatch.ClockDetails;
import com.example.stopwatch.R;

public class Clock extends View {

    public int[] val= new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    public float[] xP;
    public float[] yP;
    public float[] xPMin;
    public float[] yPMin;
    public float tWidth;
    public float tHeight;
    public float minSpace;
    public float dialRadius;
    public float radiusR;
    public boolean isSec=true;
    public int lapse;
    public Paint paintOut;
    public Paint paintOutFill;
    public Paint paintHand;
    public Paint paintOutWrite;
    public Paint paintBack;

    public float padding=10;
    public float clockRad;
    public float xCen;
    private float yCen;

    //min
    public float tWidthMin;
    public float tHeightMin;
    public float minSpaceMin;
    public float dialRadiusMin;
    public float radiusRMin;
    public int lapseMin;
    public Paint paintOutMin;
    public Paint paintOutFillMin;
    public Paint paintHandMin;
    public Paint paintOutWriteMin;
    public Paint paintBackMin;

    public float paddingMin=60;
    public float clockRadMin;
    public float xCenMin;
    private float yCenMin;

    private ClockDetails cd;

    public Clock(Context context) {
        super(context);
        init(null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }
    public void init(@Nullable AttributeSet set){
        if(set==null){
            return;
        }

        //sec
            paintOut=new Paint();
            paintOut.setStyle(Paint.Style.STROKE);
            paintOut.setColor(getResources().getColor(R.color.blue));
            paintHand=new Paint(Paint.ANTI_ALIAS_FLAG);
            paintOut.setStyle(Paint.Style.STROKE);
            paintHand.setColor(getResources().getColor(R.color.yellow));
            paintOutWrite=new Paint();
            paintOutWrite.setStyle(Paint.Style.FILL);
            paintOutWrite.setColor(getResources().getColor(R.color.blue));
            paintOutWrite.setTextSize(50);
            paintOutFill=new Paint();
            paintOutFill.setStyle(Paint.Style.FILL);
            paintOutFill.setColor(getResources().getColor(R.color.blue));
            paintBack=new Paint();
            paintBack.setStyle(Paint.Style.FILL);
            paintBack.setColor(getResources().getColor(R.color.blackish));
        //min
            paintOutMin=new Paint();
            paintOutMin.setStyle(Paint.Style.STROKE);
            paintOutMin.setColor(getResources().getColor(R.color.colorAccent));
            paintHandMin=new Paint(Paint.ANTI_ALIAS_FLAG);
            paintOutMin.setStyle(Paint.Style.STROKE);
            paintHandMin.setColor(getResources().getColor(R.color.minHand));
            paintOutWriteMin=new Paint();
            paintOutWriteMin.setStyle(Paint.Style.FILL);
            paintOutWriteMin.setColor(getResources().getColor(R.color.colorAccent));
            paintOutWriteMin.setTextSize(25);
            paintOutFillMin=new Paint();
            paintOutFillMin.setStyle(Paint.Style.FILL);
            paintOutFillMin.setColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        set();
        //sec
        canvas.drawColor(getResources().getColor(R.color.blackish));
        canvas.drawCircle(xCen,yCen,clockRad,paintOut);
        canvas.drawLine(xCen,yCen,xCen,yCen-dialRadius,paintHand);
        canvas.drawCircle(xCen,yCen,20,paintOutFill);
        for(int i=0;i<12;i++) {
            canvas.drawText("" +val[i],xP[i]-20,yP[i],paintOutWrite);
        }

        //min
        canvas.drawCircle(xCenMin,yCenMin,clockRadMin,paintOutMin);
        canvas.drawLine(xCenMin,yCenMin,xCenMin,yCenMin-dialRadiusMin,paintHandMin);
        canvas.drawCircle(xCenMin,yCenMin,10,paintOutFillMin);
        for(int i=0;i<12;i++) {
            canvas.drawText("" +val[i],xPMin[i]-10,yPMin[i],paintOutWriteMin);
        }

        invalidate();

        moveHands(canvas);
    }

    private void moveHands(Canvas canvas) {
        if(ClockDetails.ang!=0){
            canvas.drawCircle(xCen,yCen,dialRadius,paintBack);

            canvas.drawCircle(xCenMin,yCenMin,clockRadMin,paintOutMin); //min circle
            for(int i=0;i<12;i++) {
                canvas.drawText("" +val[i],xPMin[i]-10,yPMin[i],paintOutWriteMin);  //numbers
            }

            canvas.drawLine(xCenMin,yCenMin,(float) (xCenMin+((dialRadiusMin)*Math.sin(Math.toRadians(ClockDetails.angMin)))),(float) (yCenMin-((dialRadiusMin)*Math.cos(Math.toRadians(ClockDetails.angMin)))),paintHandMin);
            canvas.drawCircle(xCenMin,yCenMin,10,paintOutFillMin);//min center
            //min done

            canvas.drawLine(xCen,yCen,(float) (xCen+((dialRadius)*Math.sin(Math.toRadians(ClockDetails.ang)))),(float) (yCen-((dialRadius)*Math.cos(Math.toRadians(ClockDetails.ang)))),paintHand);
            canvas.drawCircle(xCen,yCen,10,paintOutFill);
        }

    }

    private void set() {
        tWidth=getWidth();
        tHeight=getHeight();
        minSpace=Math.min(tWidth,tHeight);
        clockRad=(minSpace-padding)/2;
        radiusR=clockRad-75;
        dialRadius=radiusR-50;
        xCen=tWidth/2;
        yCen=tHeight/2;
//        Log.i("Main", "Center: "+xCen);
//        Log.i("Main", "Center: "+yCen);
        paintOut.setStrokeWidth(10);
        paintHand.setStrokeWidth(8);
        xP=new float[12];
        yP=new float[12];

        for(int i=0;i<12;i++){
            xP[i]= (float) (xCen+((radiusR)*Math.sin(Math.toRadians(i*30))));
            yP[i]= (float) (yCen-((radiusR)*Math.cos(Math.toRadians(i*30))));
        }

        minSpaceMin=dialRadius;
        clockRadMin=(minSpaceMin-paddingMin)/2;
        radiusRMin=clockRadMin-50;
        dialRadiusMin=radiusRMin-25;
        xCenMin=dialRadius/2+125;
        yCenMin=yCen;
        paintOutMin.setStrokeWidth(10);
        paintHandMin.setStrokeWidth(6);
        xPMin=new float[12];
        yPMin=new float[12];

        for(int i=0;i<12;i++){
            xPMin[i]= (float) (xCenMin+((radiusRMin)*Math.sin(Math.toRadians(i*30))));
            yPMin[i]= (float) (yCenMin-((radiusRMin)*Math.cos(Math.toRadians(i*30))));
        }
    }

}
