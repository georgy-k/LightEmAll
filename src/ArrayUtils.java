import java.util.ArrayList;

public class ArrayUtils {

  // Copies source array into the end of the dest array
  <T> ArrayList<T> copy(ArrayList<T> dest, ArrayList<T> source) {
    for (int i = 0; i < source.size(); i++) {
      dest.add(source.get(i));
    }
    return dest;

  }

}
