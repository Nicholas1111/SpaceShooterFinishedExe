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
