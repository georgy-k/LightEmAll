import tester.*;

class Deque<T> {
  Sentinel<T> header;

  // makes an empty deque
  Deque() {
    this.header = new Sentinel<T>();
  }

  // makes a deque with the given header for testing
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  /*
   * Template Fields: ... header ... -- Sentinel<T> Methods: ... size() ... -- int
   * ... addAtHead(T t) ... -- void ... addAtTail(T t) ... -- void ...
   * removeFromHead() ... -- T ... removeFromTail() ... -- T ... find(IPred<T>
   * pred) ... -- ANode<T> ... removeNode(ANode<T> node) ... -- void Methods on
   * Fields: ... header.getSize() ... -- int ... header.makeFirst(T t) ... -- void
   * ... header.makeLast(T t) ... -- void ... header.removeFirst() ... -- T ...
   * header.removeLast() ... -- T ... header.finder(IPred<T> pred) ... -- ANode<T>
   * ... header.removeGiven(ANode<T> node) ... -- void
   */

  // counts the number of nodes in a deque, excluding the sentinel
  int size() {
    /*
     * Template Everything from the class template
     */
    return this.header.getSize();
  }

  // EFFECT:
  // adds a node with data t at the front of the list
  void addAtHead(T t) {
    /*
     * Template Everything from the class template Parameters: ... t ... -- T
     */
    this.header.makeFirst(t);
  }

  // EFFECT:
  // adds a node with data t at the end of the list
  void addAtTail(T t) {
    /*
     * Template Everything from the class template Parameters: ... t ... -- T
     */
    this.header.makeLast(t);
  }

  // removes the first node in the deque and returns the removed value
  T removeFromHead() {
    /*
     * Template Everything from the class template
     */
    return this.header.removeFirst();
  }

  // removes the last node in the deque and returns the removed value
  T removeFromTail() {
    /*
     * Template Everything from the class template
     */
    return this.header.removeLast();
  }

  // returns the first node in the deque which fulfills the given predicate
  ANode<T> find(IPred<T> pred) {
    /*
     * Template Everything from the class template Parameters: ... pred ... --
     * IPred<T>
     */
    return this.header.finder(pred);
  }

