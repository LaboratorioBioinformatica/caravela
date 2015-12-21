package br.usp.iq.lbi.caravela.intervalTree;

import java.util.Collections;
import java.util.List;

public class IntervalTreeProblem {
	public static void main(String args[]) {
		
		IntervalTree<Integer> it = new IntervalTree<Integer>();

		it.addInterval(0L, 10L, 1);
		it.addInterval(0L, 10L, 1);
		it.addInterval(20L, 30L, 2);
		it.addInterval(15L, 17L, 3);
		it.addInterval(25L, 35L, 4);

		List<Interval<Integer>> intervals = it.getIntervals(0,35);
		
		Collections.sort(intervals);
		int size = intervals.size();
		System.out.println(intervals.get(0).getStart());
		System.out.println(intervals.get(size -1).getEnd());
		
		
		List<Integer> result1 = it.get(5L);
		List<Integer> result2 = it.get(10L);
		List<Integer> result3 = it.get(29L);
		List<Integer> result4 = it.get(5L, 15L);

		System.out.print("\nIntervals that contain 5L:");
		for (int r : result1)
			System.out.print(r + " ");

		System.out.print("\nIntervals that contain 10L:");
		for (int r : result2)
			System.out.print(r + " ");

		System.out.print("\nIntervals that contain 29L:");
		for (int r : result3)
			System.out.print(r + " ");

		System.out.print("\nIntervals that intersect (5L,15L):");
		for (int r : result4)
			System.out.print(r + " ");
		
		for (Interval<Integer> interval : intervals) {
			System.out.println(interval);
		}
	}
}
