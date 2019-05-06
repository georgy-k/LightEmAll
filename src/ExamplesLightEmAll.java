import java.util.*;

import tester.*;
import javalib.worldimages.*;

public class ExamplesLightEmAll {
  LightEmAll testGame1;
  LightEmAll testGame2;
  LightEmAll testGame3;
  LightEmAll testGame4;
  LightEmAll testGame5;

  ArrayList<GamePiece> nodes1;
  GamePiece cell1;
  GamePiece cell2;
  GamePiece cell3;
  GamePiece cell4;

  GamePiece g1 = new GamePiece(0, 0, true, false, false, false, false, false);
  GamePiece g2 = new GamePiece(1, 1, false, true, false, false, false, false);
  GamePiece g3 = new GamePiece(0, 1, false, false, true, false, false, false);
  GamePiece g4 = new GamePiece(1, 0, false, false, false, true, false, false);
  GamePiece g5 = new GamePiece(2, 2, true, false, true, false, false, false);

  GamePiece gconnect1 = new GamePiece(0, 0, false, false, false, true, false, true);
  GamePiece gconnect2 = new GamePiece(1, 0, false, false, true, false, false, false);
  GamePiece gconnect3 = new GamePiece(1, 0, true, true, false, false, false, false);

  Random rand = new Random();

  void initConditions() {
    this.testGame1 = new LightEmAll(8, 8, false);
    this.testGame2 = new LightEmAll(14, 14, false);
    this.cell1 = new GamePiece(1, 1, false, true, false, false, false, false);
    this.cell2 = new GamePiece(1, 2, true, false, false, true, false, false);
    this.cell3 = new GamePiece(2, 1, false, true, false, false, true, true);
    this.cell4 = new GamePiece(2, 2, true, false, true, false, false, false);
    this.nodes1 = new ArrayList<GamePiece>(Arrays.asList(cell1, cell2, cell3, cell4));

    this.testGame3 = new LightEmAll(4, 4, false);
    this.testGame4 = new LightEmAll(8, 8);
    this.testGame5 = new LightEmAll(7, 7, false);

    this.g1 = new GamePiece(0, 0, true, false, false, false, false, false);
    this.g2 = new GamePiece(1, 1, false, true, false, false, false, false);
    this.g3 = new GamePiece(0, 1, false, false, true, false, false, false);
    this.g4 = new GamePiece(1, 0, false, false, false, true, false, false);
    this.g5 = new GamePiece(2, 2, true, false, true, false, false, false);

    this.gconnect1 = new GamePiece(0, 0, false, false, false, true, false, true);
    this.gconnect2 = new GamePiece(1, 0, false, false, true, false, false, false);
    this.gconnect3 = new GamePiece(1, 0, true, true, false, false, false, false);
  }

  boolean testCreateNodes(Tester t) {
    initConditions();
    return t.checkExpect(testGame1.nodes.get(0),
        new GamePiece(1, 1, false, false, false, true, false, true));
  }

  boolean testisPowered(Tester t) {
    initConditions();
    GamePiece powerStation = this.testGame1.board.get(0).get(0);
    testGame1.isPowered(powerStation, false);
    return t.checkExpect(this.testGame1.board.get(2).get(3).powered, true)
        && t.checkExpect(this.testGame1.board.get(4).get(5).powered, true)
        && t.checkExpect(this.testGame1.board.get(6).get(1).powered, true);
  }

  boolean testBottomHelper(Tester t) {
    initConditions();
    ArrayList<GamePiece> nodes = testGame1.nodes;
    return t.checkExpect(testGame1.bottomHelper(nodes.get(0)), nodes.get(8))
        && t.checkExpect(testGame1.bottomHelper(nodes.get(10)), nodes.get(10))
        && t.checkExpect(testGame1.bottomHelper(nodes.get(20)), nodes.get(28));
  }

  boolean testTopHelper(Tester t) {
    initConditions();
    ArrayList<GamePiece> nodes = testGame1.nodes;
    return t.checkExpect(testGame1.topHelper(nodes.get(0)), nodes.get(0))
        && t.checkExpect(testGame1.topHelper(nodes.get(10)), nodes.get(2))
        && t.checkExpect(testGame1.topHelper(nodes.get(20)), nodes.get(12));
  }

  boolean testLeftHelper(Tester t) {
    initConditions();
    ArrayList<GamePiece> nodes = testGame1.nodes;
    return t.checkExpect(testGame1.leftHelper(nodes.get(0)), nodes.get(0))
        && t.checkExpect(testGame1.leftHelper(nodes.get(33)), nodes.get(33))
        && t.checkExpect(testGame1.leftHelper(nodes.get(34)), nodes.get(34));
  }

  boolean testRightHelper(Tester t) {
    initConditions();
    ArrayList<GamePiece> nodes = testGame1.nodes;
    return t.checkExpect(testGame1.rightHelper(nodes.get(0)), nodes.get(0))
        && t.checkExpect(testGame1.rightHelper(nodes.get(33)), nodes.get(33))
        && t.checkExpect(testGame1.rightHelper(nodes.get(35)), nodes.get(35));
  }

