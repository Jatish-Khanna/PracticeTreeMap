import java.util.*;

/**
 * Bonus Challenge:
 * Implement the operations so that the time complexity for each operation
 * (add points, get top players, get rank, remove player) is logarithmic or better.
 */
public class LeaderBoardSystem {

  static class Player {
    String name;
    int score;

    Player(String name, int score) {
      this.name = name;
      this.score = score;
    }

    public String toString() {
      return name + " " + score;
    }
  }

  TreeMap<Integer, Set<Player>> leaderboard = new TreeMap<>(Comparator.reverseOrder());
  Map<String, Player> players = new HashMap<>();

  /**
   * getRank: O(S) — This is the rank calculation method
   * that iterates over the scores, which is at most the number of distinct scores (S).
   *
   * @param addPlayer - the player name
   * @return - rank
   */
  private int getRank(String addPlayer) {
    Player player = null;
    if (players.containsKey(addPlayer)) {
      player = players.get(addPlayer);
    }
    return player == null ? -1 : computeRank(player.score);
  }

  private int computeRank(int score) {
//    var highest = leaderboard.firstKey();

//    int rank = leaderboard.subMap(highest, true, score, true).size(); //  including the lowest
    return leaderboard.headMap(score, true).size(); //  including the lowest
  }

  /**
   * getTopPlayers: O(N) — In the worst case, you iterate over the top N players.
   *
   * @param n - top players with N rank
   * @return - list of players
   */
  private List<Player> getTopPlayers(int n) {
    int count = 0;
    List<Player> playersWithScore = new ArrayList<>();

    for (Map.Entry<Integer, Set<Player>> foundPlayers : leaderboard.entrySet()) {
      if (count >= n) {
        break;
      }
      playersWithScore.addAll(foundPlayers.getValue());
      ++count;
    }
    return playersWithScore;
  }

  /**
   * removePlayer: O(log N) —
   * Removing a player from the leaderboard is logarithmic in the number of distinct scores.
   *
   * @param removePlayer - remove the player name
   */
  private void removePlayer(String removePlayer) {
    if (!players.containsKey(removePlayer)) {
      return;
    }
    Player player = players.remove(removePlayer); // player removed and marked as removed in leaderboard
    leaderboard.get(player.score).remove(player); // remove from the leaderboard
    if (leaderboard.get(player.score).isEmpty()) {
      leaderboard.remove(player.score);
    }
  }

  /**
   * addPoints: O(log N) — Adding or updating a player involves removing
   * the player from the old score group and adding them to the new score group.
   * Both operations are logarithmic in the size of the leaderboard.
   *
   * @param name - name of the player
   * @param i    - score of the player
   */
  private void addPoints(String name, int i) {
    Player player;
    if (!players.containsKey(name)) {
      player = new Player(name, i);
    } else {
      player = players.get(name); // get the player with name
      removePlayer(name); // remove from previous score group
      player.score += i; // add the score
    }
    // put the player for lookup
    players.put(name, player);
    // add the player to the score group
    leaderboard.computeIfAbsent(player.score, (withScore) -> new HashSet<>()).add(player);
  }

  public static void main(String[] args) {
    LeaderBoardSystem leaderboard = new LeaderBoardSystem();
    leaderboard.addPoints("Alice", 100);
    leaderboard.addPoints("Bob", 200);
    leaderboard.addPoints("Charlie", 150);


// Top 2 players
    List<Player> topPlayers = leaderboard.getTopPlayers(2);
    System.out.println("Top Players: " + topPlayers);

    leaderboard.addPoints("Alice", 70);
    System.out.println("Added 70 points for Alice");


// Expected output: ["Bob", "Charlie"]

// Get rank of Alice
    int rank = leaderboard.getRank("Alice");
    System.out.println("Rank: Alice: " + rank);
// Expected output: 3 (since Bob and Charlie have higher scores)

// Remove Bob
    leaderboard.removePlayer("Bob");
    System.out.println("Removed player: Bob");


// Get top 2 players after removing Bob
    topPlayers = leaderboard.getTopPlayers(10);
    System.out.println("Top Players: " + topPlayers);

// Expected output: ["Charlie", "Alice"]
    leaderboard.removePlayer("Charlie");
    System.out.println("Removed player: Charlie");
    rank = leaderboard.getRank("Charlie");
    System.out.println("Rank: Charlie" + rank);
  }
}