  // EFFECT:
  // removes the given node from the deque
  void removeNode(ANode<T> node) {
    /*
     * Template Everything from the class template Parameters: ... node ... --
     * ANode<T>
     */
    this.header.removeGiven(node);
  }
}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // increases the size by one for each node
  abstract int addSize();

  // removes this node and updates next and prev
  abstract T removeThis();

  // returns the node if it fulfills the predicate
  abstract ANode<T> finderHelp(IPred<T> pred);

  // EFFECT:
  // returns nothing if the node is not found or if the given node is a sentinel
  void removeGivenHelp(ANode<T> node) {
    /*
     * Template Everything from the class template Parameters: ... node ... --
     * ANode<T>
     */
  }

  // EFFECT:
  // updates the next and prev nodes when this node is removed
  void removeNode() {
    /*
     * Template Everything from the class template
     */
    this.next.updatePrev(this.prev);
    this.prev.updateNext(this.next);
  }

  // EFFECT:
  // updates this node's next node with the given node
  void updateNext(ANode<T> next) {
    /*
     * Template Everything from the class template Parameters: ... next ... --
     * ANode<T>
     */
    this.next = next;
  }

  // EFFECT:
  // updates this node's prev node with the given node
  void updatePrev(ANode<T> prev) {
    /*
     * Template Everything from the class template Parameters: ... prev ... --
     * ANode<T>
     */
    this.prev = prev;
  }

}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  /*
   * Template Fields: ... next ... -- ANode<T> ... prev ... -- ANodeT> Methods:
   * ... addSize() ... -- int ... removeThis() ... -- T ... finderHelp(IPred<T>
   * pred) ... -- ANode<T> ... removeGivenHelp(ANode<T> node) ... -- void ...
   * removeNode() ... -- void ... updateNext(ANode<T> next) ... -- void ...
   * updatePrev(ANode<T> prev) ... -- void ... getSize() ... -- int ...
   * makeFirst(T t) ... -- void ... makeLast(T t) ... -- void ... removeFirst()
   * ... -- T ... removeLast() ... -- T ... finder(IPred<T> pred) ... -- ANode<T>
   * ... removeGiven(ANode<T> node) ... -- void Methods on Fields: ...
   * next.updatePrev(ANode<T> prev) ... -- void ... prev.updateNext(ANode<T> next)
   * ... -- void ... next.removeThis() ... -- T ... prev.removeThis() ... -- T ...
   * next.finderHelp(IPred<T> pred) ... -- ANode<T> ...
   * next.removeGivenHelp(ANode<T> node) ... -- void
   */

  // calls the helper add size to calculate the size of the list
  public int getSize() {
    /*
     * Template Everything from the class template
     */
    return this.next.addSize();
  }

  // returns 0 since the sentinel does not contribute to the size of the list
  public int addSize() {
    /*
     * Template Everything from the class template
     */
    return 0;
  }

  // EFFECT:
  // adds a new node at the front of the list, and updates the nodes beside it
  void makeFirst(T t) {
    /*
     * Template Everything from the class template Parameters: ... t ... -- T
     */
    this.next = new Node<T>(t, this.next, this);
  }

  // EFFECT:
  // adds a new node at the back of the list, and updates the nodes beside it
  void makeLast(T t) {
    /*
     * Template Everything from the class template Parameters: ... t ... -- T
     */
    this.prev = new Node<T>(t, this, this.prev);
  }

  // removes the first node in the list
  T removeFirst() {
    /*
     * Template Everything from the class template
     */
    return this.next.removeThis();
  }

  // removes the last node in the list
  T removeLast() {
    /*
     * Template Everything from the class template
     */
    return this.prev.removeThis();
  }

  // cannot remove an item from an empty list
  T removeThis() {
    /*
     * Template Everything from the class template
     */
    throw new RuntimeException("Cannot remove a node from an empty list.");
  }

  // searches the list for a node that fulfills the given predicate
  ANode<T> finder(IPred<T> pred) {
    /*
     * Template Everything from the class template Parameters: ... pred ... --
     * IPred<T>
     */
    return this.next.finderHelp(pred);
  }

  // Returns the sentinel if no node fulfills the given predicate
  ANode<T> finderHelp(IPred<T> pred) {
    /*
     * Template Everything from the class template Parameters: ... pred ... --
     * IPred<T>
     */
    return this;
  }

  // EFFECT:
  // removes the given node if it is found in the list, otherwise does nothing
  void removeGiven(ANode<T> node) {
    this.next.removeGivenHelp(node);
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  // constructor for examples that updates other nodes in the list when this node
  // is made
  Node(T data, ANode<T> next, ANode<T> prev) {
    // checks to make sure none of the given nodes are null
    if (next == null) {
      throw new IllegalArgumentException("The next node cannot be null.");
    }
    if (prev == null) {
      throw new IllegalArgumentException("The previous node cannot be null.");
    }

    this.data = data;
    this.next = next;
    this.prev = prev;

    // updates the next and prev nodes to remain accurate
    next.updatePrev(this);
    prev.updateNext(this);
  }

  /*
   * Template Fields: ... data ... -- T ... next ... -- ANode<T> ... prev ... --
   * ANode<T> Methods: ... addSize() ... -- int ... removeThis() ... -- T ...
   * finderHelp(IPred<T> pred) ... -- ANode<T> ... removeGivenHelp(ANode<T> node)
   * ... -- void ... removeNode() ... -- void ... updateNext(ANode<T> next) ... --
   * void ... updatePrev(ANode<T> prev) ... -- void Methods on Fields: ...
   * next.addSize() ... -- int ... next.finderHelp(IPred<T> pred) ... -- ANode<T>
   * ... next.removeGivenHelp(ANode<T> node) ... -- void
   */

  // adds one for each node in the list
  int addSize() {
    /*
     * Template Everything from the class template
     */
    return 1 + this.next.addSize();
  }

  // removes the node from the next and prev, and returns the data from the
  // removed node
  T removeThis() {
    /*
     * Template Everything from the class template
     */

    // update next and prev nodes
    this.removeNode();

    return this.data;
  }

  // returns this if it fulfills the predicate, otherwise checks the rest of the
  // list
  ANode<T> finderHelp(IPred<T> pred) {
    /*
     * Template Everything from the class template Parameters: ... pred ... --
     * IPred<T> Methods on Parameters: ... pred.apply(T t) ... -- boolean
     */
    if (pred.apply(this.data)) {
      return this;
    }
    else {
      return this.next.finderHelp(pred);
    }
  }

  @Override
  // removes this node if it is the same as the given node, otherwise checks the
  // rest of the list
  void removeGivenHelp(ANode<T> node) {
    /*
     * Template Everything from the class template Parameters: ... node ... --
     * ANode<T>
     */
    if (this == node) {
      this.removeNode();
    }
    else {
      this.next.removeGivenHelp(node);
    }
  }
}

interface IPred<T> {
  // evaluates the given value with the predicate
  boolean apply(T t);
}

class Even implements IPred<Integer> {

  // checks if the number is even
  public boolean apply(Integer t) {
    /*
     * Template Parameters: ... t ... -- Integer
     */
    return t % 2 == 0;
  }

}

class IsShort implements IPred<String> {

