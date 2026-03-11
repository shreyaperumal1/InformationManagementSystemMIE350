-- Users
INSERT INTO users (userId, name, email, passwordHash, role, phone) VALUES
    (1, 'Alice Anderson', 'alice@gmail.com', 'password123', 'CUSTOMER', '123-456-0101'),
    (2, 'Bob Baxter', 'bob@gmail.com', 'password123', 'CUSTOMER', '123-456-0202'),
    (3, 'Cameron Jones', 'cameron@postal.com', 'password123', 'DRIVER', '123-456-0303'),
    (4, 'Danny Smith', 'danny@gmail.com', 'password123', 'DRIVE', '123-456-0404'),
    (5, 'Emily Patrick', 'emily@postal.com','password123', 'ADMIN', '123-456-0505'),
    (6, 'Franny Gaines', 'franny@gmail.com', 'password123', 'CUSTOMER', '123-456-0606'),
    (7, 'Grace Wu', 'grace@gmail.com', 'password123', 'CUSTOMER', '123-456-0707'),
    (8, 'Harold Brown', 'harold@postal.com', 'password123', 'DRIVER', '123-456-0808'),
    (9, 'Ilora Scott', 'ilora@postal.com', 'password123', 'DRIVER', '123-456-0909'),
    (10,'Jimmy John', 'jimmy@gmail.com', 'password123', 'CUSTOMER', '123-456-1010');
-- Address
INSERT INTO address (addressId, buildingType, aptNum, streetNum, streetName, streetType, city, provinceOrState, country, postalOrZip) VALUES
    (1, 'WAREHOUSE', null, 178, 'High Park', 'AVENUE', 'Toronto', 'Ontario', 'Canada', 'M6P2S4'),
    (2, 'WAREHOUSE', null, 4415, 'Mississauga', 'ROAD', 'Mississauga', 'Ontario', 'Canada', 'L5M3G8'),
    (3, 'WAREHOUSE', null, 900, 'Markham', 'ROAD', 'Scarborough', 'Ontario', 'Canada', 'M1H2Y2'),
    (4, 'WAREHOUSE', null, 25, 'Peel Centre', 'DRIVE', 'Brampton', 'Ontario', 'Canada', 'L6T3R5'),
    (5, 'WAREHOUSE', null, 2420, 'Sixth', 'LINE', 'Oakville', 'Ontario', 'Canada', 'L6H5Z8'),
    (6, 'WAREHOUSE', null, 1866, 'Liverpool', 'ROAD', 'Pickering', 'Ontario', 'Canada', 'L1V1W3'),
    (7, 'WAREHOUSE', null, 1, 'Wellington', 'STREET', 'Ottawa', 'Ontario', 'Canada', 'K1A0A9'),
    (8, 'WAREHOUSE', null, 1127, 'Mont-Royal', 'AVENUE', 'Montreal', 'Quebec', 'Canada', 'H2J1X9'),
    (9, 'WAREHOUSE', null, 4852, 'Clifton', 'HILL', 'Niagara Falls', 'Ontario', 'Canada', 'L2G3N4'),
    (10, 'WAREHOUSE', null, 1235, 'Richmond', 'STREET', 'London', 'Ontario', 'Canada', 'N6A0C1'),
    (11, 'APARTMENT', 1902, 5785, 'Victoria', 'AVENUE', 'Niagara Falls', 'Ontario', 'Canada', 'L2G3L6'),
    (12, 'HOTEL', 22A, 99, 'Wellesley', 'STREET', 'Toronto', 'Ontario', 'Canada', 'M7A1W3'),
    (13, 'HOUSE', null, 140, 'King', 'STREET', 'Toronto', 'Ontario', 'Canada', 'M5H3Y2'),
    (14, 'HOUSE', null, 33, 'Yonge', 'STREET', 'Toronto', 'Ontario', 'Canada', 'M5E1G4'),
    (15, 'HOUSE', null, 6360, 'Hawthorne', 'DRIVE', 'Windsor', 'Ontario', 'Canada', 'N8T1J9'),
    (16, 'HOUSE', null, 50, 'Ouellette', 'AVENUE', 'Windsor', 'Ontario', 'Canada', 'N9A6T3'),
    (17, 'HOUSE', null, 100, 'King', 'STREET', 'Hamilton', 'Ontario', 'Canada', 'L8P1A2'),
    (18, 'HOUSE', null, 555, 'University', 'AVENUE', 'Toronto', 'Ontario', 'Canada', 'M5G1X8'),
    (19, 'HOUSE', null, 200, 'Dundas', 'STREET', 'Belleville', 'Ontario', 'Canada', 'K8N1E4'),
    (20, 'HOUSE', null, 181, 'Queen', 'STREET', 'Kitchener', 'Ontario', 'Canada', 'N2H2H2'),
    (21, 'HOUSE', null, 1, 'Stone', 'ROAD', 'Guelph', 'Ontario', 'Canada', 'N1G2W1'),
    (22, 'HOUSE', null, 10, 'Dundas', 'STREET', 'Toronto', 'Ontario', 'Canada', 'M5B2G9'),
    (23, 'HOUSE', null, 77, 'Bay', 'STREET', 'Toronto', 'Ontario', 'Canada', 'M5J2S1');
