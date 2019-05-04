package simpledb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import simpledb.Aggregator.AggregateFunctionFactory;
import simpledb.StringAggregator.StringAggregatorImplWithoutGrouping;

/**
 * An {@code IntAggregator} computes some aggregate value over a set of {@code IntField}s.
 */
public class IntAggregator implements Aggregator {

	/**
	 * A {@code IntAggregatorImpl} instance.
	 */
	IntAggregator impl;
	private int gbfield;
	private Type gbfieldtype;
	private int afield;
	private Op what;
	private HashMap<Field, Integer> groupList = new HashMap<Field, Integer>();
	private TupleDesc td_tup;
	Field gbField;
	/**
	 * Constructs an {@code IntAggregator}.
	 * 
	 * @param gbfield
	 *            the 0-based index of the group-by field in the tuple, or {@code NO_GROUPING} if there is no grouping
	 * @param gbfieldtype
	 *            the type of the group by field (e.g., {@code Type.INT_TYPE}), or {@code null} if there is no grouping
	 * @param afield
	 *            the 0-based index of the aggregate field in the tuple
	 * @param what
	 *            the aggregation operator
	 */
	public IntAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
		// some code goes heres
		//throw new UnsupportedOperationException("Implement this");
		this.gbfield = gbfield;
		if(gbfield == Aggregator.NO_GROUPING) 
    		gbField = new IntField(Aggregator.NO_GROUPING);
    	this.gbfieldtype = gbfieldtype;
    	this.afield = afield;
    	this.what = what;	
	}

	/**
	 * Merges a new {@code Tuple} into the aggregate, grouping as indicated in the constructor.
	 * 
	 * @param tup
	 *            the {@code Tuple} containing an aggregate field and a group-by field
	 */
	public void merge(Tuple tup) {
		// some code goes here
		//throw new UnsupportedOperationException("Implement this");
		td_tup = tup.getTupleDesc();
		gbField = tup.getField(gbfield);
    	int intialValue = 0;
    	int tupValue = ((IntField)tup.getField(afield)).getValue();   	
    	if((groupList.containsKey(gbField)) == false) {
    		groupList.put(gbField,intialValue);
    		intialValue = tupValue;
    	}
    	int listValue = groupList.get(gbField);
    	switch(what) {
    		case MIN:
    				if(intialValue != 0) {
    					if(tupValue < listValue)
    						intialValue = tupValue;  				
    				} else 
    					intialValue = listValue;	
    				break;	
    		case MAX:
					if(tupValue > listValue)
						intialValue = tupValue;
					break;
    		case AVG:
    				if(intialValue != 0)
    					listValue = intialValue;	
    				else
    					listValue = (listValue + tupValue)/2;
    					intialValue = listValue;	
    				break;	
    		case SUM:
    				listValue += tupValue;
    				intialValue = listValue;
    				break;	
    		case COUNT:
    				listValue++;
    				intialValue = listValue;
    				break;   	  		
    	}
    	groupList.put(gbField,intialValue);	
	}

	/**
	 * Creates a {@code DbIterator} over group aggregate results.
	 *
	 * @return a {@code DbIterator} whose tuples are the pair ({@code groupVal}, {@code aggregateVal}) if using group,
	 *         or a single ({@code aggregateVal}) if no grouping. The {@code aggregateVal} is determined by the type of
	 *         aggregate specified in the constructor.
	 */
	public DbIterator iterator() {
		// some code goes here
		//throw new UnsupportedOperationException("Implement this");
		Type[] type= null;
	   	TupleDesc td = null;
	   	Field f = null ;
    	ArrayList<Tuple> tupList = new ArrayList<Tuple>();
    	TupleIterator tupIterator =  new TupleIterator(td, tupList);	
    	if(gbfield != Aggregator.NO_GROUPING) {		
    		type = new Type[] {this.gbfieldtype,Type.INT_TYPE};
    		td = new TupleDesc(type);
    		Set<Field> j=groupList.keySet() ;
    			for(Field i:j) {
    				int value = groupList.get(i);
    				f = new IntField(value);
    				Tuple t = new Tuple(td);
    				t.setField(0,i);
    				t.setField(1,f);
    				tupList.add(t);
    			}
    	} 
    	else {	
    		type = new Type[] {Type.INT_TYPE};
     		td = new TupleDesc(type); 
     		Set<Field> i = groupList.keySet() ;
     		int value = groupList.get(i); 
     		f = new IntField(value); 
     		Tuple t = new Tuple(td); 		
   			t.setField(0,f);	
   			tupList.add(t);
    	}
		return tupIterator;
	}

	/**
	 * An {@code IntAggregatorImpl} computes some aggregate value over a set of {@code Field}s.
	 */
	abstract class IntAggregatorImpl {
		// some code goes here
		public abstract DbIterator iterator();
		public abstract void merge(Tuple tup);
		public abstract void clear();
	}

	// some code goes here

	/**
	 * Clears this {@code IntAggregator}.
	 */
	@Override
	public void clear() {
		// some code goes here
		impl = new IntAggregator(gbfield, gbfieldtype, afield, what);
		impl = null;
		//throw new UnsupportedOperationException("Implement this");
	}
}