  // checks if the given string has length less than 2
  public boolean apply(String t) {
    /*
     * Template Parameters: ... t ... -- String
     */
    return t.length() < 2;
  }

}

class ExamplesDeque {

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<String> deque3;
  Deque<Integer> deque4;
  Sentinel<String> s2;
  Sentinel<String> s3;
  Sentinel<Integer> s4;
  ANode<String> abc;
  ANode<String> bcd;
  ANode<String> cde;
  ANode<String> def;
  ANode<String> a;
  ANode<String> b;
  ANode<String> c;
  ANode<String> d;
  ANode<String> e;
  ANode<String> aaa;

  ANode<Integer> one;
  ANode<Integer> two;
  ANode<Integer> three;
  ANode<Integer> four;
  IPred<Integer> isEven;
  IPred<String> isShort;

  void initConditions() {
    this.abc = new Node<String>("abc");
    this.bcd = new Node<String>("bcd");
    this.cde = new Node<String>("cde");
    this.def = new Node<String>("def");
    this.a = new Node<String>("a");
    this.b = new Node<String>("b");
    this.c = new Node<String>("c");
    this.d = new Node<String>("d");
    this.e = new Node<String>("e");
    this.one = new Node<Integer>(1);
    this.two = new Node<Integer>(2);
    this.three = new Node<Integer>(3);
    this.four = new Node<Integer>(4);

    this.deque1 = new Deque<String>();

    this.s2 = new Sentinel<String>();
    this.abc = new Node<String>("abc", this.bcd, this.s2);
    this.bcd = new Node<String>("bcd", this.cde, this.abc);
    this.cde = new Node<String>("cde", this.def, this.bcd);
    this.def = new Node<String>("def", this.s2, this.cde);
    this.deque2 = new Deque<String>(this.s2);

    this.s3 = new Sentinel<String>();
    this.a = new Node<String>("a", this.d, this.b);
    this.d = new Node<String>("d", this.c, this.a);
    this.c = new Node<String>("c", this.s3, this.d);
    this.b = new Node<String>("b", this.a, this.e);
    this.e = new Node<String>("e", this.b, this.s3);
    this.deque3 = new Deque<String>(this.s3);

    this.s4 = new Sentinel<Integer>();
    this.one = new Node<Integer>(1, this.two, this.s4);
    this.two = new Node<Integer>(2, this.three, this.one);
    this.three = new Node<Integer>(3, this.four, this.two);
    this.four = new Node<Integer>(4, this.s4, this.three);
    this.deque4 = new Deque<Integer>(this.s4);

    this.isEven = new Even();
    this.isShort = new IsShort();

  }

  boolean testSize(Tester t) {
    this.initConditions();

    return t.checkExpect(this.deque1.size(), 0) && t.checkExpect(this.deque2.size(), 4)
        && t.checkExpect(this.deque3.size(), 5);
  }

  boolean testAddAtHead(Tester t) {
    this.initConditions();

    this.deque1.addAtHead("aaa");
    this.deque2.addAtHead("ooo");
    this.deque3.addAtHead("bbb");

    return t.checkExpect(this.deque1.header.next,
        new Node<String>("aaa", this.deque1.header, this.deque1.header))
        && t.checkExpect(this.deque2.header.next,
            new Node<String>("ooo", this.abc, this.deque2.header))
        && t.checkExpect(this.deque3.header.next,
            new Node<String>("bbb", this.e, this.deque3.header));
  }

  boolean testAddAtTail(Tester t) {
    this.initConditions();

    this.deque1.addAtTail("aaa");
    this.deque2.addAtTail("ooo");
    this.deque3.addAtTail("bbb");

    return t.checkExpect(this.deque1.header.prev,
        new Node<String>("aaa", this.deque1.header, this.deque1.header))
        && t.checkExpect(this.deque2.header.prev,
            new Node<String>("ooo", this.deque2.header, this.def))
        && t.checkExpect(this.deque3.header.prev,
            new Node<String>("bbb", this.deque3.header, this.c));
  }

  boolean testRemoveFromHead(Tester t) {
    this.initConditions();

    return t.checkException(new RuntimeException("Cannot remove a node from an empty list."),
        this.deque1, "removeFromHead") && t.checkExpect(this.deque2.removeFromHead(), "abc")
        && t.checkExpect(this.deque3.removeFromHead(), "e");
  }

  boolean testRemoveFromTail(Tester t) {
    this.initConditions();

    return t.checkException(new RuntimeException("Cannot remove a node from an empty list."),
        this.deque1, "removeFromTail") && t.checkExpect(this.deque2.removeFromTail(), "def")
        && t.checkExpect(this.deque3.removeFromTail(), "c");
  }

