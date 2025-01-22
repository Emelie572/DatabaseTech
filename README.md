# Shoe Shop Database

## Project Overview
This project is designed to model a shoe shop database. The database contains information about products, customers, orders, and the relationships between them. It includes tables for storing product details, customer data, orders, and sales transactions.

## Database Schema

The database contains the following key entities:
- **ProductType**:
- **ProductCategory**: 
- **Product**:
- **Brand**:
- **ProductOption**:
- **Customers**: Data on customers, such as their names and location.
- **Orders**: Orders made by customers, including the total price.
- **OrderItems**: Details of products ordered in each order.

## Database Design

- **ER Diagram**: A visual representation of the database entities, relationships, and attributes. 
  - 
  
- **Relations Model**: Describes the tables, columns, and relationships in the database. 
  - 

## SQL Scripts

- **DDL Script**: A SQL file that creates the necessary tables and fills the database with sample data.
  - 
  
- **DML Script**: A SQL file that answers specific questions based on the database.
  - Available as 

## Sample Data

The database includes the following data:

- **Products**: At least 8 products (e.g., sandals, sneakers, boots, etc.).
- **Categories**: At least 5 product categories (e.g., sandals, sneakers, boots, etc.).
- **Customers**: At least 5 customers with different purchasing histories.
- **Orders**: At least 6 orders placed by customers.

## Questions Answered by the Database

The following questions can be answered using SQL queries on the database:

1. **Which customers have bought black sandals in size 38 from the brand Ecco?**  
   The query lists customer names without hardcoded IDs.
   
2. **List the number of products per category.**  
   The result includes category names and the total number of products in each category.
   
3. **Generate a customer list with the total amount spent by each customer.**  
   Displays each customer's first and last name along with their total spending.
   
4. **Print the total order value per location (city) where the total order value is greater than 1000 SEK.**  
   The list includes city names and order values. It ensures that there are cities with order values less than 1000 SEK.
   
5. **Create a top-5 list of the most sold products.**  
   Lists the top 5 most sold products based on total sales.

6. **Which month had the highest sales?**  
   The query will return the month with the largest total sales, based on the data in the database.
