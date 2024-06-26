package com.example.had_store.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "Hqwu";
    static  final  int dbVersion=31;
    public DbHelper(Context context){ super(context,dbName,null,dbVersion);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table NhanVien (" +
                "maNv Text primary key ," +
                "tenNv TEXT not null," +
                "matKhauNv TEXT not null," +
                "soNv integer not null," +
                "emailNv TEXT not null," +
                "anhNv text not null)");
        sqLiteDatabase.execSQL("insert into NhanVien values " +
                "('NV01','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUYjtgg39whpZmugBXYoTbp296AqxVA3yzuw&usqp=CAU')," +
                "('NV02','Cao Hoàng','123',0366691234,'dungntph@fpt.edu.vn','https://afamilycdn.com/150157425591193600/2022/8/23/299980243-2045230712333844-1983650452575958652-n-3730-1661222602446-16612226025621952261667.jpeg')," +
                "('NV03','Tuấn Anh ','123',0366691234,'dungntph@fpt.edu.vn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWjoyF0mcOPxktb_6WpDO84CsHyXT0a_0HQg&usqp=CAU')," +
                "('NV04','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCTllYr56xg6PDvJCwRevKDgz1xBxYVAX_Zw&usqp=CAU')," +
                "('NV05','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSDw7Aw1I-FC4KkJzqHiON1gSeKVb54XzwRw&usqp=CAU')" +
                "");

        sqLiteDatabase.execSQL("create table KhachHang (" +
                "maKh text primary key," +
                "tenKh TEXT not null," +
                "matKhauKh TEXT not null," +
                "soKh integer not null," +
                "emailKh TEXT not null," +
                "diaChiKh TEXT not null," +
                "anhKh text not null)");
        sqLiteDatabase.execSQL("insert into KhachHang values " +
                "('KH01','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','Hn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUYjtgg39whpZmugBXYoTbp296AqxVA3yzuw&usqp=CAU')," +
                "('KH02','Cao Hoàng','123',0366691234,'dungntph@fpt.edu.vn','Hn','https://afamilycdn.com/150157425591193600/2022/8/23/299980243-2045230712333844-1983650452575958652-n-3730-1661222602446-16612226025621952261667.jpeg')," +
                "('KH03','Tuấn Anh ','123',0366691234,'dungntph@fpt.edu.vn','Hn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWjoyF0mcOPxktb_6WpDO84CsHyXT0a_0HQg&usqp=CAU')," +
                "('KH04','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','Hn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCTllYr56xg6PDvJCwRevKDgz1xBxYVAX_Zw&usqp=CAU')," +
                "('KH05','Nguyễn Tuấn ','123',0366691234,'dungntph@fpt.edu.vn','Hn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSDw7Aw1I-FC4KkJzqHiON1gSeKVb54XzwRw&usqp=CAU')" +
                "");


        sqLiteDatabase.execSQL("create table HangSanPham (" +
                "maHang integer primary key autoincrement," +
                "tenHang TEXT not null," +
                "diaChiHang TEXT not null," +
                "anhHang text not null)");
        sqLiteDatabase.execSQL("insert into HangSanPham values " +
                "(1,'APPLE','Nam Tu Liem - Ha Noi','https://yt3.googleusercontent.com/WoDkWmAjQ5Dbw-ccjqFku8ThK2UYcqaOqq25PBE9eGb_S-vsqxiKU2kL2kZJVz_BcAMv3WUWsA=s900-c-k-c0x00ffffff-no-rj')," +
                "(2,'SAMSUNG','Nho Quan - Ninh Binh','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1vv60lRXEgKNg25DFs2YymtVvZ1CC-ul4wCSBM_EoEa1qw3AbqL8o64hOZd7lpObWeJk&usqp=CAU')," +
                "(3,'XIAOMI','Nam Tu Liem - Ha Noi','https://www.movilzona.es/app/uploads-movilzona.es/2020/07/xiaomi-logo-y-fondo-degradado.jpg')," +
                "(4,'VIVO','Nam Tu Liem - Ha Noi','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfMW4kaQoFEXOAJO4O8BfZTbhTB0PKNSL0tw&usqp=CAU')," +
                "(5,'OPPO','Nam Tu Liem - Ha Noi','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9z9J4sFsWZP4Q7gOHtpZjzX_rN4svdLngLA&usqp=CAU')");

        sqLiteDatabase.execSQL("create table SanPham (" +
                "maSp integer primary key autoincrement," +
                "tenSp text not null, " +
                "giaSp integer not null," +
                "soLuongSp integer not null," +
                "trangThaiSp text not null," +//có sale hay không
                "maHang integer references HangSanPham(maHang), " +
                "anhSp text not null ," +
                "mota Text not null)");
        sqLiteDatabase.execSQL("insert into SanPham values " +
                "(1,'IPHONE 15 PRO MAX',35000000,123,'sale',1,'https://cdn.hoanghamobile.com/i/preview/Uploads/2023/09/13/iphone-15-pro-max-white-titanium-pure-back-iphone-15-pro-max-white-titanium-pure-front-2up-screen-usen.png','qưewqewqe')," +
                "(2,'VIVO X NOTE',20000000,12,'Khong',4,'https://lequanmobile.com/wp-content/uploads/2023/04/VIVO-X-NOTE-BLACK-.png','qưewqew')," +
                "(3,'IPHONE 13 PRO MAX',20000000,23,'sale',1,'https://cdn.viettelstore.vn/Images/Product/ProductImage/1896556097.jpeg','qưewqewqe')," +
                "(4,'IPHONE 15 PRO ',30000000,79,'khong',1,'https://cdn.viettelstore.vn/Images/Product/ProductImage/1896556097.jpeg','qưewqewqe')," +
                "(5,'SAMSUNG GALAXY S23',15000000,68,'sale',2,'https://images.samsung.com/is/image/samsung/assets/vn/2201/preorder/1_image_carousel/2_group_kv2/S21FE_Carousel_GroupKV2_MO.jpg','qưewqewqewqe')");

        sqLiteDatabase.execSQL("create table GioHang (" +
                "maGio integer primary key autoincrement," +
                "soLuong integer not null," +
                "diaChiGio text not null," +
                "maSp integer references SanPham(maSp), " +
                "maKh text references KhachHang(maKh) )");
        sqLiteDatabase.execSQL("insert into GioHang values " +
                "(1,4,'Ha Noi',1,'KH01')," +
                "(2,5,'Ninh Binh',2,'KH01')," +
                "(3,2,'Ha Noi',1,'KH01')," +
                "(4,1,'Ha Noi',1,'KH01')," +
                "(5,5,'Ninh Binh',2,'KH01')");

        sqLiteDatabase.execSQL("create table DonHang (" +
                "maDon integer primary key autoincrement," +
                "ngayLap date not null," +
                "trangThaiDon text not null," +
                "maGio integer references GioHang(maGio)," +
                "maKh text references KhachHang(maKh) )");
        sqLiteDatabase.execSQL("insert into DonHang values " +
                "(1,'20/09/2023','Đang vận chuyển',1,'KH01')," +
                "(2,'20/09/2023','Đang vận chuyển',2,'KH01')," +
                "(3,'20/09/2023','Đang vận chuyển',5,'KH01')," +
                "(4,'20/09/2023','Đang vận chuyển',3,'KH01')," +
                "(5,'20/09/2024','Đang vận chuyển',4,'KH01')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists SanPham");
        sqLiteDatabase.execSQL("drop table if exists HangSanPham");
        sqLiteDatabase.execSQL("drop table if exists DonHang");
        sqLiteDatabase.execSQL("drop table if exists GioHang");
        sqLiteDatabase.execSQL("drop table if exists NhanVien");
        sqLiteDatabase.execSQL("drop table if exists KhachHang");
        onCreate(sqLiteDatabase);
    }
}