-- Warehouses
INSERT INTO warehouse (warehouseId, name, addressId, capacityMaxPackages, region) VALUES
    (1, 'Toronto Warehouse', 1 , 5000, 'Toronto'),
    (2, 'Mississauga Warehouse', 2, 2000, 'Mississauga'),
    (3, 'Scarborough Warehouse', 3, 3000, 'Scarborough'),
    (4, 'Brampton Warehouse', 4, 3000, 'Brampton'),
    (5, 'Oakville Warehouse', 5, 10000, 'Oakville'),
    (6, 'Pickering Warehouse', 6, 2000, 'Pickering'),
    (7, 'Ottawa Warehouse', 7, 1000, 'Ottawa'),
    (8, 'Montreal Warehouse', 8, 20000, 'Montreal'),
    (9, 'Niagara Warehouse', 9, 5000, 'Niagara'),
    (10, 'London Warehouse', 10, 3000, 'London');
-- Vehicles
INSERT INTO vehicle (vehicleId, plate, type, capacityWeight, capacityVolume) VALUES
    (1,'ABCD 123','VAN',500.0,10.0),
    (2,'EFGH 456','TRUCK',2000.0,40.0),
    (3,'IJKL 789','VAN',600.0,12.0),
    (4,'MNOP 321','BIKE',50.0,1.5),
    (5,'QRST 654','TRUCK',1800.0,35.0),
    (6,'UVWX 987','VAN',550.0,11.0),
    (7,'YZAB 111','TRUCK',2200.0,45.0),
    (8,'CDEF 222','BIKE',40.0,1.2),
    (9,'GHIJ 333','VAN',520.0,10.5),
    (10,'KLMN 444','TRUCK',2500.0,50.0);
-- Orders (1,2,6,7,10 Ids are customers)
INSERT INTO orders (orderId, customerId, createdAt, pickupAddressId, dropoffAddressId, totalCost, orderStatus) VALUES
   (1,  1, CURRENT_TIMESTAMP, 23, 20, 25.50, 'CONFIRMED'),
   (2,  2, CURRENT_TIMESTAMP, 13, 17, 18.75, 'DELIVERED'),
   (3,  6, CURRENT_TIMESTAMP, 14, 15, 42.00, 'IN_TRANSIT'),
   (4,  7, CURRENT_TIMESTAMP, 16, 18, 21.30, 'PENDING'),
   (5, 10, CURRENT_TIMESTAMP, 19, 21, 33.90, 'CONFIRMED'),
   (6,  1, CURRENT_TIMESTAMP, 22, 11, 27.45, 'IN_TRANSIT'),
   (7,  2, CURRENT_TIMESTAMP, 20, 23, 16.80, 'DELIVERED'),
   (8,  6, CURRENT_TIMESTAMP, 17, 16, 24.60, 'CONFIRMED'),
   (9,  7, CURRENT_TIMESTAMP, 15, 19, 38.25, 'PENDING'),
   (10, 10, CURRENT_TIMESTAMP, 18, 13, 19.99, 'CONFIRMED');
--Shipments
INSERT INTO shipment (shipmentId, orderId, trackingNumber, type, weight, volume, fragileFlag, currentStatus, currentWarehouseId) VALUES
    (1,  1,  'TRK-000001', 'PARCEL', 2.5, 0.50, false, 'IN_TRANSIT', 1),
    (2,  2,  'TRK-000002', 'PARCEL', 1.8, 0.30, false, 'DELIVERED', 2),
    (3,  3,  'TRK-000003', 'PARCEL', 4.2, 0.90, true,  'IN_TRANSIT', 3),
    (4,  4,  'TRK-000004', 'PARCEL', 3.1, 0.60, false, 'PENDING', 4),
    (5,  5,  'TRK-000005', 'PARCEL', 6.4, 1.20, true,  'CONFIRMED', 5),
    (6,  6,  'TRK-000006', 'PARCEL', 2.0, 0.40, false, 'IN_TRANSIT', 6),
    (7,  7,  'TRK-000007', 'PARCEL', 1.5, 0.25, false, 'DELIVERED', 7),
    (8,  8,  'TRK-000008', 'PARCEL', 3.7, 0.70, true,  'CONFIRMED', 8),
    (9,  9,  'TRK-000009', 'PARCEL', 5.0, 1.10, false, 'PENDING', 9),
    (10, 10, 'TRK-000010', 'PARCEL', 2.9, 0.55, false, 'IN_TRANSIT', 10);
--Routes
INSERT INTO route (routeId, driverid, startwarehouseId, stops, plannedStartTime, plannedEndTime, routeStatus) VALUES
    (1, 3, 1, '[1,2,3,4,5,6]',3],2,3,4,5,6), DATEADD('HOUR', -6, CURRENT_TIMESTAMP), DATEADD('HOUR',  2, CURRENT_TIMESTAMP), 'IN_PROGRESS');
