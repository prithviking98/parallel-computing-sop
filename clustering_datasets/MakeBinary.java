import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MakeBinary {
 
	public static void main(String[] args) throws Exception {
		// if (args.length < 2) {
		// 	System.out.println("Please provide input and output files");
		// 	System.exit(0);
		// }
 
		// String inputFile = args[0];
		// String outputFile = args[1];
 
 
 		String file = "/home/prithvi/clustering_datasets/default.bin";
 		// Create a new file and override when already exists
 		//second param is append=true/false
 		try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file, false))){
 			int numpoints=10;
	 		output.writeInt(numpoints); //number of points
	 		output.writeInt(2); //dimension

	 		double[] x={0.0,1.0,-1.0,0.0,0.0 , 10.0,11.0,9.0,10.0,10.0}; //x coordinates
	 		double[] y={0.0,0.0,0.0,1.0,-1.0 ,  0.0, 0.0,0.0, 1.0,-1.0}; //y coordinates
			
			for(int i=0;i<numpoints;++i)
			{
				output.writeDouble(x[i]);
				output.writeDouble(y[i]);
			}

			output.close();
 		}
 		
	}
}