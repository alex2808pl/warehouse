-- liquibase formatted sql

-- changeset serhii:create_table_products
CREATE TABLE products (CategoryId INT NULL, DiscountPrice DECIMAL NULL, Price DECIMAL NULL,
                       ProductId INT AUTO_INCREMENT NOT NULL, CreatedAt datetime NULL,
                       UpdatedAt datetime NULL, Description VARCHAR(255) NULL,
                       ImageURL VARCHAR(255) NULL, Name VARCHAR(255) NULL,
                       CONSTRAINT PK_PRODUCTS PRIMARY KEY (ProductId));

-- changeset serhii:create_table_categories
CREATE TABLE categories (CategoryId INT AUTO_INCREMENT NOT NULL,
                        Name VARCHAR(255) NULL, CONSTRAINT
                        PK_CATEGORIES PRIMARY KEY (CategoryId));

-- changeset serhii:create_table_prices
CREATE TABLE prices (PriceId INT AUTO_INCREMENT NOT NULL,
                     ChangeAt TIMESTAMP NULL,
                     Price DECIMAL(10, 2) NULL,
                     CreatedAt TIMESTAMP NULL,
                     ProductId INT,
                     FOREIGN KEY (ProductId) REFERENCES Products(ProductId),
                    CONSTRAINT PK_PRICES PRIMARY KEY (PriceId));

-- changeset serhii:create_index_prices
-- CREATE INDEX foreign_key_prices_products ON prices(ProductId);

-- changeset serhii:create_foreign_key_prices_products
-- ALTER TABLE prices ADD CONSTRAINT foreign_key_prices_products FOREIGN KEY (ProductId)
-- REFERENCES products (ProductId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset serhii:create_index_products
CREATE INDEX foreign_key_products_categories ON products(CategoryId);

-- changeset serhii:create_foreign_key_products_categories
ALTER TABLE products ADD CONSTRAINT foreign_key_products_categories FOREIGN KEY (CategoryId)
REFERENCES categories (CategoryId) ON UPDATE RESTRICT ON DELETE RESTRICT;

