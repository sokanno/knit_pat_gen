import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pattern_gen_CA extends PApplet {



ControlP5 cp5;
PImage img;
Range range;
PFont font;
Slider abc;
int row = 100;
int column = 100;
boolean [][] pixelBool = new boolean [row][column];
int [][] lastPixelBool = new int [row][column];
int rectSize = 500;
int res = rectSize/row;
int horizonMargin = 20;
int topMargin = 20;
int bottomMargin = 140;
int colorVal;
int maxInterval = 10000;
int interval = 1000;
boolean [] materialArray = new boolean [maxInterval];
boolean [] editMaterialArray = new boolean [maxInterval];

int element_0 = 10; 
int element_1 = 20;
int rangeLimit = 100;

// variable for lifegame
int sparseDefault = 2;
int denseDefault = 4;

int dense = 2;
int sparse = 4;
int birth = 3;

boolean lifegameFlag = false;

public void setup() {
  size(rectSize + horizonMargin*2, rectSize + topMargin +bottomMargin);
  colorMode(RGB);
  font = loadFont("04b-03b-16.vlw");
  textAlign(RIGHT, BOTTOM);
  cp5 = new ControlP5(this);
  range = cp5.addRange("White-Black")
    // disable broadcasting since setRange and setRangeValues will trigger an event
    .setBroadcast(false) 
      .setPosition(horizonMargin, rectSize + topMargin+20)
        .setSize(240, 20)
          .setHandleSize(20)
            .setRange(0, rangeLimit)
              .setRangeValues(element_0, element_1)
                // after the initialization we turn broadcast back on again
                .setBroadcast(true)
                  .setColorForeground(color(64, 124, 255, 90))
                    .setColorBackground(color(0, 10, 100, 80))  
                      ;        
  range = cp5.addRange("range")
    // disable broadcasting since setRange and setRangeValues will trigger an event
    .setBroadcast(false) 
      .setPosition(horizonMargin+60, rectSize + topMargin+100)
        .setSize(100, 20)
          .setHandleSize(10)
            .setRange(0, 8)
              .setRangeValues(dense,sparse)
                // after the initialization we turn broadcast back on again
                .setBroadcast(true)
                  .setColorForeground(color(64, 124, 255, 90))
                    .setColorBackground(color(0, 10, 100, 80))  
                      ; 
  cp5.addSlider("birth")
    .setPosition(horizonMargin+200, rectSize + topMargin+100)
      .setWidth(80)
        .setRange(0,8) // values can range from big to small as well
          .setValue(birth)
            .setNumberOfTickMarks(9)
              .setSliderMode(Slider.FLEXIBLE)
                .setColorForeground(color(64, 124, 255, 90))
                  .setColorBackground(color(0, 10, 100, 80))  
                    ;
                                
  // cp5.addSlider("sparse")
  //   .setPosition(horizonMargin+90, rectSize+topMargin+100)
  //     .setWidth(70)
  //       .setRange(0,7) // values can range from big to small as well
  //         .setValue(sparseDefault)
  //           .setNumberOfTickMarks(8)
  //             // .setSliderMode(Slider.FLEXIBLE)
  //               .setColorBackground(color(0, 10, 100, 80))  
  //                 ;  
  // cp5.addSlider("dense")
  //   .setPosition(horizonMargin+200, rectSize+topMargin+100)
  //     .setWidth(70)
  //       .setRange(1,8) // values can range from big to small as well
  //         .setValue(denseDefault)
  //           .setNumberOfTickMarks(8)
  //             // .setSliderMode(Slider.FLEXIBLE)
  //               .setColorBackground(color(0, 10, 100, 80))  
  //                 ;                    
  cp5.addButton("export_image")
    .setPosition(rectSize-70, rectSize+topMargin+20)
      .setSize(90, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  cp5.addButton("left")
    .setPosition(horizonMargin, rectSize+topMargin+60)
      .setSize(30, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  cp5.addButton("right")
    .setPosition(horizonMargin+40, rectSize+topMargin+60)
      .setSize(30, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  cp5.addButton("up")
    .setPosition(horizonMargin+80, rectSize+topMargin+60)
      .setSize(30, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  cp5.addButton("down")
    .setPosition(horizonMargin+120, rectSize+topMargin+60)
      .setSize(30, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  cp5.addButton("lifegame")
    .setPosition(horizonMargin, rectSize+topMargin+100)
      .setSize(50, 20)
        .setColorBackground(color(0, 10, 100, 80))
          ;
  // customize(presetSelector); // customize the first list
  img = createImage(row, column, HSB);
  textSize(10);
  for (int i=0; i<interval; i++) {
    if (i%element_1 < element_0) materialArray[i] = true;
    else materialArray[i] = false;
  }
}

public void draw() {
  background(96);
  fill(40,50,100,80);
  textFont(font, 16);
  text("PATTERN GENERATOR", 
       rectSize+horizonMargin, rectSize+bottomMargin);
  //make a pixel array
  if(!lifegameFlag){
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        if(editMaterialArray[(i*row+j) % interval] == false){
          pixelBool[i][j] = materialArray[ (i*row+j) % interval ];
        }else pixelBool[i][j] = !materialArray[ (i*row+j) % interval ];
      }
    }
  }

  //draw the pixel array
  for (int i=0; i<row; i++) {
    for (int j=0; j<column; j++) {
      if (i*row + j < interval) {
        stroke(192);
        if (pixelBool[i][j] == true) colorVal = 200;
        else colorVal = 50;
      }
      else {
        stroke(64);
        if (pixelBool[i][j] == true) colorVal = 255;
        else colorVal = 0;
      }
      fill(colorVal);
      rect(horizonMargin+j*res, topMargin+i*res, res, res);
    }
  }
}

public void controlEvent(ControlEvent theControlEvent) {
  if (theControlEvent.isFrom("White-Black")) {
    // min and max values are stored in an array.
    // access this array with controller().arrayValue().
    // min is at index 0, max is at index 1.
    element_0 = PApplet.parseInt(theControlEvent.getController().getArrayValue(0));
    element_1 = PApplet.parseInt(theControlEvent.getController().getArrayValue(1));
    if(element_1 == 0) element_1 = 1;
    for (int i=0; i<interval; i++) {
      if (i%element_1 < element_0) materialArray[i] = true;
      else materialArray[i] = false;
      lifegameFlag = false;
    }
  }
  // if(sparse >= dense) sparse = dense - 1; 
  // if(dense <= sparse) dense = sparse + 1; 
  if (theControlEvent.isFrom("range")) {
    sparse = PApplet.parseInt(theControlEvent.getController().getArrayValue(0));
    dense = PApplet.parseInt(theControlEvent.getController().getArrayValue(1));
  }
}

public void keyPressed() {
  if (key == CODED) {
    if (keyCode == RIGHT && interval < maxInterval) {
      interval++;
      lifegameFlag = false;
    }
    else if (keyCode == LEFT && interval > 1) {
      interval--;	
      lifegameFlag = false;
    }
    else if (keyCode == DOWN && interval < maxInterval) {
      interval+=row;
      lifegameFlag = false;
    }
    else if (keyCode == UP && interval > row) {
      interval-=row;
      lifegameFlag = false;
    }
  }
  boolean [] materialArray = new boolean [interval];
  if (key == 'l' ||key == 'L'){
    lifegame();
  }
}

public void left(){
  if (interval > 1) interval--; 
  lifegameFlag = false;
}
public void right(){
  if(interval < maxInterval) interval++;
  lifegameFlag = false;
}
public void up(){
  if(interval > row) interval-=row;
  lifegameFlag = false;
}
public void down(){
 if(interval < maxInterval) interval+=row; 
 lifegameFlag = false;
}

public void mousePressed() {
  if (mouseX >= horizonMargin 
    && mouseX <= rectSize + horizonMargin
    && mouseY >= topMargin 
    && mouseY <= rectSize + topMargin) {
    int xPos = (mouseX - horizonMargin) / res;
    int yPos = (mouseY - topMargin) / res;
    int xyPos = yPos*row + xPos;
    if ((xyPos) > interval) {
      xyPos = xyPos % interval;
    }
    editMaterialArray[xyPos] = !editMaterialArray[xyPos];
    lifegameFlag = false;
  }
  // print(sparse);
  // println(dense);
}

public void lifegame(){
  lifegameFlag = true;

  // // //end connect to the other end version (haven't done)
  // for(int i=0; i<row*3; i++){
  //   for(int j=0; j<column*3; j++){
  //     lastPixelBool[i][j] = int(pixelBool[i%row][j%column]);
  //     // if(i==0 && j==column-1) println("ussu");
  //   }
  // }
  // for(int i=row; i<row*2; i++){
  //   for(int j=column; j<column*2; j++){
  //     int state = lastPixelBool[i-1][j] + lastPixelBool[i+1][j]
  //                +lastPixelBool[i][j-1] + lastPixelBool[i][j+1]
  //                +lastPixelBool[i+1][j-1] + lastPixelBool[i+1][j+1]
  //                +lastPixelBool[i-1][j-1] + lastPixelBool[i-1][j+1];

  //     if(pixelBool[i%row][j%column] == true && state == 3 || state == 2){
  //       pixelBool[i%row][j%column] = true;
  //     }
  //     else if(pixelBool[i%row][j%column] == true && state < 2){
  //       pixelBool[i%row][j%column] = false;
  //     }
  //     else if(pixelBool[i%row][j%column] == true && state > 4){
  //       pixelBool[i%row][j%column] = false;
  //     }
  //     else if(pixelBool[i%row][j%column] == false && state == 3){
  //       pixelBool[i%row][j%column] = true;
  //     }
  //     else pixelBool[i%row][j%column] = false;
  //   }
  // }

  //end is the wall version
  for(int i=0; i<row; i++){
    for(int j=0; j<column; j++){
      lastPixelBool[i][j] = PApplet.parseInt(pixelBool[i][j]);
    }
  }
  for(int i=1; i<row-1; i++){
    for(int j=1; j<column-1; j++){
      int state = lastPixelBool[i-1][j] + lastPixelBool[i+1][j]
                 +lastPixelBool[i][j-1] + lastPixelBool[i][j+1]
                 +lastPixelBool[i+1][j-1] + lastPixelBool[i+1][j+1]
                 +lastPixelBool[i-1][j-1] + lastPixelBool[i-1][j+1];
                      
      if(pixelBool[i][j] == true){    
        if(state < dense && state >= sparse){ //Survive
          pixelBool[i][j] = true;
        }
        else if(state < sparse){           //Depopulation
          pixelBool[i][j] = false;
        }
        else if(state >= dense){           //Congestion
          pixelBool[i][j] = false;
        }
      }
      else if(pixelBool[i][j] == false && state == birth){ //Birth                       
        pixelBool[i][j] = true;
      }        
      else{
        pixelBool[i][j] = false;
      }
    }
  }
}

public void export_image() {
  img.loadPixels();
  for (int i=0; i<row; i++) {
    for (int j=0; j<column; j++) {
      if (pixelBool[i][j]) img.pixels[i*row+j] = color(255);
      else img.pixels[i*row+j] = color(0);
    }
  }
  img.updatePixels();
  selectOutput("Select a file to write to:", "fileOutput");
}

public void fileOutput(File selection) {
  if (selection != null) {
    println("User selected " + selection.getAbsolutePath());
    img.save(selection.getAbsolutePath() + ".jpg");
    println("done saving");
  }
}




  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pattern_gen_CA" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
