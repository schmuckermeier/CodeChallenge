package control;

import java.sql.*;


public class DBController {
	
	private PreparedStatement addNumbers;
	
	public Connection createConnection() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:h2:~/codechallenge");
	}
	
	public void createNumbersTable(Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE Numbers (Value INTEGER NOT NULL)");
	}
	
	public void deleteNumbersTable(Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE Numbers");
	}
	
	public void addNumber(Connection connection, int number) throws SQLException{
		if(this.addNumbers == null){
			this.addNumbers = connection.prepareStatement("INSERT INTO Numbers VALUES (?)");
		}
		addNumbers.setInt(1, number);
		addNumbers.execute();
	}
	
	public long getCount(Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Numbers");
		resultSet.next();
		return resultSet.getLong(1);
	}
	
	public double getMedian(Connection connection) throws SQLException{
		long count = getCount(connection);
		boolean isEvenLength = count % 2 == 0;
		
		String query = "SELECT value FROM (SELECT rownum() as Row, value FROM (SELECT * FROM Numbers ORDER BY Value)) ";
		
		if(isEvenLength){
			long indexBelowMedian = count / 2;
			long indexAboveMedian = indexBelowMedian + 1;
			query += "WHERE Row = " + indexBelowMedian + " or Row = " + indexAboveMedian;
		}else{
			long center = count / 2 + 1;
			query += "WHERE Row = " + center; 
		}
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		resultSet.next();
		int median1 = resultSet.getInt(1);
		if(resultSet.next()){
			// check if count was even get second result
			int median2 = resultSet.getInt(1);
			return ((double) (median1 + median2))/2;
		}
		return median1;
	}

}
