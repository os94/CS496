 CREATE TABLE IF NOT EXISTS `tbl_product` (  
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `name` varchar(255) NOT NULL,  
  `image` varchar(255) NOT NULL,  
  `price` double(10,2) NOT NULL,  
  PRIMARY KEY (`id`)  
 ) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;  
 --  
 -- Dumping data for table `tbl_product`  
 --  
 INSERT INTO `tbl_product` (`id`, `name`, `image`, `price`) VALUES  
 (1, 'Cap (Black)', 'Cap(Black).png', 20.00),  
 (2, 'Cap (White)', 'Cap(White).png', 20.00),  
 (3, 'Black Sweatshirt', 'Black Sweatshirt.png', 35.00),  
 (4, 'Sleeveless A (men)', 'Sleeveless A (men).png', 25.00),  
 (5, 'Sleeveless B (men)', 'Sleeveless B (men).png', 25.00),  
 (6, 'Sweatshirt A (men)', 'Sweatshirt A (men).png', 35.00),  
 (7, 'Sweatshirt B (men)', 'Sweatshirt B (men).png', 35.00),  
 (8, 'T-Shirt', 'T-Shirt.png', 20.00),  
 (9, 'T-Shirt (White)', 'T-Shirt (White).png', 17.00),  
 (10, 'Longsleeve A (women)', 'Longsleeve A (women).png', 30.00),  
 (11, 'Longsleeve B (women)', 'Longsleeve B (women).png', 30.00),  
 (12, 'Sweatshirt A (women)', 'Sweatshirt A (women).png', 35.00),
 (13, 'Sweatshirt A (women)', 'Sweatshirt A (women).png', 35.00),
 (14, '(One-piece) Dress', '(One-piece) Dress.png', 35.00),  
 (15, 'Pillow (A)', 'Pillow A.png', 13.00),  
 (16, 'Pillow (B)', 'Pillow B.png', 13.00),  
 (17, 'Pillow (C)', 'Pillow C.png', 13.00),  
 (18, 'Pillow (D)', 'Pillow D.png', 13.00),  
 (19, 'Mug Cup', 'Mug Cup.png', 10.00)
