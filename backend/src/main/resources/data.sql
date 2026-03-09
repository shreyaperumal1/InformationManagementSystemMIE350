-- Users
INSERT INTO users (userId, name, email, passwordHash, role, phone) VALUES
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
INSERT INTO warehouse (warehouseId, name, address, capacityMaxPackages, region) VALUES
    (1, 'Toronto Warehouse', '100 Depot Rd, Toronto, ON' , 5000, 'Toronto'),
    (2, 'Mississauga Warehouse', '200 Gateway Blvd, Mississauga, ON', 2000, 'Mississauga'),
    (3, 'Scarborough Warehouse', '300 Scarborough Rd, Toronto, ON', 3000, 'Scarborough'),
    (4, 'Brampton Warehouse', '400 Brampton Ave, Brampton, ON', 3000, 'Brampton');
-- Vehicles
INSERT INTO vehicle (vehicleId, plate, type, capacityWeight, capacityVolume, assignedDriverId) VALUES (1,'ABCD 123','VAN',500.0,10.0,3);
-- Orders
INSERT INTO orders (orderId, customerId, createdAt, pickupAddress, dropoffAddress, totalCost, orderStatus) VALUES
  (1,  1, CURRENT_TIMESTAMP, '10 Maple Ave, Toronto, ON','55 Oak St, Hamilton, ON',25.50, 'CONFIRMED');
--Shipments
INSERT INTO shipment (shipmentId, orderId, trackingNumber, type, weight, volume, fragileFlag, currentStatus, currentWarehouseId) VALUES
  (1,  1,  'TRK-000001', 'PARCEL', 2.5,  0.50, false, 'IN_TRANSIT', 1);
--Routes
INSERT INTO route (routeId, driverid, warehouseId, routeType, plannedStartTime, plannedEndTime, routeStatus) VALUES
  (1, 3, 1, 'pickup', DATEADD('HOUR', -6, CURRENT_TIMESTAMP), DATEADD('HOUR',  2, CURRENT_TIMESTAMP), 'IN_PROGRESS');
--Route Stops
INSERT INTO route_stop (stopId, routeId, stopSequence, stopAddress, stopType, plannedTime, completedTime) VALUES
  (1,  1, 1, '10 Maple Ave, Toronto, ON','pickup',  DATEADD('HOUR', -4, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP));

--Stop Shipments
INSERT INTO stop_shipment (stopId, shipmentId, action) VALUES (1,1,'pickup');
--Tracking Events
INSERT INTO tracking_event (eventId, shipmentId, timestamp, status, locationText, note) VALUES
  (1, 1, DATEADD('HOUR', -48, CURRENT_TIMESTAMP),'ACCEPTED','Toronto Warehouse',NULL);
--Reviews
INSERT INTO review (reviewId, orderId, customerId, rating, comment, createdAt) VALUES
  (1, 1, 1, 5, 'Great service, delivered on time!', CURRENT_TIMESTAMP);