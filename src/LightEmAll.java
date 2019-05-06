
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.*;

class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  GamePiece powerCell;
  boolean isWon;

  LightEmAll(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = createBoard(height, width, this.powerCol, this.powerRow);
    this.powerRow = 1;
    this.powerCol = this.width / 2;
    this.nodes = createNodes(this.board);
    this.mst = new ArrayList<Edge>();
    this.powerCell = this.board.get(this.powerRow - 1).get(this.powerCol - 1);
    checkIfWon();

    Random rand = new Random();
    fractalConnect(0, this.height, 0, this.width);
    // mannualConnect(this.nodes);
    placePowerCell();
    this.radius = diameter(this.nodes.get(0), false) / 2 + 1;
    scramble(this.nodes, rand);
    // isPowered(this.powerCell);
    isPowered(this.powerCell, false);
  }

  // Constructor for testing purposes, without scramble
  LightEmAll(int width, int height, boolean isWon) {
    this.width = width;
    this.height = height;
    this.board = createBoard(height, width, this.powerCol, this.powerRow);
    this.powerRow = 1;
    this.powerCol = this.width / 2;
    this.nodes = createNodes(this.board);
    this.mst = new ArrayList<Edge>();
    this.powerCell = this.board.get(this.powerRow - 1).get(this.powerCol - 1);
    this.isWon = false;
    // Old mannual connect
    // mannualConnect(this.nodes);
    fractalConnect(0, this.height, 0, this.width);
    this.radius = diameter(this.nodes.get(0), false) / 2 + 1;
    placePowerCell();
    isPowered(this.powerCell, false);
  }

  // Creates a 2 dimensional array of cells, where each inner array represents a
  // separate row
  ArrayList<ArrayList<GamePiece>> createBoard(int rows, int columns, int powerRow,
      int powerColumn) {
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();

    for (int i = 1; i <= rows; i++) {
      ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();
      board.add(nodes);

      for (int j = 1; j <= columns; j++) {
        nodes.add(new GamePiece(i, j, false, false, false, false, false, false));
      }

    }
    return board;
  }

  // Flattens 2d array into 1d array by copying every inner array into the "nodes"
  // array returns nodes when loop is over
  ArrayList<GamePiece> createNodes(ArrayList<ArrayList<GamePiece>> board) {
    ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();

    for (ArrayList<GamePiece> ag : board) {
      new ArrayUtils().copy(nodes, ag);
    }
    return nodes;
  }

  // Makes the GamePiece at the given powerRow and powerCol the
  // powerStation
  // EFFECT: modifies a GamePiece's powerStation and powered fields
  void placePowerCell() {
    GamePiece powerCell = this.board.get(powerRow - 1).get(powerCol - 1);
    powerCell.powerStation = true;
    powerCell.powered = true;
  }

  // Sets manually generated connections so that all cell except the middle row
  // have vertical connection. Middle row also has horizontal connection
  // EFFECT: modifies the connection fields of each GamePiece in the
  // given board
  void mannualConnect(ArrayList<GamePiece> board) {
    int rows = this.height;
    int columns = this.width;
    int intersect = rows / 2 + 1;

    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows - 1; j++) {
        GamePiece current = board.get(j * columns + i);

        // GamePiece topNeighbor = board.get(j * columns + i - columns);
        GamePiece bottomNeighbor = board.get(j * columns + i + columns);
        // GamePiece leftNeighbor = board.get(j * columns + i - 1);
        GamePiece rightNeighbor = board.get(j * columns + i + 1);

        if (current.col != columns) {
          if (current.row != intersect) {
            current.mutualConnect(bottomNeighbor, "bottom");
          }
          else {
            current.mutualConnect(bottomNeighbor, "bottom");
            current.mutualConnect(rightNeighbor, "right");
          }
        }
        else {
          current.mutualConnect(bottomNeighbor, "bottom");
        }
      }
    }
  }

  // Goes through each GamePiece and randomly rotates it
  // There is a 50% chance that cell will be rotated
  // EFFECT: potentially calls rotate, modifying
  // a cell's connection fields by rotating 90 degrees clockwise
  void scramble(ArrayList<GamePiece> board, Random rand) {
    for (GamePiece g : board) {
      int rotation = rand.nextInt(2);
      if (rotation == 1) {
        g.rotate();
      }
    }

  }

  // Goes through each GamePiece and checks if it is powered if all GamePieces are
  // powered, sets isWon to true
  // EFFECT: modifies the isWon field depending on
  // if all GamePieces are powered or not
  void checkIfWon() {
    for (GamePiece g : this.nodes) {
      if (!g.powered) {
        this.isWon = false;
        return;
      }
    }
    this.isWon = true;

  }

  // Draws the game by delegating drawing each cell to GamePiece class, then
  // places the resulting images on the empty scene
  // Throws the Win message if game is won
  public WorldScene makeScene() {
    WorldScene base = this.getEmptyScene();
    TextImage winMessage = new TextImage("You Win!", 22, FontStyle.BOLD, Color.GREEN);

    int idx = 0;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        WorldImage drawThis = this.nodes.get(idx).drawGamePiece();
        base.placeImageXY(drawThis, j * (int) drawThis.getWidth() + ((int) drawThis.getWidth() / 2),
            i * (int) drawThis.getHeight() + ((int) drawThis.getHeight() / 2));
        idx++;
      }
    }
    if (this.isWon) {
      base.placeImageXY(winMessage, this.width / 2 * 40, this.height / 2 * 40);
    }
    return base;
  }

  // Rotates the GamePiece user has clicked on and checks if game is won
  // After every click shuts lights off and reruns BSF search from power station
  // again
  // EFFECT: calls rotate method that modifies connection fields of the GamePiece
  // by rotating them clockwise
  public void onMousePressed(Posn pos) {
    if (!this.isWon) {
      if (pos.x < this.width * 40 && pos.y < this.height * 40) {
        this.nodes.get(getIndex(pos)).rotate();
        for (GamePiece g : this.nodes) {
          g.powered = false;
        }
        isPowered(this.powerCell, false);
        this.checkIfWon();
      }
    }
  }

  // Accepts a user's key input and moves the powerCell according to the direction
  // of the keys they press
  // EFFECT: modifies powerCell field by making it be the cell immediately to the
  // left, right, top or bottom or the current powerCell (depends on key pressed)
  // makes the old powerCell a GamePiece
  // Checks if the game has been won after moving the powerCell
  public void onKeyEvent(String key) {
    if (!this.isWon) {
      if (key.equals("right")) {
        // returns the right neighbor if there is one,
        // and the current powerCell if there is not
        GamePiece newPowerCell = this.rightHelper(this.powerCell);
        powerCell.powerStation = false;
        this.powerCell = newPowerCell;
        this.makePowerCell(newPowerCell);
        this.isPowered(this.powerCell, false);
        this.checkIfWon();
      }

      if (key.equals("left")) {
        // returns the left neighbor if there is one,
        // and the current powerCell if there is not
        GamePiece newPowerCell = this.leftHelper(this.powerCell);
        powerCell.powerStation = false;
        this.powerCell = newPowerCell;
        this.makePowerCell(newPowerCell);
        this.isPowered(this.powerCell, false);
        this.checkIfWon();
      }

      if (key.equals("up")) {
        // returns the top neighbor if there is one,
        // and the current powerCell if there is not
        GamePiece newPowerCell = this.topHelper(this.powerCell);
        powerCell.powerStation = false;
        this.powerCell = newPowerCell;
        this.makePowerCell(newPowerCell);
        this.isPowered(this.powerCell, false);
        this.checkIfWon();
      }

      if (key.equals("down")) {
        // returns the bottom neighbor if there is one,
        // and the current powerCell if there is not
        GamePiece newPowerCell = this.bottomHelper(this.powerCell);
        powerCell.powerStation = false;
        this.powerCell = newPowerCell;
        this.makePowerCell(newPowerCell);
        this.isPowered(this.powerCell, false);
        this.checkIfWon();
      }
    }
  }

  // Makes the given GamePiece the powerCell by modifying the powerCell field.
  // EFFECT: Modifies the powerCell field so that the old
  // powerCell is no longer the powerCell, and the new powerCell is the given
  // GamePiece
  void makePowerCell(GamePiece g) {
    // modify locations to point to this cell
    this.powerCell = g;
    this.powerCell.powerStation = false;
    this.powerCol = g.col;
    this.powerRow = g.row;
    this.placePowerCell();
  }

  // Transforms mouse position into the corresponding GamePiece's index
  public int getIndex(Posn pos) {
    return pos.x / 40 + pos.y / 40 * this.width;
  }

  // Returns the left neighbor of current cell if it has a right connection,
  // returns current cell of there is no left neighbor
  GamePiece leftHelper(GamePiece current) {
    if (current.col == 1) {
      return current;
    }
    else {
      if (board.get(current.row - 1).get(current.col - 2).right) {

        return (board.get(current.row - 1).get(current.col - 2));
      }
      else {
        return current;
      }
    }
  }

  // Returns the right neighbor of current cell if it has a left connection,
  // returns current cell of there is no left neighbor
  GamePiece rightHelper(GamePiece current) {
    if (current.col == this.width) {
      return current;
    }
    else {
      if (board.get(current.row - 1).get(current.col).left) {

        return (board.get(current.row - 1).get(current.col));
      }
      else {
        return current;
      }
    }
  }

  // Returns the top neighbor of current cell if it has a bottom connection
  // returns current cell of there is no top neighbor
  GamePiece topHelper(GamePiece current) {
    if (current.row == 1) {
      return current;
    }
    else {
      if (board.get(current.row - 2).get(current.col - 1).bottom) {

        return (board.get(current.row - 2).get(current.col - 1));
      }
      else {
        return current;
      }
    }
  }

  // Returns the bottom neighbor of current cell if it has a top connection
  // returns current cell of there is no bottom neighbor
  GamePiece bottomHelper(GamePiece current) {
    if (current.row == this.height) {
      return current;
    }
    else {
      if (board.get(current.row).get(current.col - 1).top) {

        return (board.get(current.row).get(current.col - 1));
      }
      else {
        return current;
      }
    }
  }

  // Uses a subdivision algorithm to generate a board composed of 2 x 2 fractals
  // EFFECT: checks the size of the board (based on number of columns and rows)
  // connects the base case fractal by the left, right, and bottom
  // connects all fractals by the left, right, and bottom
  void fractalConnect(int begRow, int endRow, int begCol, int endCol) {
    if ((endRow - begRow) > 2 && (endCol - begCol) > 2) {
      this.fractalConnect(begRow, (endRow - (endRow - begRow) / 2), begCol,
          (endCol - (endCol - begCol) / 2));
      this.fractalConnect(begRow, (endRow - (endRow - begRow) / 2),
          (begCol + (endCol - begCol) / 2), endCol);
      this.fractalConnect((begRow + (endRow - begRow) / 2), endRow, begCol,
          (endCol - (endCol - begCol) / 2));
      this.fractalConnect((begRow + (endRow - begRow) / 2), endRow,
          (begCol + (endCol - begCol) / 2), endCol);

      // Connect left
      this.board.get(endRow - (endRow - begRow) / 2 - 1).get(begCol)
          .mutualConnect(this.board.get(endRow - (endRow - begRow) / 2).get(begCol), "bottom");
      // Connect right
      this.board.get(endRow - (endRow - begRow) / 2 - 1).get(endCol - 1)
          .mutualConnect(this.board.get(endRow - (endRow - begRow) / 2).get(endCol - 1), "bottom");
      // COnnect bottom
      this.board.get(endRow - 1).get((endCol - (endCol - begCol) / 2) - 1)
          .mutualConnect(this.board.get(endRow - 1).get(endCol - (endCol - begCol) / 2), "right");
    }
    else {
      // Connect left
      this.board.get(begRow).get(begCol).mutualConnect(this.board.get(begRow + 1).get(begCol),
          "bottom");
      // Connect right
      this.board.get(begRow).get(begCol + 1)
          .mutualConnect(this.board.get(begRow + 1).get(endCol - 1), "bottom");
      // Connect bottom
      this.board.get(begRow + 1).get(begCol)
          .mutualConnect(this.board.get(begRow + 1).get(endCol - 1), "right");
    }
  }

  // Computes the distance of the path to the farthest GamePiece while following
  // wires from the powerCell. Finds the first farthest point and checks
  // one more time just in case a farther path exists
  int diameter(GamePiece from, boolean secondTime) {
    ArrayList<GamePiece> alreadySeen = new ArrayList<GamePiece>();
    Deque<GamePiece> workList = new Deque<GamePiece>();
    Deque<GamePiece> neighborList = new Deque<GamePiece>();
    GamePiece farthest = from;

    int distance = 0;

    workList.addAtTail(from);

    while (workList.size() != 0) { // add radius < dist

      while (workList.size() != 0) {
        GamePiece next = workList.removeFromHead();
        alreadySeen.add(next);
        farthest = next; // farest.powered = true

        if (next.left) {
          if (!alreadySeen.contains(this.leftHelper(next))) {
            neighborList.addAtTail(this.leftHelper(next));
          }
        }
        if (next.right) {
          if (!alreadySeen.contains(this.rightHelper(next))) {
            neighborList.addAtTail(this.rightHelper(next));
          }
        }
        if (next.top) {
          if (!alreadySeen.contains(this.topHelper(next))) {
            neighborList.addAtTail(this.topHelper(next));
          }
        }
        if (next.bottom) {
          if (!alreadySeen.contains(this.bottomHelper(next))) {
            neighborList.addAtTail(this.bottomHelper(next));
          }
        }

      }
      workList = neighborList;
      neighborList = new Deque<GamePiece>();
      distance++;
    }
    if (secondTime) {
      return distance - 1;
    }
    else {
      return diameter(farthest, true);
    }
  }

  // Breadth first search that checks the paths from the powerCell
  // outwards. Sets powered field to true for connected GamePieces
  // that are not farther from the powerCell than the radius field.
  // EFFECT: modifies the fields of GamePieces according to if they
  // fulfill the conditions of being powered or not.
  void isPowered(GamePiece powerStation, boolean secondTime) {
    ArrayList<GamePiece> alreadySeen = new ArrayList<GamePiece>();
    Deque<GamePiece> workList = new Deque<GamePiece>();
    Deque<GamePiece> neighborList = new Deque<GamePiece>();
    GamePiece farest = powerStation;

    for (GamePiece g : this.nodes) {
      g.powered = false;
    }

    int distance = 0;

    workList.addAtTail(powerStation);

    while (workList.size() != 0 && distance <= this.radius) { // add radius < dist

      while (workList.size() != 0) {
        GamePiece next = workList.removeFromHead();
        alreadySeen.add(next);
        farest = next;
        farest.powered = true;// farest.powered = true

        if (next.left) {
          if (!alreadySeen.contains(this.leftHelper(next))) {
            neighborList.addAtTail(this.leftHelper(next));
          }
        }
        if (next.right) {
          if (!alreadySeen.contains(this.rightHelper(next))) {
            neighborList.addAtTail(this.rightHelper(next));
          }
        }
        if (next.top) {
          if (!alreadySeen.contains(this.topHelper(next))) {
            neighborList.addAtTail(this.topHelper(next));
          }
        }
        if (next.bottom) {
          if (!alreadySeen.contains(this.bottomHelper(next))) {
            neighborList.addAtTail(this.bottomHelper(next));
          }
        }

      }
      workList = neighborList;
      neighborList = new Deque<GamePiece>();
      distance++;
    }
  }
}
