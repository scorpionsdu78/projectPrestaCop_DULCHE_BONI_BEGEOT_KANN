import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.functions.col


object sparkTest extends App{



  val spark = SparkSession.builder
    .master("local[*]")
    .appName("Spark Word Count")
    .getOrCreate()

  val df2014  = spark.read.format("csv").option("header","true").load("./data/nyc-parking-tickets/Parking_Violations_Issued_-_Fiscal_Year_2014__August_2013___June_2014_.csv")
  val df2015 = spark.read.format("csv").option("header","true").load("./data/nyc-parking-tickets/Parking_Violations_Issued_-_Fiscal_Year_2015.csv")
  val df2016 = spark.read.format("csv").option("header","true").load("./data/nyc-parking-tickets/Parking_Violations_Issued_-_Fiscal_Year_2016.csv")
  val df2017 = spark.read.format("csv").option("header","true").load("./data/nyc-parking-tickets/Parking_Violations_Issued_-_Fiscal_Year_2017.csv")


  val columns2014 = df2014.columns.toSeq.toList
  val columns2017 = df2017.columns.toSeq.toList
  val difcol = columns2014.slice(columns2017.length, columns2014.length)
  //print("column 2014: " + columns2014 + "\n")
  //print("columns 2017:" + columns2017 + "\n")
  //print("columns diff" + difcol + "\n")

  val df2017fix = difcol.foldLeft(df2017)((df2017, month) => df2017.withColumn(month , lit(null: String)) )
  //df2017fix.show()



  print("df 2014")
  //df2014.show()

  print("df final")
  val df = df2014.union(df2015).union(df2016).union(df2017fix)
  df.show()

  /*val group = df.groupBy("Registration State").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show()*/

  /*val group = df.groupBy("Violation Location").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show()*/

  /*val df2 = df.select("Street Code1", "Street Code2", "Street Code3").where(col("Street Code1")>0).where(col("Street Code2")>0).where(col("Street Code3")>0)
  val group = df2.groupBy("Street Code1","Street Code2","Street Code3").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  /*val group = df.groupBy("Street Name").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  //Vehicle Color|Intersecting Street||House Number||Violation Time|

  /*val df2 = df.select("Violation Precinct").where(col("Violation Precinct")>0)
  val group = df2.groupBy("Violation Precinct").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  /*val df2 = df.select("Law Section","Sub Division").where(col("Law Section").isNotNull).where(col("Sub Division").isNotNull)
  val group = df2.groupBy("Law Section","Sub Division").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  /*val df2 = df.select("Street Name", "Intersecting Street", "House Number", "Violation Location").where(col("Street Name").isNotNull).where(col("Intersecting Street").isNotNull).where(col("House Number") > 0).where(col("Violation Location").isNotNull)
  val group = df2.groupBy("Street Name", "Intersecting Street", "House Number", "Violation Location").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  /*val df2 = df.select("Street Name",  "House Number", "Violation Location").where(col("Street Name").isNotNull).where(col("House Number") > 0).where(col("Violation Location").isNotNull)
  val group = df2.groupBy("Street Name", "House Number", "Violation Location").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)*/

  val df2 = df.select("Street Name", "Intersecting Street", "Violation Location").where(col("Street Name").isNotNull).where(col("Intersecting Street").isNotNull).where(col("Violation Location").isNotNull)
  val group = df2.groupBy("Street Name", "Intersecting Street",  "Violation Location").count()
  val groupSort = group.orderBy(col("count").desc)
  groupSort.show(10)

}
