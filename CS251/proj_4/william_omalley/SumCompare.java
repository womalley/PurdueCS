/*
*
*	sum class for sumSort
*
*/

import java.util.Comparator;

public class SumCompare { 	//implements Comparable {
	public int index0;
	public int index1;
	public int sumVal;

	public SumCompare(int index0, int index1, int sumVal) {
		this.index0 = index0;
		this.index1 = index1;
		this.sumVal = sumVal;
	}

	public int getIndex0() {
		return (index0);
	}

	public int getIndex1() {
		return (index1);
	}

	public int getSumVal() {
		return (sumVal);
	}

	public void setIndex0(int index0) {
		this.index0 = index0;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}
	
	public void setSumVal(int sumVal) {
		this.sumVal = sumVal;
	}

}
