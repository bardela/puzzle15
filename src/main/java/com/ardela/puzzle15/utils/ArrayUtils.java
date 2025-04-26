package com.ardela.puzzle15.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayUtils {
  public static int[][] copyMatrix(int[][] matrix) {
    return IntStream.range(0, matrix.length)
        .mapToObj(row -> copyRow(matrix[row]))
        .toArray(int[][]::new);
  }

  public static int[] copyRow(int[] row) {
    return IntStream.range(0, row.length)
        .map(col -> row[col])
        .toArray();
  }

  public static List<Integer> generateSequenceInRandomOrder(int start, int end) {
    List<Integer> sequenceInRandomOrder = IntStream.rangeClosed(start, end).boxed()
        .collect(Collectors.toList());
    Collections.shuffle(sequenceInRandomOrder);
    return sequenceInRandomOrder;
  }
}
