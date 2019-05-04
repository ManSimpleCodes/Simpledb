# Simpledb-1
1 >The following are the methods which are implemented by me.

PART 1. Fields and Tuples
TupleDesc.java:
public Type getType(int i) 
public String toString()
Tuple.java
public String toString()

PART 2. Catalog
Catalog.java
public int getTableId(String name)
public TupleDesc getTupleDesc(int tableid) 
public DbFile getDbFile(int tableid) 

PART 3. Buffer Pool
BufferPool.java
public Page getPage(TransactionId tid, PageId pid, Permissions perm)
			
PART 4. HeapPage access
HeapPage.java
public Tuple getTuple(int entryID)
protected int entryCount()
protected int tupleLocation(int entryID)
public Iterator<Tuple> iterator()

PART 5. HeapFile access
HeapFile.java
public Page readPage(PageId pid)
public DbFileIterator iterator(TransactionId tid)
PART 6. HeapPage Mutability
HeapPage.java
public void addTuple(Tuple t) 								 public void deleteTuple(Tuple t) 
 In part5 HeapFile.java, I had to add few lines to the original code in public void open() method in order to implement public DbFileIterator iterator(TransactionId tid).
