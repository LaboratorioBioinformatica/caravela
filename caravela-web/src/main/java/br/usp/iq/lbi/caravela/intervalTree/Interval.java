package br.usp.iq.lbi.caravela.intervalTree;

public class Interval<Type> implements Comparable<Interval<Type>> {

	private long start;
	private long end;
	private Type data;

	public Interval(long start, long end, Type data) {
		this.start = start;
		this.end = end;
		this.data = data;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public Type getData() {
		return data;
	}

	public boolean contains(long time) {
		//modify to closed interval
		return time <= end && time >= start;
	}

	public boolean intersects(Interval<?> other) {
		// modify to closed interval
		return other.getEnd() >= start && other.getStart() <= end;
	}

	public int compareTo(Interval<Type> other) {
		if (start < other.getStart())
			return -1;
		else if (start > other.getStart())
			return 1;
		else if (end < other.getEnd())
			return -1;
		else if (end > other.getEnd())
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(start).append(":").append(end).toString();
	}
	
//	public static void main(String[] args) {
//		ArrayList<Interval<String>> intervalList = new ArrayList<Interval<String>>();
//		intervalList.add(new Interval<String>(22, 30, "teste"));
//		
//		intervalList.add(new Interval<String>(30, 35, "teste")); //?
//		
//		intervalList.add(new Interval<String>(15, 20, "teste"));
//		intervalList.add(new Interval<String>(1, 10, "teste"));
//		intervalList.add(new Interval<String>(1, 10, "teste"));
//		intervalList.add(new Interval<String>(9, 12, "teste"));
//		intervalList.add(new Interval<String>(11, 18, "teste"));
//		
//		
//		Collections.sort(intervalList);
//		
//		HashMap<String,List<Interval<String>>> hashMap = new HashMap<String, List<Interval<String>>>();
//		
//		for (Interval<String> interval : intervalList) {
//			String taxonomy = interval.getData();
//			List<Interval<String>> list = hashMap.get(taxonomy);
//			
//			if(list == null){
//				ArrayList<Interval<String>> intervals = new ArrayList<Interval<String>>();
//				intervals.add(interval);
//				hashMap.put(taxonomy, intervals);
//			} else {
//				ArrayList<Interval<String>> intervalsNews = new ArrayList<Interval<String>>();
//				for (Interval<String> interval2 : list) {
//					if(interval2.intersects(interval)){
//						// if has intersection - extend interval
//						System.out.println(interval);
//						System.out.println(interval2);
//						System.out.println("-------------");
//					} else {
//						// if does not have - add new interval
//						intervalsNews.add(interval2);
//					}
//				}
//				list.addAll(intervalsNews);
//			}
//			
//		}
//		
//	}

}
