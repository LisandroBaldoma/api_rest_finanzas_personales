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

-- Insertar subcategorías bajo 'Electrónica'
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Laptops', 'Gastos relacionados con computadoras portátiles', (SELECT id FROM categories WHERE name = 'Electrónica'), true),
('Teléfonos', 'Gastos en teléfonos móviles y accesorios', (SELECT id FROM categories WHERE name = 'Electrónica'), true);

-- Insertar subcategorías bajo 'Muebles'
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Sofás', 'Gastos en sofás y muebles de sala', (SELECT id FROM categories WHERE name = 'Muebles'), true),
('Mesas', 'Gastos en mesas y muebles de comedor', (SELECT id FROM categories WHERE name = 'Muebles'), true);

-- Insertar subcategorías bajo 'Alimentos'
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Frutas', 'Gastos en frutas frescas', (SELECT id FROM categories WHERE name = 'Alimentos'), true),
('Carnes', 'Gastos en productos cárnicos', (SELECT id FROM categories WHERE name = 'Alimentos'), true);

-- Insertar subcategorías bajo 'Entretenimiento'
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Cine', 'Gastos en entradas de cine', (SELECT id FROM categories WHERE name = 'Entretenimiento'), true),
('Eventos', 'Gastos en conciertos y eventos en vivo', (SELECT id FROM categories WHERE name = 'Entretenimiento'), true);

-- Insertar subcategorías bajo 'Ropa'
INSERT INTO categories (name, description, parent_category_id, is_active) VALUES
('Hombres', 'Gastos en ropa para hombres', (SELECT id FROM categories WHERE name = 'Ropa'), true),
('Mujeres', 'Gastos en ropa para mujeres', (SELECT id FROM categories WHERE name = 'Ropa'), true);

