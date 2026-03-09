-- Users
INSERT INTO users (user_id, name, email, password_hash, role, phone) VALUES
    (1, 'Alice Anderson', 'alice@gmail.com', 'password123', 'CUSTOMER', '123-456-0101'),
    (2, 'Bob Baxter', 'bob@gmail.com', 'password123', 'CUSTOMER', '123-456-0202'),
    (3, 'Cameron Jones', 'cameron@postal.com', 'password123', 'DRIVER_PICKUP', '123-456-0303'),
    (4, 'Danny Smith', 'danny@gmail.com', 'password123', 'DRIVER_DELIVERY', '123-456-0404'),
    (5, 'Emily Patrick', 'emily@postal.com','password123', 'ADMIN', '123-456-0505'),
    (6, 'Franny Gaines', 'franny@gmail.com', 'password123', 'CUSTOMER', '123-456-0606'),
    (7, 'Grace Wu', 'grace@gmail.com', 'password123', 'CUSTOMER', '123-456-0707'),
    (8, 'Harold Brown', 'harold@postal.com', 'password123', 'DRIVER_PICKUP', '123-456-0808'),
    (9, 'Ilora Scott', 'ilora@postal.com', 'password123', 'DRIVER_DELIVERY', '123-456-0909'),
    (10,'Jimmy John', 'jimmy@gmail.com', 'password123', 'CUSTOMER', '123-456-1010');

-- Warehouses
INSERT INTO warehouse (warehouse_id, name, address, capacity_max_packages, region) VALUES
    (1, 'Toronto Warehouse', '' , 5000, 'Toronto'),
    (2, 'Mississauga Warehouse', '', 2000, 'Mississauga'),
    (3, 'Scarborough Warehouse', '', 3000, 'Scarborough'),
    (4, 'Brampton Warehouse', '', 3000, 'Brampton');
-- Vehicles

-- Orders

--Shipments

--Routes

--Route Stops

--Stop Shipments

--Tracking Events

--Reviews