  void testCheckIfWon(Tester t) {
    initConditions();
    testGame1.checkIfWon();
    testGame2.checkIfWon();
    t.checkExpect(testGame1.isWon, false);
    t.checkExpect(testGame2.isWon, false);
  }

  boolean testFractalConnect(Tester t) {
    initConditions();
    ArrayList<GamePiece> nodes = testGame1.nodes;
    return t.checkExpect(nodes.get(0), new GamePiece(1, 1, false, false, false, true, false, true))
        && t.checkExpect(nodes.get(3), new GamePiece(1, 4, false, false, false, true, true, true))
        && t.checkExpect(nodes.get(5), new GamePiece(1, 6, false, false, false, true, false, false))
        && t.checkExpect(nodes.get(10), new GamePiece(2, 3, false, true, true, false, false, true));
  }

  boolean testMutualConnect(Tester t) {
    GamePiece g1 = new GamePiece(1, 1, false, false, false, false, false, false);
    GamePiece g2 = new GamePiece(4, 5, false, false, false, false, false, false);
    GamePiece g3 = new GamePiece(3, 2, false, false, false, false, false, false);
    GamePiece g4 = new GamePiece(6, 2, false, false, false, false, false, false);

    g1.mutualConnect(g2, "bottom");
    g3.mutualConnect(g4, "right");

    return t.checkExpect(g1.bottom, true) && t.checkExpect(g2.top, true)
        && t.checkExpect(g3.right, true) && t.checkExpect(g4.left, true);
  }

  void testRotate(Tester t) {
    this.initConditions();
    this.g1.rotate();
    this.g2.rotate();
    this.g3.rotate();
    this.g4.rotate();
    this.g5.rotate();

    t.checkExpect(this.g1, new GamePiece(0, 0, false, false, true, false, false, false));
    t.checkExpect(this.g2, new GamePiece(1, 1, false, false, false, true, false, false));
    t.checkExpect(this.g3, new GamePiece(0, 1, false, true, false, false, false, false));
    t.checkExpect(this.g4, new GamePiece(1, 0, true, false, false, false, false, false));
    t.checkExpect(this.g5, new GamePiece(2, 2, false, true, true, false, false, false));
  }

  boolean testConnect(Tester t) {
    this.initConditions();
    ArrayList<GamePiece> board = testGame1.nodes;
    return t.checkExpect(board.get(0), new GamePiece(1, 1, false, false, false, true, false, true))
        && t.checkExpect(board.get(27), new GamePiece(4, 4, true, false, true, false, false, true))
        && t.checkExpect(board.get(5),
            new GamePiece(1, 6, false, false, false, true, false, false));
  }

  boolean testCreateBoard(Tester t) {
    this.initConditions();
    ArrayList<GamePiece> board = testGame3.nodes;
    return t.checkExpect(board.get(0), new GamePiece(1, 1, false, false, false, true, false, true));
  }

  void testGetIndex(Tester t) {
    this.initConditions();
    t.checkExpect(this.testGame4.getIndex(new Posn(0, 0)), 0);
    t.checkExpect(this.testGame4.getIndex(new Posn(120, 75)), 11);
    t.checkExpect(this.testGame4.getIndex(new Posn(120, 0)), 3);
    t.checkExpect(testGame1.getIndex(new Posn(40, 100)), 17);
    t.checkExpect(testGame1.getIndex(new Posn(160, 20)), 4);
    t.checkExpect(testGame1.getIndex(new Posn(200, 300)), 61);
    t.checkExpect(testGame1.getIndex(new Posn(480, 150)), 36);
  }

  // test onMousePressed - how?
  void testOnMousePressed(Tester t) {
    this.initConditions();
    this.testGame4.onMousePressed(new Posn(0, 0));
  }

  void testPlacePowerCell(Tester t) {
    this.initConditions();
    this.testGame3.placePowerCell();
    t.checkExpect(this.testGame3.nodes.get(1).powerStation, true);
    t.checkExpect(this.testGame3.nodes.get(1).powered, true);
  }

  // test scramble - how?
  // checking it scrambles the correct number of GamePieces
  /*
   * void testScramble(Tester t) { this.initConditions();
   * t.checkExpect(this.testGame4.scrambleTest(new Random(), 4), 4);
   * t.checkExpect(this.testGame5.scrambleTest(new Random(), 9), 9); }
   */

  boolean testDiameter(Tester t) {
    initConditions();
    return t.checkExpect(testGame2.diameter(testGame2.nodes.get(0), false), 53)
        && t.checkExpect(testGame1.diameter(testGame1.nodes.get(0), false), 29);
  }

  // Runs 8X8 UNscrambled
  /*
   void testBigBangStart(Tester t) { initConditions();
   testGame1.bigBang(testGame1.width * 40, testGame1.height * 40, 0.1); }
   */
   
  // Runs 7X7 UNscrambled
  /*
   * void testBigBangStart(Tester t) { initConditions();
   * testGame5.bigBang(testGame5.width * 40, testGame5.height * 40, 0.1); }
   */

  // Runs 8X8 scrambled
 
   public void testBigBangStart(Tester t) { initConditions();
   testGame4.bigBang(testGame4.width * 40, testGame4.height * 40, 0.1); }
  
}
