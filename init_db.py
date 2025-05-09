import sqlite3

# Tạo kết nối đến cơ sở dữ liệu
def create_connection():
    conn = sqlite3.connect('myshop.db')  # Tạo cơ sở dữ liệu myshop.db nếu chưa có
    return conn

# Tạo bảng trong cơ sở dữ liệu
def create_tables():
    conn = create_connection()
    cursor = conn.cursor()

    # Tạo bảng sản phẩm
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS sanpham (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        ten TEXT NOT NULL,
        gia REAL NOT NULL,
        hinhanh TEXT,
        mota TEXT
    )''')

    # Tạo bảng khách hàng
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS khachhang (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        ten TEXT NOT NULL,
        sdt TEXT NOT NULL UNIQUE,
        diachi TEXT
    )''')

    # Tạo bảng hóa đơn
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS hoadon (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        id_khachhang INTEGER,
        id_sanpham INTEGER,
        thoigian TEXT NOT NULL,
        FOREIGN KEY(id_khachhang) REFERENCES khachhang(id),
        FOREIGN KEY(id_sanpham) REFERENCES sanpham(id)
    )''')

    conn.commit()  # Lưu thay đổi
    conn.close()  # Đóng kết nối

# Chạy hàm tạo bảng
create_tables()
print("Các bảng đã được tạo thành công trong cơ sở dữ liệu!")
