CREATE TABLE IF NOT EXISTS `contact_msg` (
    `contact_id` int AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `mobile_num` varchar(10) NOT NULL,
    `email` varchar(100) NOT NULL,
    `message` varchar(500) NOT NULL,
    `status` varchar(10) NOT NUll, -- status of a contact message whether it's been read or not.
    --timestamp and admin information
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `services` (
    `serviceName` varchar(100) NOT NULL,
    `serviceDetails` varchar(500) NOT NULL,
    `type` varchar(50) NOT NULL,
    --timestamp and admin information
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);