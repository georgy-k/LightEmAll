
import java.awt.Color;
import javalib.worldimages.*;

public class GamePiece {
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  boolean powered;

  // variable for power
  // check if neighboring cells are powered, and if connected

  GamePiece(int row, int col, boolean left, boolean right, boolean top, boolean bottom,
      boolean powerStation, boolean powered) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = powerStation;
    this.powered = powered;

  }

  // Connects this cell to the given cell with this given connectType
  // EFFECT: modifies connection type fields of this and other cells
  void mutualConnect(GamePiece other, String connectType) {
    if (connectType.equals("bottom")) {
      this.bottom = true;
      other.top = true;
    }

    if (connectType.equals("right")) {
      this.right = true;
      other.left = true;
    }

    if (connectType.equals("top")) {
      this.top = true;
      other.bottom = true;
    }

    if (connectType.equals("left")) {
      this.left = true;
      other.right = true;
    }
  }

  // Draws this GamePiece with wires on it
  // Wires become yellow if cell is powered
  // PowerStation is represented by blue star
  WorldImage drawGamePiece() {
    RectangleImage background = new RectangleImage(40, 40, OutlineMode.SOLID, Color.GRAY.darker());
    RectangleImage outline = new RectangleImage(40, 40, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage gamePiece = new OverlayImage(outline, background);
    WorldImage station = new StarImage(14, 7, OutlineMode.SOLID, Color.BLUE.brighter());

    RectangleImage horizontalWire = new RectangleImage(20, 3, OutlineMode.SOLID, Color.YELLOW);
    WorldImage verticalWire = new RotateImage(horizontalWire, 90);

    RectangleImage horizontalUnp = new RectangleImage(20, 3, OutlineMode.SOLID, Color.GRAY);
    WorldImage verticalUnp = new RotateImage(horizontalUnp, 90);

    if (this.left) {
      if (this.powered) {
        gamePiece = new OverlayOffsetImage(horizontalWire, 10, 0, gamePiece);
      }
      else {
        gamePiece = new OverlayOffsetImage(horizontalUnp, 10, 0, gamePiece);
      }
    }

    if (this.right) {
      if (this.powered) {
        gamePiece = new OverlayOffsetImage(horizontalWire, -10, 0, gamePiece);
      }
      else {
        gamePiece = new OverlayOffsetImage(horizontalUnp, -10, 0, gamePiece);
      }
    }

    if (this.top) {
      if (this.powered) {
        gamePiece = new OverlayOffsetImage(verticalWire, 0, 10, gamePiece);
      }
      else {
        gamePiece = new OverlayOffsetImage(verticalUnp, 0, 10, gamePiece);
      }
    }

    if (this.bottom) {
      if (this.powered) {
        gamePiece = new OverlayOffsetImage(verticalWire, 0, -10, gamePiece);
      }
      else {
        gamePiece = new OverlayOffsetImage(verticalUnp, 0, -10, gamePiece);
      }
    }

    if (this.powerStation) {
      gamePiece = new OverlayImage(station, gamePiece);
    }

    return gamePiece;
  }

  // Rotates this cell connection fields
  // EFFECT: Modifies connection fields by rotating
  // the GamePiece 90 degrees clockwise
  void rotate() {
    boolean newTop = this.left;
    boolean newRight = this.top;
    boolean newBottom = this.right;
    boolean newLeft = this.bottom;

    this.left = newLeft;
    this.right = newRight;
    this.top = newTop;
    this.bottom = newBottom;
  }

}