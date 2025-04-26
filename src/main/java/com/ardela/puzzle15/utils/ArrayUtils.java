package com.ardela.puzzle15.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayUtils {
  public static int[][] copyMatrix(int[][] matrix) {
    return Arrays.stream(matrix).map(ArrayUtils::copyRow)
        .toArray(int[][]::new);
  }

  public static int[] copyRow(int[] row) {
    return Arrays.stream(row, 0, row.length)
        .toArray();
  }

  public static List<Integer> generateSequenceInRandomOrder(int start, int end) {
    List<Integer> sequenceInRandomOrder = IntStream.rangeClosed(start, end).boxed()
        .collect(Collectors.toList());
    Collections.shuffle(sequenceInRandomOrder);
    return sequenceInRandomOrder;
  }
}
