import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.geom.*;

VerletPhysics2D physics;
Clothes clothes;

void setup() {
  size(800, 600, FX2D );
  physics=new VerletPhysics2D();
  physics.setWorldBounds(new Rect(0, 0, width, height));
  physics.addBehavior(new GravityBehavior(new Vec2D(0, 0.5)));
  clothes = new Clothes(10, 50, 50);
}

void mousePressed(){
  clothes.change();
}

void mouseReleased(){
  clothes.release();
}

void draw() {
  background(51);
  text("Try to press the mouse's buttons", 10, 10);
  physics.update();
  clothes.display();
}