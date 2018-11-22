package abc;

import java.util.ArrayList;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.SparkConf;
import org.apache.spark.util.LongAccumulator;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        SparkConf conf = new SparkConf().setAppName("spark test 2").setMaster("spark://mpiuser-OptiPlex-3020:7077");
		JavaSparkContext sc = new JavaSparkContext(conf);

		//this program tries to find the time improvement in summing the elements of a large array

		ArrayList <Integer> arr= new ArrayList<Integer>(10000000);
		for (int i=0;i<10000000;++i)
			arr.add(i,1);

		int sum;

		sum=0;
		long t1=System.nanoTime();
		for(int i=0;i<1e6;++i)
			sum=sum+arr.get(i);
		long t2=System.nanoTime();
		double rt=((double)t2-t1)/1000000000;
		System.out.println("sum = "+sum);
		System.out.println("time = "+rt);

		JavaRDD<Integer> parr = sc.parallelize(arr);
		parr.persist(StorageLevel.MEMORY_ONLY());
		long t= parr.count();

		//now parr is cached in memory. finding sum.
		t1=System.nanoTime();
		sum=parr.reduce( (Integer x,Integer y) -> x+y);
		t2=System.nanoTime();
		rt=((double)t2-t1)/1000000000;
		System.out.println("sum = "+sum);
		System.out.println("time = "+rt);

		LongAccumulator acc=sc.sc().longAccumulator();
		System.out.println(acc.value());
		t1=System.nanoTime();
		parr.foreach( (Integer x) ->{ acc.add(x);});
		t2=System.nanoTime();
		rt=((double)t2-t1)/1000000000;
		System.out.println("sum = "+acc.value());
		System.out.println("time = "+rt);
    }
}
