// ===== Stars ===== \\

int MAXstars = 200;
float starX[] = new float[MAXstars];
float starY[] = new float[MAXstars];
float starS[] = new float[MAXstars];
float starSize[] = new float[MAXstars];

// ===== ===== ===== ===== \\


// ===== Planets ===== \\

int MAXplanets = int(random(3, 6));
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

boolean isColliding(float objectSize, float objectPosX, float objectPosY, float projectileSize, float projectilePosX, float projectilePosY) {
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
