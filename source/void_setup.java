import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.*; 
import static javax.swing.JOptionPane.*; 
import java.util.*; 
import java.io.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class void_setup extends PApplet {

public void setup() {
  background(0);
  textSize(100);
  fill(255);
  stroke(255);
  frameRate(50);
  
// Hier werden die Bilder geladen.

  shuttle = loadImage("shuttle.png");
  meteor = loadImage("meteor.png");
  healpack = loadImage("healpack.png");
  bullet = loadImage("bullet.png");

// Hier werden die Anfangsdaten der Sterne festgelegt.

  int starCounter = 0;

  while (starCounter < MAXstars) {
    starX[starCounter] = random(0, width);
    starY[starCounter] = random(0, height);
    starS[starCounter] = random(0.1f, 0.5f);
    starSize[starCounter] = random(1.0f, 3.0f);
    starCounter += 1;
  }

// Hier werden die Anfangsdaten der Planeten festgelegt.

  int planetCounter = 0;

  while (planetCounter < MAXplanets) {
    planetC[planetCounter] = (int)random(30, 80);
    planetX[planetCounter] = random(0, width);
    planetY[planetCounter] = random(0, height);
    planetS[planetCounter] = random(0.5f, 2.0f);
    planetSize[planetCounter] = random(300, 500);
    planetCounter += 1;
  }

// Hier werden die Anfangsdaten der Mteoriten festgelegt.

  int meteorCounter = 0;

  while (meteorCounter < MAXmeteors) {
    meteorX[meteorCounter] = width;
    meteorY[meteorCounter] = random(0, height);
    meteorS[meteorCounter] = random(1, 5);
    meteorSize[meteorCounter] = random(30, 100);
    meteorCounter += 1;
  }

// Hier werden die Daten des Medipacks festgelegt.

  packX = width;
  packY = random(0, height);
  packSize = 100;
  packS = 3;

// Hier werden die Daten der Kugel festgelegt.

  bulletX = mouseX;
  bulletY = mouseY;
  bulletS = 5;
  bulletSize = 50;
  
// Hier wird der Highscore gelesen.
  
  readHighscore();
  println("Highscores erfolgreich geladen.");
}
// ===== Hier werden Scores der Spieler verglichen. ===== \\

class Player implements Comparable<Player> {
  public String Name;
  public int Score;

  public int compareTo(Player player) {    
    if (player.Score == Score)
    {
      return 0;
    } else if (player.Score < Score)
    {
      return -1;
    } else
    {
      return 1;
    }
  }

  public String print() {
    String returnValue = Name + " - " + Score;
    return returnValue;
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== ===== === Hier werden die Sterne generiert. === ===== ===== ===== ===== ===== ===== \\

public void drawStars() {

  int starCounter = 0;

  // Hier werden die Sterne bewegt.

  while (starCounter < MAXstars) {
    stroke(255);
    strokeWeight(starSize[starCounter]);
    point(starX[starCounter], starY[starCounter]); 

    starX[starCounter] = starX[starCounter] - starS[starCounter];

    // Hier werden die Sterne, wenn sie links ankommen, wieder nach rechts gesetzt.

    if (starX[starCounter] < 0) {
      starX[starCounter] = width;
    }
    starCounter += 1;
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== == Hier werden die Planeten generiert. == ===== ===== ===== ===== ===== ===== \\

public void drawPlanets() {

  int planetCounter= 0;

  // Hier werden die Planeten bewegt.

  while (planetCounter < MAXplanets) {
    stroke(planetC[planetCounter]);
    strokeWeight(planetSize[planetCounter]);
    point(planetX[planetCounter], planetY[planetCounter]);

    // Hier werden die Planeten, wenn sie links ankommen, wieder nach rechts gesetzt.

    planetX[planetCounter] = planetX[planetCounter] - planetS[planetCounter];
    if (planetX[planetCounter] < -200) {
      planetX[planetCounter] = width + 200;
      planetY[planetCounter] = random(-200, height);
      planetSize[planetCounter] = random(300, 500);
      planetS[planetCounter] = random(0.5f, 2.0f);
      planetC[planetCounter] = (int)random(30, 80);
    }
    planetCounter += 1;
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== Hier wird das Shuttle generiert. == ===== ===== ===== ===== ===== ===== ===== ===== \\

public void drawShuttle() {

  // Hier wird getestet, ob die Maus außerhalb des Fensters ist, um das Shuttle dann am Rand zu bewegen, oder, wenn die Maus im Fenster ist, das Shuttle zur Maus zu packen.

  if (MouseInfo.getPointerInfo().getLocation().x < displayWidth / 6) {    
    shuttleX = 0 - 50;
  } else if (MouseInfo.getPointerInfo().getLocation().x > displayWidth / 6 * 5) {
    shuttleX = (displayWidth / 6 * 4) - 50;
  } else {


    shuttleX = MouseInfo.getPointerInfo().getLocation().x - (displayWidth / 6) - 50;
  }

  if (MouseInfo.getPointerInfo().getLocation().y < (displayHeight / 6) + 25) {
    shuttleY = 0 - 25;
  } else if (MouseInfo.getPointerInfo().getLocation().y > (displayHeight / 6 * 5) + 25) {
    shuttleY = (displayHeight / 6 * 4) - 25;
  } else {
    shuttleY = MouseInfo.getPointerInfo().getLocation().y - ((displayHeight / 6)) - 50;
  }

  // Hier wird das Shuttle platziert 

  image(shuttle, shuttleX, shuttleY - 25, shuttleSize, shuttleSize);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== Hier wird der Score gezählt und angezeigt. ==== ===== ===== ===== ===== ===== ===== \\

public void drawScore() {
  score += 1;
  textAlign(RIGHT);
  fill(255);
  textSize(displayHeight/20);
  text(score, displayWidth/6*4, displayHeight/20);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== Hier wird das Level gezähl und agezeigt.  ===== ===== ===== ===== ===== ===== ===== \\

public void drawLevel() {
  lvlTimer += 1;
  if (lvlTimer > 250) {
    lvl += 1;
    lvlTimer = 0;
  }
  textAlign(LEFT);
  fill(255);
  textSize(displayHeight/20);
  text("Level: " + lvl, 0, displayHeight/20);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== ===== Hier werden die Meteoriten genereiert. == ===== ===== ===== ===== ===== ===== \\

public void drawMeteors() {
  int meteorCounter = 0;

  // Hier wird die Anzahl der Meteoriten an das Level angepasst.

  MAXmeteors = lvl + 3;

  //  Hier werden die Meteoriten platziert.
  while (meteorCounter < MAXmeteors) {
    image(meteor, meteorX[meteorCounter] - (meteorSize[meteorCounter]/2), meteorY[meteorCounter] - (meteorSize[meteorCounter]/2), meteorSize[meteorCounter], meteorSize[meteorCounter]); 

    // Hier werden die Meteoriten bewegt.

    meteorX[meteorCounter] = meteorX[meteorCounter] - (meteorS[meteorCounter] + (lvl / 3));

    // Hier wird getestet, ob der Meteor mit der Kugel kollidiert.

    if (isColliding(meteorSize[meteorCounter], meteorX[meteorCounter], meteorY[meteorCounter], bulletSize, bulletX, bulletY)) {
      meteorS[meteorCounter] = random(1, 5);
      meteorX[meteorCounter] = width;
      meteorY[meteorCounter] = random(0, height);
      meteorSize[meteorCounter] = random(30, 100);

      bulletInAir = false;
      bulletX = -100;
      bulletY = -100;
    }

    // Hier wird getestet, ob der Meteor mit dem Shuttle kollidiert.

    if (isColliding(shuttleSize, mouseX, mouseY, meteorSize[meteorCounter], meteorX[meteorCounter], meteorY[meteorCounter])) {
      health -=1;
      meteorS[meteorCounter] = random(1, 5);
      meteorX[meteorCounter] = width;
      meteorY[meteorCounter] = random(0, height);
      meteorSize[meteorCounter] = random(30, 100);
    }

    // Hier wird getestet, ob der Meteor Links ankommt.

    if (meteorX[meteorCounter] < 0) {
      meteorS[meteorCounter] = random(1, 5);
      meteorX[meteorCounter] = width;
      meteorY[meteorCounter] = random(0, height);
      meteorSize[meteorCounter] = random(30, 100);
    }
    meteorCounter += 1;
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== ===== ===== Hier wird der Endscreen gezeichnet. ===== ===== ===== ===== ===== ===== ===== \\

public void drawEndscreen() {

  // Hier wird getestet, ob ie Leertaste gedrückt wird, um den highscorescreen zu öffnen.

  if (keyPressed) {
    if (key == ' ') {
      drawHighscoreScreen();
    }
  } else {

    // Hier wird der EndScreen gezeichnet.

    background(0);
    textAlign(CENTER);
    textSize(width / 10);
    fill(150, 0, 0);
    text("Game Over", width /2, height /2);

    fill(255);
    textSize(width/20);
    text("Dein Score: " + score, width /2, height /2 + 100);

    text("Dein Level: " + lvl, width /2, height /2 + 200);

    textSize(width/30);
    fill(100);
    text("Drücke die Leertaste, um die Highscores zu sehen.", width/2, height/2 + 300);
    text("Starte das Spiel neu, um eine weitere runde zu starten.", width/2, height/2 + 350);
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== Hier wird die Lebensanzeige generiert. == ===== ===== ===== ===== ===== ===== ===== ===== \\

public void drawHealthBar() {
  strokeWeight(30);
  stroke(100);
  fill(100);
  line(300, 35, 1050, 35); 

  strokeWeight(20);
  stroke(255, 0, 0);
  fill(255, 0, 0);
  line(300, 35, 300 + health*75, 35);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== Hier wird getestet, ob die Leben über 10, oder unter 1 sind. ==== ===== ===== ===== ===== \\

public void drawMaxHealth() {
  if (health > 10) {
    health = 10;
  }
  if (health < 1) {
    health = 0;
    inGame = false;
    Nickname();
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== Hier wird das Lebenspacket gezeichnet. == ===== ===== ===== ===== ===== ===== ===== ===== \\

public void drawPack() {
  packTimer += random(1, 3);

  // Hier wird getestet, ob der Timer eine bestimmte höhe erreicht, um das Pack zu platzieren.

  if (packTimer > 1000) {
    image(healpack, packX - (packSize/2), packY - (packSize/2), packSize, packSize);
    packX -= packS;

    // Hier wird getestet, ob das Pack mit dem Shuttle kollidiert.

    if (isColliding(shuttleSize, mouseX, mouseY, packSize, packX, packY)) {
      health += 2;
      packTimer = 0;
      packX = width;
      packY = random(0, height);
      packSize = 100;
      packS = 3;
    }

    // Hier wird getestet, ob das Pack Links ankommt.

    if (packX < 0) {
      packTimer = 0;
      packX = width;
      packY = random(0, height);
      packSize = 100;
      packS = 3;
    }
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== Hier wird die Laserkugel gezeichnet. ==== ===== ===== ===== ===== ===== ===== ===== ===== \\

public void drawBullet() {

  // Hier wird getestet, ob die Maustaste gedrückt ist, un d ob noch keine Kugel in der Luft ist.

  if (mousePressed == true && bulletInAir == false) {
    bulletInAir = true;
    bulletX = mouseX;
    bulletY = mouseY;
  }

  // Hier wird die Kugel platziert und bewegt.

  if (bulletInAir == true) {
    if (bulletX < width) {
      image(bullet, bulletX - bulletSize/2, bulletY - bulletSize/2, bulletSize, bulletSize);
      bulletX += bulletS;
    } else {
      bulletInAir = false;
    }
  }
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== ===== Hier kann der Spieler seinen Name festlegen. == ===== ===== ===== ===== ===== ===== ===== \\

public void Nickname() {

  // Hier wird der Spieler nach seinem Namen gefragt. 

  final String id = showInputDialog("Bitte gebe deinen Kürzel als Namen ein, um in die Highscoreliste eingetragen zu werden.");

  if (id == null)   exit();

  // Falls der Spieler nichts einträgt, wird er erinnert, etwas einzugeben.

  else if ("".equals(id)) {
    showMessageDialog(null, "Bitte etwas eingeben!", 
      "Alert", ERROR_MESSAGE);
    Nickname();
  } else if (ids.hasValue(id)) {

    // Wenn der Spieler etwas einträgt, wird er benachrichtigt, das sein Score eingetragen wurde.

    showMessageDialog(null, "Der score wurde Hinzugefügt.", 
      "Info", INFORMATION_MESSAGE);
  } else {
    showMessageDialog(null, "Der score wurde Hinzugefügt.", 
      "Info", INFORMATION_MESSAGE);

    ids.append(id);
  }

  // Hier wird der Name mit dem Score zu einem neuen Spieler Account gemacht, und zur Liste hinzugefügt.

  Player newplayer = new Player();
  newplayer.Name = id;
  newplayer.Score = score;
  highscore.add(newplayer);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== Hier wrid die Highscore-Tabelle am Ende gezeichnet. = ===== ===== ===== ===== ===== ===== ===== \\

public void drawHighscoreScreen() {
  background(0);
  textSize(width/40);
  textAlign(CENTER);
  fill(255);

  // Hier wird die Liste geordnet.

  Collections.sort(highscore);

  // Hier werden die ersten 10 Plätze der Liste angezeigt.

  if (highscore.size() > 0) {
    text("1. " + highscore.get(0).print(), width/2, 100);
  }

  if (highscore.size() > 1) {
    text("2. " + highscore.get(1).print(), width/2, 150);
  }

  if (highscore.size() > 2) {
    text("3. " + highscore.get(2).print(), width/2, 200);
  }

  if (highscore.size() > 3) {
    text("4. " + highscore.get(3).print(), width/2, 250);
  }

  if (highscore.size() > 4) {
    text("5. " + highscore.get(4).print(), width/2, 300);
  }
  if (highscore.size() > 5) {
    text("6. " + highscore.get(5).print(), width/2, 350);
  }

  if (highscore.size() > 6) {
    text("7. " + highscore.get(6).print(), width/2, 400);
  }

  if (highscore.size() > 7) {
    text("8. " + highscore.get(7).print(), width/2, 450);
  }

  if (highscore.size() > 8) {
    text("9. " + highscore.get(8).print(), width/2, 500);
  }

  if (highscore.size() > 9) {
    text("10. " + highscore.get(9      ).print(), width/2, 550);
  }

  // Hier werden erklärungen angezeigt.

  textAlign(LEFT);
  fill(200);
  textSize(width/40);
  text("Die Top 10 Scores von " + (highscore.size()) + " gespielten Spielen.", 50, 600);

  textAlign(CENTER);
  fill(100);
  textSize(width/30);
  text("Lasse die Leertaste los, um dein Spielergebnis zu sehen.", width/2, height/2 + 300);
  text("Starte das Spiel neu, um eine weitere runde zu starten.", width/2, height/2 + 350);
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== Hier wird der Highscore der Spieler gespeichert. ==== ===== ===== ===== ===== ===== ===== ===== \\

public void saveHighscore() {

  // Hier wird die Variable für die Anzahl der einzutragenen Highscores auf 0 gesetzt, und output wird der Datei "highscore.txt" zugewiesen.

  size = 0;
  output = createWriter("highscore.txt");

  while ( size < highscore.size() ) {

    // Hier werden die highscores in die Datei geschrieben.

    output.println(highscore.get(size).Name + " | " + highscore.get(size).Score);
    size += 1;
  }

  // Hier wird die Datei gespeichert und geschlossen.

  output.flush();
  output.close();
  size = 0;
}

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== \\
// ===== ===== ===== ===== ===== Hier wird die Datei mit den Highscores gelesen. ===== ===== ===== ===== ===== ===== ===== ===== \\

public void readHighscore() {

  // Hier wird eine Variable zum Zählen der Zeilen in Datei festgelegt, und reader wird der Datei "highscore.txt" zugewiesen.

  BufferedReader reader = createReader("highscore.txt");
  String line = null;
  try {
    while ((line = reader.readLine()) != null) {

      // Hier wird das gelesene in Name und Score geteilt, und als Variablen definiert.

      String[] pieces = splitTokens(line, "|");
      pieces[0] = trim(pieces[0]);
      pieces[1] = trim(pieces[1]);
      String NAME = pieces[0];
      int SCORE = Integer.parseInt(pieces[1]);
      Player newplayer = new Player();

      // Hier werden die gelesenen Daten in einen newplayer gewandelt.

      newplayer.Name = NAME;
      newplayer.Score = SCORE;
      highscore.add(newplayer);
    }

    // Hier wird der reader beendet.

    reader.close();
  } 
  catch (IOException e) {
    e.printStackTrace();
  }
}
// ===== Stars ===== \\

int MAXstars = 200;
float starX[] = new float[MAXstars];
float starY[] = new float[MAXstars];
float starS[] = new float[MAXstars];
float starSize[] = new float[MAXstars];

// ===== ===== ===== ===== \\


// ===== Planets ===== \\

int MAXplanets = PApplet.parseInt(random(3, 6));
float planetX[] = new float[MAXplanets];
float planetY[] = new float[MAXplanets];
float planetS[] = new float[MAXplanets];
float planetSize[] = new float[MAXplanets];
int planetC[] = new int[MAXplanets];

// ===== ===== ===== ===== \\


// ===== SpaceShuttle ===== 

float shuttleX;
float shuttleY;
float shuttleSize = 100;
PImage shuttle;
// ===== ===== ===== ===== \\


// ===== Meteors ===== \\

int MAXmeteors = 10000;
float meteorX[] = new float[MAXmeteors];
float meteorY[] = new float[MAXmeteors];
float meteorS[] = new float[MAXmeteors];
float meteorSize[] = new float[MAXmeteors];
PImage meteor;

// ===== ===== ===== ===== \\


// ===== Level - Score - Health ===== \\

int score;
int lvl = 1;
int lvlTimer;
int health = 10;

// ===== ===== ===== ===== \\


// =====  is Projectile colliding with Object? ===== \\

public boolean isColliding(float objectSize, float objectPosX, float objectPosY, float projectileSize, float projectilePosX, float projectilePosY) {
  boolean returnValue = false;   

  //  Falls die untere Seite des Objekts mit der oberen Seite des Projektils kolliediert. 
  if ( projectilePosX - projectileSize/2 < objectPosX + objectSize/2) {

    //  Falls die obere Seite des Objekts mit der unteren Seite des Projektils kolliediert. 
    if ( projectilePosX + projectileSize/2 > objectPosX - objectSize/2) {

      //  Falls die  Rückseite des Objekts mit der vordere Seite des Projektils kolliediert. 
      if ( projectilePosY + projectileSize/2 > objectPosY - objectSize/2) {

        //  Falls die vordere Seite des Objekts mit der Rückseite des Projektils kolliediert. 
        if ( projectilePosY - projectileSize/2 < objectPosY + objectSize/2) {
          returnValue = true;
        }
      }
    }
  }
  return returnValue;
}

// ===== ===== ===== ===== \\


// ===== Healpack ===== \\

float packX;
float packY;
float packS;
float packSize;
int packTimer = 0;
PImage healpack;

// ===== ===== ===== ===== \\


// ===== Bullet ===== \\

boolean bulletInAir = false;
float bulletX;
float bulletY;
float bulletS;
float bulletSize;
PImage bullet;

// ===== ===== ===== ===== \\


// ===== Nickname und Highscore ===== \\

final StringList ids = new StringList( new String[] {
  } );

LinkedList<Player> highscore = new LinkedList <Player>();

// ===== ===== ===== ===== \\


// ===== Highscorelist Size, is game started?, Print in file with output ===== \\
boolean inGame = true;
PrintWriter output;
int size;

public void draw() {
  if (inGame == true) {
    background(0);
    stroke(255);
    drawStars();
    drawPlanets();
    drawBullet();
    drawShuttle();
    drawMeteors();
    drawHealthBar();
    drawScore();
    drawLevel();
    drawPack();
    drawMaxHealth();
  }
  if (inGame == false) {
    saveHighscore();
    drawEndscreen();
  }
}
// Hier werden die Libraries importiert






// Hier wird die Bildschirmgröße festgelegt.

public void settings() {
  int ScreenW = displayWidth  /  3 * 2;
  int ScreenH  = displayHeight  /  3 * 2;

  size(ScreenW, ScreenH);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "void_setup" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
