**0. Cài đặt**
Môi trường:
 - Centos 7 64bit
 - CPU: 8 core
 - RAM: 52GB

Cài đặt Memcached theo hướng dẫn https://github.com/memcached/memcached/wiki/Install

yum install memcached

systemctl enable memcached

systemctl start memcached

Cấu hình dung lượng tối đa lưu trữ trên memcached: vim /etc/sysconfig/memcached  , Thay đổi tham số CACHESIZE="50000" (50GB)

Cài đặt MySQL: 
wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm

rpm -ivh mysql-community-release-el7-5.noarch.rpm

yum install mysql-server

systemctl enable mysqld

systemctl start mysqld

 
**1. Hướng dẫn chạy sinh bộ dữ liệu test:**

Sử dụng class: com.memcached.generate.GenerateDataTest, chạy hàm main sẽ sinh ra được bộ dữ liệu test bao gồm:
- product.txt: 10.000 bản ghi
- customer.txt: 1.000.000 bản ghi
- order.txt: 20.000.000 bản ghi
- order_with_detail.txt: 20.000.000 bản ghi (dùng để import vào memcached)
- order_detail.txt: 80.000.000 bản ghi

**2. Import vào MySQL**
- Tạo CSDL theo cấu trúc như trong file src/resources/mysql_DDL.sql
- Đẩy dữ liệu vào trong cơ sở dữ liệu bằng các lệnh sau: 
LOAD DATA LOCAL INFILE '/home/csdl/product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE '/home/csdl/customer.txt' INTO TABLE customer;
LOAD DATA LOCAL INFILE '/home/csdl/order.txt' INTO TABLE orders;
LOAD DATA LOCAL INFILE '/home/csdl/order_detail.txt' INTO TABLE order_detail;
(Chú ý thay đường dẫn tới file dữ liệu test)

Dữ liệu được import vào mất khoảng 10 phút 

Nếu test với bảng MySQL Inmemory tham khảo cách tạo bảng trong file src/resources/mysql_DDL_In_memory.sql
Cấu trúc bảng tương tự như trên và thời gian import  vào khoảng 8phút 30giay

**3. Import vào Memcached**

Cấu hình các thông tin sau trong file src/resource/config.properties
- MEMCACHED_URL: Địa chỉ memcached
- MEMCACHED_PORT: Port của memcached
- MEMCACHED_MAX_CONNECTION: Số lượng connection tối đa
- DATA_FILE_FOLDER: Đường dẫn tới thư mục chứa data test

Chạy URL sau khi chạy lên trình duyệt: http://host:port/example/json/importMemcached
(Với host:port là đường dẫn của ứng dụng được triển khai)

Chờ tới khi dữ liệu được import vào hết memcached (~19 phút)

**4. Chạy test thử nghiệm**

Test lấy dữ liệu theo Id/Key trong bảng order_detail (80.000.000) bản ghi:

- http://host:port/example/json/memcached/getOrderDetailById

- http://host:port/example/json/mysql/getOrderDetailById

Test lấy dữ liệu join từ 2 bảng orders và order_detail

- http://host:port/example/json/memcached/getOrderPrice

- http://host:port/example/json/mysql/getOrderPrice



****
