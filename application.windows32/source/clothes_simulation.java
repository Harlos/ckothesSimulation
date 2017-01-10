import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import toxi.physics2d.*; 
import toxi.physics2d.behaviors.*; 
import toxi.geom.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class clothes_simulation extends PApplet {





VerletPhysics2D physics;
Clothes clothes;

public void setup() {
  
  physics=new VerletPhysics2D();
  physics.setWorldBounds(new Rect(0, 0, width, height));
  physics.addBehavior(new GravityBehavior(new Vec2D(0, 0.5f)));
  clothes = new Clothes(10, 50, 50);
}

public void mousePressed(){
  clothes.change();
}

public void mouseReleased(){
  clothes.release();
}

public void draw() {
  background(51);
  text("Try to press the mouse's buttons", 10, 10);
  physics.update();
  clothes.display();
}
class Clothes {
  VerletParticle2D[][] clothes;
  int w;
  int h;
  float strength;
  int len;
  VerletParticle2D moved;
  Clothes(int len, int h, int w) {
    strength = 1.7f;
    this.w = w;
    this.h = h;
    this.len = len;
    moved = new VerletParticle2D(0, 0);
    clothes = new VerletParticle2D[w][];
    for (int i = 0; i < w; i++) {
      clothes[i] = new VerletParticle2D[h];
      for (int j = 0; j < h; j++) {      
        clothes[i][j] = new VerletParticle2D(width/4 + i * len, j * len);
        physics.addParticle(clothes[i][j]);
      }
    }

    for (int i = 0; i < w; i++) 
      for (int j = 0; j < h; j++) {    
        if (i != 0) {
          VerletSpring2D spring = new VerletSpring2D(clothes[i-1][j], clothes[i][j], len, strength);
          physics.addSpring(spring);
        }
        if (i != w - 1) {
          VerletSpring2D spring = new VerletSpring2D(clothes[i + 1][j], clothes[i][j], len, strength);
          physics.addSpring(spring);
        }
        if (j != 0) {
          VerletSpring2D spring = new VerletSpring2D(clothes[i][j - 1], clothes[i][j], len, strength);
          physics.addSpring(spring);
        }
        if (j != h - 1) {
          VerletSpring2D spring = new VerletSpring2D(clothes[i][j + 1], clothes[i][j], len, strength);
          physics.addSpring(spring);
        }
      }
    clothes[w - 1][0].lock();
    clothes[0][0].lock();
  }

  public void change() {   
    if (mouseButton == RIGHT) {
      clothes[w-1][h-1].set(width/6+(w-1)*len, (h/2)*len);
      clothes[w-1][h-1].lock();
    }  
    if (mouseButton == LEFT) {
      clothes[0][h-1].set(width/6, (h/2)*len);
      clothes[0][h-1].lock();
    }
  }

  public void release() {
    if (mouseButton != RIGHT) 
      clothes[w-1][h-1].unlock();
    if (mouseButton != LEFT) 
      clothes[0][h-1].unlock();
  }
  public void display() {  
    strokeWeight(2);
    noFill();
    for (int i = 0; i < w; i++) 
      for (int j = 0; j < h; j++) {    
        if (i != 0) 
          line(clothes[i-1][j].x, clothes[i-1][j].y, clothes[i][j].x, clothes[i][j].y);
        if (i > w - 1) 
          line(clothes[i+1][j].x, clothes[i+1][j].y, clothes[i][j].x, clothes[i][j].y);
        if (j != 0) 
          line(clothes[i][j-1].x, clothes[i][j-1].y, clothes[i][j].x, clothes[i][j].y);
        if (j > h - 1) 
          line(clothes[i][j-1].x, clothes[i][j-1].y, clothes[i][j].x, clothes[i][j].y);
      }
  }
};
  public void settings() {  size(800, 600, FX2D ); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "clothes_simulation" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
