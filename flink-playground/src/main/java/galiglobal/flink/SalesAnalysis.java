package galiglobal.flink;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.common.operators.Order;

import java.net.URL;

public class SalesAnalysis {

    public static void main(String[] args) throws Exception {

        // Set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // Path to the input file
        URL resource = SalesAnalysis.class.getResource("/generated_sales_data.csv");
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }
        String inputPath = "/tmp/generated_sales_data.csv" ;//resource.getPath();


        // Path to the output file
        String outputPath = "output_sales_per_product.csv";

        // Read CSV file
        DataSet<Tuple5<Integer, String, Integer, Double, String>> salesData = env.readCsvFile(inputPath)
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .ignoreInvalidLines()
                .types(Integer.class, String.class, Integer.class, Double.class, String.class);

        // Calculate total sales per product
        DataSet<Tuple2<String, Double>> totalSalesPerProduct = salesData
                .map(transaction -> new Tuple2<>(transaction.f1, transaction.f2 * transaction.f3))
                .returns(TypeInformation.of(new TypeHint<Tuple2<String, Double>>() {}))
                .groupBy(0) // Group by product_id (f0)
                .sum(1)     // Sum the total sales (f1)
                .sortPartition(0, Order.ASCENDING); // Sort by product_id

        // Write the result to a CSV file
        totalSalesPerProduct.writeAsCsv(outputPath, "\n", ",");

        // Execute the Flink job
        env.execute("Sales Analysis");
    }
}
