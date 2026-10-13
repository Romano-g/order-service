CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_products_name
        CHECK (LENGTH(TRIM(name)) > 0),

    CONSTRAINT chk_products_price
        CHECK (price >= 0)
);