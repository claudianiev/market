-- ===============================================
-- Script de creación de base de datos: inventario_db
-- Microservicio: Inventario
-- ===============================================

CREATE TABLE IF NOT EXISTS producto_ref (
        id BIGINT PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        precio NUMERIC(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventario (
      id SERIAL PRIMARY KEY,
      producto_ref_id BIGINT NOT NULL,
      cantidad_disponible INT NOT NULL DEFAULT 0,
      ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT fk_producto_ref FOREIGN KEY (producto_ref_id)
          REFERENCES producto_ref (id)
          ON DELETE CASCADE
);

INSERT INTO producto_ref (id, nombre, precio) VALUES
      (1, 'Laptop Lenovo Pro X', 2999.99),
      (2, 'Mouse Inalámbrico', 39.90),
      (3, 'Teclado Mecánico', 120.50);

INSERT INTO inventario (producto_ref_id, cantidad_disponible) VALUES
     (1, 10),
     (2, 100),
     (3, 50);