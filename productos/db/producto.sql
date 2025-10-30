CREATE SEQUENCE seq_producto_id START WITH 1 INCREMENT BY 1;

CREATE TABLE producto (
                          id INT PRIMARY KEY DEFAULT nextval('seq_producto_id'),
                          nombre VARCHAR(100) NOT NULL,
                          precio NUMERIC(10,2) CHECK (precio >= 0)
);

ALTER SEQUENCE seq_producto_id OWNED BY producto.id;


-- Índice para búsquedas rápidas por nombre
CREATE INDEX idx_producto_nombre ON producto (nombre);

-- Índice para consultas por rango de precio
CREATE INDEX idx_producto_precio ON producto (precio);

INSERT INTO producto (nombre, precio) VALUES
      ('Laptop Lenovo', 2500.00),
      ('Mouse Logitech', 120.00)
ON CONFLICT DO NOTHING;