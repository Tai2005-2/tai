from flask import Flask, jsonify, request
from flask_cors import CORS
import sqlite3
from datetime import datetime

app = Flask(__name__)
CORS(app)

DB_NAME = r"D:\android\007\myshop.db"


def get_db_connection():
    conn = sqlite3.connect(DB_NAME)
    conn.row_factory = sqlite3.Row
    return conn


# ------------------ API Sản Phẩm ------------------

@app.route('/sanpham', methods=['GET'])
def get_all_sanpham():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM sanpham").fetchall()
    conn.close()
    response = jsonify([dict(row) for row in rows])
    response.headers['Content-Type'] = 'application/json; charset=utf-8'  # Đảm bảo header trả về là UTF-8
    return response


@app.route('/sanpham/<int:id>', methods=['GET'])
def get_sanpham(id):
    conn = get_db_connection()
    row = conn.execute("SELECT * FROM sanpham WHERE id = ?", (id,)).fetchone()
    conn.close()
    if row:
        return jsonify(dict(row))
    return jsonify({'error': 'Not found'}), 404


@app.route('/sanpham', methods=['POST'])
def create_sanpham():
    data = request.json
    conn = get_db_connection()
    conn.execute("INSERT INTO sanpham (ten, gia, hinhanh, mota) VALUES (?, ?, ?, ?)",
                 (data['ten'], data['gia'], data['hinhanh'], data['mota']))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Created'}), 201


@app.route('/sanpham/<int:id>', methods=['PUT'])
def update_sanpham(id):
    data = request.json
    conn = get_db_connection()
    conn.execute("UPDATE sanpham SET ten = ?, gia = ?, hinhanh = ?, mota = ? WHERE id = ?",
                 (data['ten'], data['gia'], data['hinhanh'], data['mota'], id))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Updated'})


@app.route('/sanpham/<int:id>', methods=['DELETE'])
def delete_sanpham(id):
    conn = get_db_connection()
    conn.execute("DELETE FROM sanpham WHERE id = ?", (id,))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Deleted'})


# ------------------ API Khách Hàng ------------------

@app.route('/khachhang', methods=['GET'])
def get_all_khachhang():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM khachhang").fetchall()
    conn.close()
    return jsonify([dict(row) for row in rows])


@app.route('/khachhang/<int:id>', methods=['GET'])
def get_khachhang(id):
    conn = get_db_connection()
    row = conn.execute("SELECT * FROM khachhang WHERE id = ?", (id,)).fetchone()
    conn.close()
    if row:
        return jsonify(dict(row))
    return jsonify({'error': 'Not found'}), 404


@app.route('/khachhang', methods=['POST'])
def create_khachhang():
    data = request.json
    conn = get_db_connection()
    conn.execute("INSERT INTO khachhang (ten, sdt, diachi) VALUES (?, ?, ?)",
                 (data['ten'], data['sdt'], data['diachi']))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Created'}), 201


@app.route('/khachhang/<int:id>', methods=['PUT'])
def update_khachhang(id):
    data = request.json
    conn = get_db_connection()
    conn.execute("UPDATE khachhang SET ten = ?, sdt = ?, diachi = ? WHERE id = ?",
                 (data['ten'], data['sdt'], data['diachi'], id))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Updated'})


@app.route('/khachhang/<int:id>', methods=['DELETE'])
def delete_khachhang(id):
    conn = get_db_connection()
    conn.execute("DELETE FROM khachhang WHERE id = ?", (id,))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Deleted'})


# ------------------ API Hóa Đơn ------------------

@app.route('/hoadon', methods=['GET'])
def get_all_hoadon():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM hoadon").fetchall()
    conn.close()
    return jsonify([dict(row) for row in rows])


@app.route('/hoadon/<int:id>', methods=['GET'])
def get_hoadon(id):
    conn = get_db_connection()
    row = conn.execute("SELECT * FROM hoadon WHERE id = ?", (id,)).fetchone()
    conn.close()
    if row:
        return jsonify(dict(row))
    return jsonify({'error': 'Not found'}), 404


@app.route('/hoadon', methods=['POST'])
def create_hoadon():
    data = request.json
    # Kiểm tra xem khách hàng và sản phẩm có tồn tại không
    conn = get_db_connection()
    customer = conn.execute("SELECT * FROM khachhang WHERE id = ?", (data['id_khachhang'],)).fetchone()
    product = conn.execute("SELECT * FROM sanpham WHERE id = ?", (data['id_sanpham'],)).fetchone()
    
    if not customer or not product:
        conn.close()
        return jsonify({'error': 'Customer or product not found'}), 404

    # Tạo hóa đơn
    conn.execute("INSERT INTO hoadon (id_khachhang, id_sanpham, thoigian) VALUES (?, ?, ?)",
                 (data['id_khachhang'], data['id_sanpham'], data['thoigian']))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Created'}), 201


@app.route('/hoadon/<int:id>', methods=['PUT'])
def update_hoadon(id):
    data = request.json
    conn = get_db_connection()
    conn.execute("UPDATE hoadon SET id_khachhang = ?, id_sanpham = ?, thoigian = ? WHERE id = ?",
                 (data['id_khachhang'], data['id_sanpham'], data['thoigian'], id))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Updated'})


@app.route('/hoadon/<int:id>', methods=['DELETE'])
def delete_hoadon(id):
    conn = get_db_connection()
    conn.execute("DELETE FROM hoadon WHERE id = ?", (id,))
    conn.commit()
    conn.close()
    return jsonify({'message': 'Deleted'})


# ------------------ Tạo Dữ Liệu Mẫu ------------------

def create_sample_data():
    conn = get_db_connection()

    # Kiểm tra xem bảng đã có dữ liệu chưa
    existing_data = conn.execute("SELECT COUNT(*) FROM sanpham").fetchone()[0]
    
    if existing_data == 0:  # Chỉ thêm dữ liệu mẫu nếu bảng trống
        sample_data = [
            ("Dien Thoai A", 3500000, "a.jpg", "Pin trau, man 6.5 inch"),
            ("Laptop B", 15000000, "b.png", "Laptop RAM 16GB"),
            ("Tai nghe C", 550000, "c.jpg", "Bluetooth, pin 12h"),
            ("Chuot D", 250000, "d.jpg", "Chuot DPI cao"),
            ("Ban phim E", 500000, "e.png", "Ban phim co RGB")
        ]
        
        # Thêm dữ liệu vào bảng Sản phẩm
        conn.executemany("INSERT INTO sanpham (ten, gia, hinhanh, mota) VALUES (?, ?, ?, ?)", sample_data)
        conn.commit()

    conn.close()


# ------------------ Khởi chạy ------------------

if __name__ == '__main__':
    create_sample_data()  # Gọi hàm tạo dữ liệu mẫu trước khi chạy ứng dụng
    app.run(debug=True)
