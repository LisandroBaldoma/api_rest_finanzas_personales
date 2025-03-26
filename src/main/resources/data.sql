CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,        -- ID único para cada categoría
    name VARCHAR(50) NOT NULL,                   -- Nombre de la categoría (con restricción de longitud)
    description VARCHAR(255),                    -- Descripción de la categoría
    is_active BOOLEAN DEFAULT TRUE,              -- Estado de la categoría (activa o no)
    parent_category_id BIGINT,                   -- Relación con la categoría principal (para subcategorías)
    FOREIGN KEY (parent_category_id) REFERENCES categories(id)  -- Definición de la relación con la misma tabla
);

CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- ID único para cada gasto
    amount DECIMAL(10,2) NOT NULL,             -- Monto del gasto con 2 decimales
    date DATE NOT NULL,                        -- Fecha del gasto
    description VARCHAR(255),                  -- Descripción opcional del gasto
    payment_method VARCHAR(50) NOT NULL,       -- Método de pago (Efectivo, Tarjeta, etc.)
    currency VARCHAR(10) NOT NULL,             -- Tipo de moneda (USD, ARS, etc.)
    user_id BIGINT NOT NULL,                   -- Relación con usuario que hizo el gasto
    category_id BIGINT NOT NULL,               -- Relación con la categoría del gasto
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,    -- Si se borra un usuario, se eliminan sus gastos
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL -- Si se borra una categoría, los gastos quedan sin categoría
);

-- Insertar datos en 'users'
INSERT INTO users (name, email, password) VALUES
('John Doe', 'john.doe@example.com', 'admin'),
('Ana Pérez', 'ana.perez@example.com', 'password123'),
('Luis Martínez', 'luis.martinez@example.com', 'securePass'),
('Laura Gómez', 'laura.gomez@example.com', '1234abcd'),
('Carlos Sánchez', 'carlos.sanchez@example.com', 'qwerty123'),
('Marta López', 'marta.lopez@example.com', 'martaPass'),
('Pedro Ramírez', 'pedro.ramirez@example.com', 'admin2025'),
('María Rodríguez', 'maria.rodriguez@example.com', 'maria2025'),
('José Fernández', 'jose.fernandez@example.com', 'josePass'),
('Lucía Díaz', 'lucia.diaz@example.com', 'luciaSecure');

-- Insertar categorías principales
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Electrónica', 'Gastos relacionados con productos electrónicos', NULL, true),
('Muebles', 'Gastos en muebles y decoración para el hogar', NULL, true),
('Alimentos', 'Gastos relacionados con la compra de alimentos', NULL, true),
('Entretenimiento', 'Gastos en cine, eventos y actividades recreativas', NULL, true),
('Ropa', 'Compras de ropa y moda', NULL, true),
('Libros', 'Gastos en materiales de lectura', NULL, true),
('Viajes', 'Gastos para viajes y vacaciones', NULL, true),
('Salud', 'Gastos relacionados con salud y servicios médicos', NULL, true),
('Educación', 'Gastos para educación y aprendizaje', NULL, true),
('Deportes', 'Gastos relacionados con actividades deportivas', NULL, true);

-- Insertar subcategorías
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Laptops', 'Gastos relacionados con computadoras portátiles', (SELECT id FROM categories WHERE name = 'Electrónica'), true),
('Teléfonos', 'Gastos en teléfonos móviles y accesorios', (SELECT id FROM categories WHERE name = 'Electrónica'), true);

INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Sofás', 'Gastos en sofás y muebles de sala', (SELECT id FROM categories WHERE name = 'Muebles'), true),
('Mesas', 'Gastos en mesas y muebles de comedor', (SELECT id FROM categories WHERE name = 'Muebles'), true);

INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Frutas', 'Gastos en frutas frescas', (SELECT id FROM categories WHERE name = 'Alimentos'), true),
('Carnes', 'Gastos en productos cárnicos', (SELECT id FROM categories WHERE name = 'Alimentos'), true);

INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Cine', 'Gastos en entradas de cine', (SELECT id FROM categories WHERE name = 'Entretenimiento'), true),
('Eventos', 'Gastos en conciertos y eventos en vivo', (SELECT id FROM categories WHERE name = 'Entretenimiento'), true);

INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Hombres', 'Gastos en ropa para hombres', (SELECT id FROM categories WHERE name = 'Ropa'), true),
('Mujeres', 'Gastos en ropa para mujeres', (SELECT id FROM categories WHERE name = 'Ropa'), true);

-- Insertar gastos aleatorios
INSERT INTO expenses (amount, date, description, payment_method, currency, user_id, category_id) VALUES
(150.75, '2025-03-15', 'Compra de laptop', 'Credito', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Laptops' LIMIT 1)),

(75.50, '2025-03-10', 'Cena en restaurante', 'Efectivo', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Alimentos' LIMIT 1)),

(200.00, '2025-03-05', 'Entrada de cine', 'Debito', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Cine' LIMIT 1)),

(500.00, '2025-02-28', 'Compra de teléfono móvil', 'Transferencia', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Teléfonos' LIMIT 1)),

(30.00, '2025-03-20', 'Compra de libro', 'Credito', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Libros' LIMIT 1)),

(250.00, '2025-03-12', 'Compra de ropa para hombre', 'Efectivo', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Hombres' LIMIT 1)),

(80.00, '2025-03-08', 'Consulta médica', 'Transferencia', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Salud' LIMIT 1)),

(120.00, '2025-03-18', 'Membresía de gimnasio', 'Credito', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Deportes' LIMIT 1)),

(40.00, '2025-03-02', 'Compra de mesa de comedor', 'Efectivo', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Mesas' LIMIT 1)),

(90.00, '2025-03-22', 'Cena en familia', 'Debito', 'ARS',
 (SELECT id FROM users ORDER BY RAND() LIMIT 1),
 (SELECT id FROM categories WHERE name = 'Alimentos' LIMIT 1));

