DROP TABLE IF EXISTS client_item;

CREATE TABLE client_item(
    client_id UUID REFERENCES client(id) ON DELETE CASCADE,
    product_id UUID REFERENCES product(id) ON DELETE CASCADE
)