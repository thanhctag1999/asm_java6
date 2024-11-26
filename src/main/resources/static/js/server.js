const express = require("express");
const sql = require("mssql");
const app = express();
const port = 3000;
const sqlConfig = {
  user: "sa",
  password: "123",
  database: "asm_java6",
  server: "localhost",
  port: 1433,
  options: {
    encrypt: true,
    trustServerCertificate: true,
  },
};

app.get("/api/products", async (req, res) => {
  try {
    // Connect to the database
    await sql.connect(sqlConfig);
    const result = await sql.query("SELECT * FROM Products"); // Replace 'Products' with your table name

    // Send the data as a JSON response
    res.json(result.recordset);
  } catch (error) {
    console.error("Database query error:", error);
    res.status(500).send("Server error");
  } finally {
    // Close the database connection
    sql.close();
  }
});

app.listen(port, () => {
  console.log(`API server running at http://localhost:${port}`);
});

async function getProducts() {
  try {
    const response = await fetch("http://localhost:3000/api/products");
    const products = await response.json();
    console.log(products); // Display the products data in the console or use it in your application
  } catch (error) {
    console.error("Error fetching products:", error);
  }
}

// Call the function to fetch products
getProducts();
