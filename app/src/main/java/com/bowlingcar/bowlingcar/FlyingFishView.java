package com.bowlingcar.bowlingcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView  extends View {
    private Bitmap fish[] =new Bitmap[2];
    private int fishX =10;
    private int fishY;
    private int fishSpeed;
    private int canvasWeight,canvasHeight;
    private int yellowX,yellowY,yellowSpeed=16;
    private Paint yellowPaint= new Paint();

    private int greenX,greenY,greenSpeed=25;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed=40;
    private Paint redPaint = new Paint();

    private int score,lifeCounterOfFish;

    private boolean touch = false;
    private Bitmap backgroudImage;
    private Paint scorepaint = new Paint();
    private Bitmap life[] =new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.sport_ca);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.sport);

        backgroudImage = BitmapFactory.decodeResource(getResources(),R.drawable.images_two);

        yellowPaint.setColor(Color.WHITE);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY=550;
        score =0;
        lifeCounterOfFish=3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWeight = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroudImage,0,0,null);

        int mainFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY +fishSpeed;

        if(fishY< mainFishY){

            fishY =mainFishY;

        }if(fishY> maxFishY){
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed+2;

        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }
//yellow ball
        yellowX = yellowX -yellowSpeed;

        if(hitballchecker(yellowX,yellowY)){
            score = score+1;
            yellowX = -100;
        }

        if(yellowX<0){
            yellowX = canvasWeight +21;
            yellowY = (int) Math.floor(Math.random() *(maxFishY - mainFishY))+mainFishY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);
//green ball
        greenX= greenX -greenSpeed;

        if(hitballchecker(greenX,greenY)){
            score = score+5;
            greenX = -100;
        }

        if(greenX<0){
            greenX= canvasWeight +21;
            greenY = (int) Math.floor(Math.random() *(maxFishY - mainFishY))+mainFishY;
        }
        canvas.drawCircle(greenX,greenY,15,greenPaint);
//red ball
        redX= redX -redSpeed;

        if(hitballchecker(redX,redY)){

            greenX = -100;
            lifeCounterOfFish--;
            if(lifeCounterOfFish==0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),GameFinished.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Score",score);
                getContext().startActivity(intent);
            }
        }

        if(redX<0){
            redX= canvasWeight +21;
            redY= (int) Math.floor(Math.random() *(maxFishY - mainFishY))+mainFishY;
        }
        canvas.drawCircle(redX,redY,40,redPaint);

        canvas.drawText("Score"+score,20,60,scorepaint);

       for(int i=0; i<3; i++){

           int x = (int) (580 +life[0].getWidth() *1.5 * i);
           int y =30;

           if(i <lifeCounterOfFish){
               canvas.drawBitmap(life[0],x,y,null);
           }else {
               canvas.drawBitmap(life[1],x,y,null);
           }
       }

    }
    public boolean hitballchecker(int x, int y){
        if(fishX < x  && x <(fishX + fish[0].getWidth()) && fishY < y && y <(fishY+fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed = -22;
        }
        return true;
    }
}
