package cse417;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Program to find the lineup that has the highest projected points, subject to
 * constraints on the total budget and number at each position.
 */
public class OptimalLineup {

  /** Maximum that can be spent on players in a lineup. */
  private static final int BUDGET = 60000;

  // Number of players that must be played at each position:
  private static final int NUM_QB = 1;
  private static final int NUM_RB = 2;
  private static final int NUM_WR = 3;
  private static final int NUM_TE = 1;
  private static final int NUM_K = 1;
  private static final int NUM_DEF = 1;

  /** Entry point for a program to compute optimal lineups. */
  public static void main(String[] args) throws Exception {
    ArgParser argParser = new ArgParser("OptimalLineup");
    argParser.addOption("no-high-correlations", Boolean.class);
    args = argParser.parseArgs(args, 1, 1);

    // Parse the list of players from the file given in args[0]
    List<Player> players = new ArrayList<Player>();
    CsvParser parser = new CsvParser(args[0], false, new Object[] {
          // name, position, team, opponent
          String.class, String.class, String.class, String.class,
          // points, price, floor, ceiling, stddev
          Float.class, Integer.class, Float.class, Float.class, Float.class
        });
    while (parser.hasNext()) {
      String[] row = parser.next();
      players.add(new Player(row[0], Position.valueOf(row[1]), row[2], row[3],
          Integer.parseInt(row[5]), Float.parseFloat(row[4]),
          Float.parseFloat(row[8])));
    }

    List<Player> roster;
    if (argParser.hasOption("no-high-correlations")) {
      roster = findOptimalLineupWithoutHighCorrelations(players, "");
    } else { 
      roster = findOptimalLineup(players);
    }

    displayLineup(roster);
  }

  /** Returns the players in the optimal lineup (in any order). */
  private static List<Player> findOptimalLineup(List<Player> allPlayers) {
    // TODO: implement this using dynamic programming
    return new ArrayList<Player>();
  }

  /**
   * Returns the players in the optimal lineup subject to the constraint that
   * there are no players with high correlations, i.e., no QB-WR, QB-K, or
   * K-DEF from the same team.
   */
  private static List<Player> findOptimalLineupWithoutHighCorrelations(
      List<Player> allPlayers, String label) {
    // TODO(extra credit): implement this
    return new ArrayList<Player>();
  }

  /** Returns a pair that are highly correlated or null if none. */
  private static Player[] getHighCorrelations(List<Player> roster) {
    Player qb = roster.stream()
        .filter(p -> p.getPosition() == Position.QB).findFirst().get();

    List<Player> wrs = roster.stream()
        .filter(p -> p.getPosition() == Position.WR)
        .sorted((p,q) -> q.getPrice() - p.getPrice())
        .collect(Collectors.toList());
    for (Player wr : wrs) {
      if (qb.getTeam().equals(wr.getTeam()))
        return new Player[] { qb, wr };
    }

    Player k = roster.stream()
        .filter(p -> p.getPosition() == Position.K).findFirst().get();
    if (qb.getTeam().equals(k.getTeam()))
      return new Player[] { qb, k };

    Player def = roster.stream()
        .filter(p -> p.getPosition() == Position.DEF).findFirst().get();
    if (k.getTeam().equals(def.getTeam()))
      return new Player[] { k, def };

    return null;
  }

  /** Displays a lineup, which is assumed to meet the position constraints. */
  private static void displayLineup(List<Player> roster) {
    if (roster == null) {
      System.out.println("*** No solution");
      return;
    }

    List<Player> qbs = roster.stream()
        .filter(p -> p.getPosition() == Position.QB)
        .collect(Collectors.toList());
    List<Player> rbs = roster.stream()
        .filter(p -> p.getPosition() == Position.RB)
        .sorted((p,q) -> q.getPrice() - p.getPrice())
        .collect(Collectors.toList());
    List<Player> wrs = roster.stream()
        .filter(p -> p.getPosition() == Position.WR)
        .sorted((p,q) -> q.getPrice() - p.getPrice())
        .collect(Collectors.toList());
    List<Player> tes = roster.stream()
        .filter(p -> p.getPosition() == Position.TE)
        .collect(Collectors.toList());
    List<Player> ks = roster.stream()
        .filter(p -> p.getPosition() == Position.K)
        .collect(Collectors.toList());
    List<Player> defs = roster.stream()
        .filter(p -> p.getPosition() == Position.DEF)
        .collect(Collectors.toList());

    assert qbs.size() == NUM_QB;
    assert rbs.size() == NUM_RB;
    assert wrs.size() == NUM_WR;
    assert tes.size() == NUM_TE;
    assert ks.size() == NUM_K;
    assert defs.size() == NUM_DEF;

    assert roster.stream().mapToInt(p -> p.getPrice()).sum() <= BUDGET;

    System.out.printf(" QB  %s\n", describePlayer(qbs.get(0)));
    System.out.printf("RB1  %s\n", describePlayer(rbs.get(0)));
    System.out.printf("RB2  %s\n", describePlayer(rbs.get(1)));
    System.out.printf("WR1  %s\n", describePlayer(wrs.get(0)));
    System.out.printf("WR2  %s\n", describePlayer(wrs.get(1)));
    System.out.printf("WR3  %s\n", describePlayer(wrs.get(2)));
    System.out.printf(" TE  %s\n", describePlayer(tes.get(0)));
    System.out.printf("  K  %s\n", describePlayer(ks.get(0)));
    System.out.printf("DEF  %s\n", describePlayer(defs.get(0)));
    System.out.printf("*** Totals: price $%d, points %.1f +/- %.1f\n",
        roster.stream().mapToInt(p -> p.getPrice()).sum(),
        roster.stream().mapToDouble(p -> p.getPointsExpected()).sum(),
        Math.sqrt(roster.stream().mapToDouble(
            p -> p.getPointsVariance()).sum()));
  }

  /** Returns a short description of a player with price and opponent. */
  private static String describePlayer(Player p) {
    return String.format("%-20s $%-5d %3s %2s %3s", p.getName(), p.getPrice(),
        p.getTeam(), p.isAtHome() ? "vs" : "at", p.getOpponent());
  }
}