  boolean testFind(Tester t) {
    this.initConditions();

    return t.checkExpect(this.deque4.find(this.isEven), this.two)
        && t.checkExpect(this.deque1.find(this.isShort), new Sentinel<String>())
        && t.checkExpect(this.deque2.find(this.isShort), this.s2)
        && t.checkExpect(this.deque3.find(this.isShort), e);
  }

  boolean testRemoveNode(Tester t) {
    this.initConditions();

    this.deque1.removeNode(this.abc);
    this.deque2.removeNode(this.bcd);
    this.deque3.removeNode(this.s3);
    this.deque4.removeNode(this.three);

    return t.checkExpect(this.deque1, new Deque<String>()) && t.checkExpect(this.abc.next, this.cde)
        && t.checkExpect(this.deque3.header, this.s3) && t.checkExpect(this.four.prev, this.two);
  }

  boolean testAddSize(Tester t) {
    this.initConditions();

    return t.checkExpect(this.s2.addSize(), 0) && t.checkExpect(this.abc.addSize(), 4)
        && t.checkExpect(this.one.addSize(), 4) && t.checkExpect(this.c.addSize(), 1);
  }

  boolean testFinderHelp(Tester t) {
    this.initConditions();

    return t.checkExpect(this.s2.finderHelp(this.isShort), s2)
        && t.checkExpect(this.abc.finderHelp(this.isShort), s2)
        && t.checkExpect(this.c.finderHelp(this.isShort), this.c);
  }

  boolean testRemoveGivenHelp(Tester t) {
    this.initConditions();

    this.def.removeGivenHelp(this.s3);
    this.c.removeGivenHelp(this.b);
    this.abc.removeGivenHelp(this.bcd);
    this.three.removeGivenHelp(this.three);

    return t.checkExpect(this.abc.next, this.cde) && t.checkExpect(this.deque3.header, this.s3)
        && t.checkExpect(this.four.prev, this.two);
  }

  boolean testUpdatePrev(Tester t) {
    this.initConditions();

    this.def.updatePrev(this.s3);
    this.c.updatePrev(this.e);
    this.three.updatePrev(this.one);

    return t.checkExpect(this.def.prev, this.s3) && t.checkExpect(this.c.prev, this.e)
        && t.checkExpect(this.three.prev, this.one);
  }

  boolean testUpdateNext(Tester t) {
    this.initConditions();

    this.def.updateNext(this.s3);
    this.c.updateNext(this.e);
    this.three.updateNext(this.one);

    return t.checkExpect(this.def.next, this.s3) && t.checkExpect(this.c.next, this.e)
        && t.checkExpect(this.three.next, this.one);
  }

  boolean testRemoveThis(Tester t) {
    this.initConditions();

    return t.checkExpect(this.def.removeThis(), "def") && t.checkExpect(this.four.removeThis(), 4)
        && t.checkExpect(this.three.removeThis(), 3);
  }

  boolean testMakeFirst(Tester t) {
    this.initConditions();

    this.s2.makeFirst("world");
    this.s3.makeFirst("hi");
    this.s4.makeFirst(1919);

    return t.checkExpect(this.s2.next, new Node<String>("world", this.abc, this.s2))
        && t.checkExpect(this.s3.next, new Node<String>("hi", this.e, this.s3))
        && t.checkExpect(this.s4.next, new Node<Integer>(1919, this.one, this.s4));
  }

  boolean testMakeLast(Tester t) {
    this.initConditions();

    this.s2.makeLast("world");
    this.s3.makeLast("hi");
    this.s4.makeLast(1919);

    return t.checkExpect(this.s2.prev, new Node<String>("world", this.s2, this.def))
        && t.checkExpect(this.s3.prev, new Node<String>("hi", this.s3, this.c))
        && t.checkExpect(this.s4.prev, new Node<Integer>(1919, this.s4, this.four));
  }

  boolean testRemoveLast(Tester t) {
    this.initConditions();

    return t.checkExpect(this.s2.removeLast(), "def") && t.checkExpect(this.s3.removeLast(), "c")
        && t.checkExpect(this.s4.removeLast(), 4);
  }

  boolean testRemoveFirst(Tester t) {
    this.initConditions();

    return t.checkExpect(this.s2.removeFirst(), "abc") && t.checkExpect(this.s3.removeFirst(), "e")
        && t.checkExpect(this.s4.removeFirst(), 1);
  }

  boolean testRemoveGiven(Tester t) {
    this.initConditions();

    this.s2.removeGiven(this.bcd);
    this.s3.removeGiven(this.b);
    this.s4.removeGiven(this.two);

    return t.checkExpect(this.abc.next, this.cde) && t.checkExpect(this.e.next, this.a)
        && t.checkExpect(this.one.next, this.three);
  }

}