--Route Stops
INSERT INTO route_stop (stopId, routeId, stopSequence, stopAddress, stopType, plannedTime, completedTime) VALUES
  (1, 1, 1, 15, 'PICKUP',  DATEADD('HOUR', -4, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
  (2, 1, 2, 23, 'PICKUP',  DATEADD('HOUR', -3, CURRENT_TIMESTAMP), NULL),
  (3, 1, 3, 20, 'DELIVERY', DATEADD('HOUR', -1, CURRENT_TIMESTAMP), NULL),
  (4, 1, 4, 13, 'PICKUP',  DATEADD('MINUTE', 30, CURRENT_TIMESTAMP), NULL),
  (5, 1, 5, 17, 'DELIVERY', DATEADD('HOUR', 1, CURRENT_TIMESTAMP), NULL),
  (6, 1, 6, 18, 'DELIVERY', DATEADD('HOUR', 2, CURRENT_TIMESTAMP), NULL);
--Stop Shipments
INSERT INTO stop_shipment (stopId, shipmentId, action) VALUES
   (1,1,'pickup');
--Tracking Events
INSERT INTO tracking_event (eventId, shipmentId, timestamp, status, locationText, note) VALUES
    (2, 1, DATEADD('HOUR', -24, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'Toronto Warehouse', NULL),
    (3, 1, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 'OUT_FOR_DELIVERY', 'Kitchener', NULL),
    (4, 2, DATEADD('DAY', -3, CURRENT_TIMESTAMP), 'ACCEPTED', 'Mississauga Warehouse', NULL),
    (5, 2, DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'Mississauga Warehouse', NULL),
    (6, 2, DATEADD('DAY', -1, CURRENT_TIMESTAMP), 'DELIVERED', 'Hamilton', 'Package delivered to recipient'),
    (7, 3, DATEADD('HOUR', -12, CURRENT_TIMESTAMP), 'ACCEPTED', 'Scarborough Warehouse', NULL),
    (8, 3, DATEADD('HOUR', -6, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'Toronto Warehouse', NULL),
    (9, 4, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 'ACCEPTED', 'Brampton Warehouse', 'Awaiting pickup'),
    (10, 5, DATEADD('HOUR', -5, CURRENT_TIMESTAMP), 'ACCEPTED', 'Oakville Warehouse', NULL),
    (11, 5, DATEADD('HOUR', -1, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'Oakville Warehouse', NULL),
    (12, 6, DATEADD('HOUR', -10, CURRENT_TIMESTAMP), 'ACCEPTED', 'Pickering Warehouse', NULL),
    (13, 6, DATEADD('HOUR', -4, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'Toronto Warehouse', NULL),
    (14, 7, DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'ACCEPTED', 'Ottawa Warehouse', NULL),
    (15, 7, DATEADD('DAY', -1, CURRENT_TIMESTAMP), 'DELIVERED', 'Toronto', 'Package delivered successfully'),
    (16, 8, DATEADD('HOUR', -3, CURRENT_TIMESTAMP), 'ACCEPTED', 'Montreal Warehouse', NULL),
    (17, 9, DATEADD('HOUR', -1, CURRENT_TIMESTAMP), 'ACCEPTED', 'Niagara Warehouse', 'Waiting for route assignment'),
    (18, 10, DATEADD('HOUR', -6, CURRENT_TIMESTAMP), 'ACCEPTED', 'London Warehouse', NULL),
    (19, 10, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 'IN_TRANSIT', 'London Warehouse', NULL);
--Reviews
INSERT INTO review (reviewId, orderId, customerId, rating, comment, createdAt) VALUES
   (1, 1, 1, 5, 'Great service, delivered on time!', CURRENT_TIMESTAMP),
   (2, 2, 2, 4, 'Package arrived safely and the tracking updates were helpful.', CURRENT_TIMESTAMP),
   (3, 3, 6, 5, 'Very smooth delivery process and polite driver.', CURRENT_TIMESTAMP),
   (4, 4, 7, 3, 'Order was fine but the delivery took a bit longer than expected.', CURRENT_TIMESTAMP),
   (5, 5, 10, 4, 'Package was handled carefully and arrived in good condition.', CURRENT_TIMESTAMP),
   (6, 6, 1, 5, 'Fast service and easy pickup scheduling.', CURRENT_TIMESTAMP),
   (7, 7, 2, 4, 'Everything arrived correctly and on time.', CURRENT_TIMESTAMP),
   (8, 8, 6, 5, 'Excellent experience from pickup to dropoff.', CURRENT_TIMESTAMP),
   (9, 9, 7, 3, 'Tracking was decent but there were some delays.', CURRENT_TIMESTAMP),
   (10, 10, 10, 4, 'Good overall service and reasonable delivery time.', CURRENT_TIMESTAMP);