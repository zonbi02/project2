/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * 
 */
public class Init {

    private static String[] initializeDatabase = {
        "use dormmanager;",
        "create table `users` (\n"
        + "  `id` int not null auto_increment,\n"
        + "  `username` varchar(20) not null,\n"
        + "  `password` varchar(50) not null,\n"
        + "  `deleted` int not null default '0',\n"
        + "  primary key (`id`),\n"
        + "  unique key `username` (`username`)\n"
        + ") engine=InnoDB  default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;",
        
        "insert into users (username, password) value (\"admin\", \"admin\");",
        
        "create table `user_detail` (\n"
        + "  `user_id` int not null,\n"
        + "  `full_name` varchar(50) not null,\n"
        + "  `email` varchar(50) not null,\n"
        + "  `phone` varchar(11) not null,\n"
        + "  `role` varchar(5) not null default 'Staff',\n"
        + "  `address` varchar(50) not null,\n"
        + "  `joined_date` timestamp null default current_timestamp,\n"
        + "  primary key (`user_id`),\n"
        + "  unique key `email` (`email`),\n"
        + "  unique key `phone` (`phone`),\n"
        + "  constraint `user_detail` foreign key (`user_id`) references `users` (`id`)\n"
        + ") engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;",
        
        "insert into user_detail value (1, \"root\", \"root@dormmanager.com\", \"0123456789\", \"Admin\", \"th\", current_timestamp());",
        
        "create table `rooms` (\n"
        + "  `id` int not null AUTO_INCREMENT,\n"
        + "  `name` varchar(5) not null,\n"
        + "  `capacity` int not null default '8',\n"
        + "  `occupied` int not null default '0',\n"
        + "  `deleted` int not null default '0',\n"
        + "  primary key (`id`),\n"
        + "  unique key `name` (`name`)\n"
        + ") engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;",
        
        "create table `students` (\n"
        + "  `id` int not null AUTO_INCREMENT,\n"
        + "  `room_id` int not null,\n"
        + "  `full_name` varchar(50) not null,\n"
        + "  `gender` varchar(7) not null,\n"
        + "  `phone` varchar(11) not null,\n"
        + "  `email` varchar(50) not null,\n"
        + "  `address` varchar(50) not null,\n"
        + "  `joined_date` timestamp null default current_timestamp,\n"
        + "  `deleted` int not null default '0',\n"
        + "  primary key (`id`),\n"
        + "  unique key `phone` (`phone`),\n"
        + "  unique key `email` (`email`),\n"
        + "  key `student_room_idx` (`room_id`),\n"
        + "  constraint `student_room` foreign key (`room_id`) references `rooms` (`id`)\n"
        + ") engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;",
        
        "create table `bills` (\n"
        + "  `id` int not null AUTO_INCREMENT,\n"
        + "  `water` int not null default '0',\n"
        + "  `electricity` int not null default '0',\n"
        + "  `time` varchar(10) default null,\n"
        + "  `room_id` int default null,\n"
        + "  `payment_status` varchar(10) default 'Not paid',\n"
        + "  `deleted` int not null default '0',\n"
        + "  primary key (`id`),\n"
        + "  key `bill_room_idx` (`room_id`),\n"
        + "  constraint `bill_room` foreign key (`room_id`) references `rooms` (`id`)\n"
        + ") engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;",
        
        "create trigger inc_room after insert on students \n"
        + " for each row\n"
        + " update rooms set occupied = occupied + 1 where id = new.room_id;",
        
        "create trigger dec_room after update on students\n"
        + " for each row\n"
        + " update rooms set occupied = (select count(*) from students where deleted = 0 and room_id = room_id) where id = new.room_id; \n"
    };

    public static void initDbInfo() {
        Connection conn = Connect_db.getConnection();
        try {
            for (String i : initializeDatabase) {
                conn.createStatement().execute(i);
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
    
}
