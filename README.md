Shoe Shop Database
Project Overview
This project is designed to model a shoe shop database. The database contains information about Model.ProductType, Model.ProductCategory, Model.Product, Model.Brand, Model.ProductOption, customers, orders, and the relationships between them. It includes tables for storing product details, customer data, orders, and sales transactions.

Database Schema
The database contains the following key entities:

Model.ProductType: Defines the types of products available (e.g., shoes, sandals, boots).
Model.ProductCategory: Categorizes products into specific groups.
Model.Product: Contains details about each product, including pricing and availability.
Model.Brand: Information about the brands of the products sold.
Model.ProductOption: Variations in products, such as size, color, etc.
Customers: Data on customers, such as their names, email, and location.
Orders: Orders made by customers, including the total price and status.
OrderItems: Details of products ordered in each order.
Database Design
ER Diagram: A visual representation of the database entities, relationships, and attributes.
Relations Model: Describes the tables, columns, and relationships in the database.
SQL Scripts
DDL Script: A SQL file that creates the necessary tables and fills the database with sample data.
Available as Webshop_DDL.sql
DML Script: A SQL file that answers specific questions based on the database.
Available as Webshop_DML.sql
Sample Data

The database includes the following data:

Products: At least 8 products (e.g., sandals, sneakers, boots, etc.).
Categories: At least 5 product categories (e.g., sandals, sneakers, boots, etc.).
Customers: At least 5 customers with different purchasing histories.
Orders: At least 6 orders placed by customers.
Questions Answered by the Database
The following questions can be answered using SQL queries on the database:

Which customers have bought black sandals in size 38 from the brand Ecco? The query lists customer names without hardcoded IDs.

List the number of products per category. The result includes category names and the total number of products in each category.

Generate a customer list with the total amount spent by each customer. Displays each customer's first and last name along with their total spending.

Print the total order value per location (city) where the total order value is greater than 1000 SEK. The list includes city names and order values. It ensures that there are cities with order values less than 1000 SEK.

Create a top-5 list of the most sold products. Lists the top 5 most sold products based on total sales.

Which month had the highest sales? The query will return the month with the largest total sales, based on the data in the database.

Stored Procedure: AddToCart
A stored procedure called AddToCart has been implemented to handle the addition of products to a customer's order.

Functionality of AddToCart:
If the provided orderId is null or the order doesn't exist, a new order is created for the customer and the selected product is added to it.
If the order exists, the selected product is added to the order.
If the product is already in the order, the quantity of that product is increased by 1.
For each product added to an order, the stock quantity of that product is decreased accordingly.

User Interface for AddToCart
The AddToCart procedure is accessed via a Java-based command-line interface that allows users to interact with the database. The interface follows the MVC pattern and utilizes the Singleton design pattern for managing database connections.

MVC Architecture:
Model: Contains classes that represent database entities such as Product, Customer, Order, etc.
View: Manages user input and displays output. It prompts users for actions such as selecting products, logging in, and confirming their order.
Controller: Handles the logic of interacting with the database and controlling the flow of the application (e.g., adding products to an order, managing user login).
Singleton Pattern:
The database connection is managed using the Singleton pattern, ensuring that only one instance of the database connection exists throughout the application's lifecycle.

Console User Flow:
Login: The user is prompted to enter their username and password to log in.
Product Selection: After logging in, the user is shown a list of available products and selects the one they want to add to their cart.
Add to Cart: The product is added to the customer's active order, or a new order is created if necessary.
Feedback: The user receives feedback about the success or failure of the operation.
