package com.example.dhbc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CSDL {
    DataBase db;
    public CSDL(Context applicationContext) {
        db = new DataBase(applicationContext, "DHBC.sql", null, 1);
    }
    public void ChoiLai(Context context){
        db.QueryData("DROP TABLE IF EXISTS CauHoi" );
        TaoCSDL(context);
        Toast.makeText(context, "Bạn đã chọn chơi lại từ đầu", Toast.LENGTH_SHORT).show();
    }
    public void TaoCSDL(Context context) {
//        db.QueryData("DROP TABLE IF EXISTS Ruby" );
        Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='Rubys'");
        if (cursor1 == null || cursor1.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS Rubys (id INTEGER PRIMARY KEY AUTOINCREMENT,SoLuong Integer)");
            db.QueryData("INSERT INTO Rubys  VALUES (null,24)");
        }


        Cursor cursor = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='CauHoi'");
        if (cursor == null || cursor.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS CauHoi (id INTEGER PRIMARY KEY AUTOINCREMENT, HinhAnh TEXT, DapAn NVARCHAR(100), TinhTrang INTEGER DEFAULT 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baocao', 'báo cáo', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'aomua', 'áo mưa', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'canthiep', 'can thiệp', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'cattuong', 'cát tường', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'chieutre', 'chiếu tre', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'danhlua', 'đánh lừa', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'danong', 'đàn ông', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'giandiep', 'gián điệp', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'giangmai', 'giang mai', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hoidong', 'hội đồng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hongtam', 'hồng tâm', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'khoailang', 'khoai lang', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'kiemchuyen', 'kiếm chuyện', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'lancan', 'lan can', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'masat', 'ma sát', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nambancau', 'nam bán cầu', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'oto', 'ô tô', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'quyhang', 'quy hàng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'songsong', 'song song', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'thattinh', 'thất tình', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'thothe', 'thỏ thẻ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tichphan', 'tích phân', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tohoai', 'tô hoài', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'totien', 'tổ tiên', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tranhthu', 'tranh thủ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'vuaphaluoi', 'vua phá lưới', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'vuonbachthu', 'vườn bách thú', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xakep', 'xà kép', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xaphong', 'xà phòng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xauho', 'xấu hổ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xedapdien', 'xe đạp điện', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bachcau', 'bạch cầu', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baoquat', 'bao quát', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'batcahaitay', 'bắt cá hai tay', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hungthu', 'hung thủ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'kinhdo', 'kinh độ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'matuy', 'ma túy', 0)");


            db.QueryData("INSERT INTO CauHoi  VALUES (null,'anboobui', 'ăn bờ ở bụi', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'ando', 'ấn độ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bachhoa', 'bách hóa', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'benhbinh', 'bệnh binh', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bodaonha', 'bồ đào nha', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'caucanh', 'cầu cạnh', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'cauchi', 'cầu chì', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'chunho', 'chữ nho', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'daoaothaca', 'đào ao thả cá', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'giatang', 'gia tăng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hama', 'hà mã', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'kyvong', 'kỳ vọng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nghean', 'nghệ an', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nhatban', 'nhật bản', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'quantrong', 'quan trọng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'thachthuc', 'thách thức', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tinhnghich', 'tinh nghịch', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'trongvang', 'trống vắng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'trotrong', 'trò trống', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xahoa', 'xa hoa', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'yeucau', 'yêu cầu', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nguao', 'ngựa ô', 0)");

            db.QueryData("INSERT INTO CauHoi  VALUES (null,'aimo', 'ái mộ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bactinh', 'bạc tình', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'badong', 'ba động', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baophu', 'bao phủ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baothuc', 'báo thức', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baotu', 'bao tử', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'batron', 'ba trợn', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'baxa', 'bà xã', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bongda', 'bóng đá', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'bioi', 'bỉ ổi', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'butsagachet', 'bút sa gà chết', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'calonnuotcabe', 'cá lớn nuốt cá bé', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'caulong', 'cầu lông', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'caumay', 'cầu mây', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'chidiem', 'chỉ điểm', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'chuotquang', 'chuột quang', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'cobap', 'cơ bắp', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'daituong', 'đại tướng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'danco', 'đàn cò', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'dautu', 'đầu tư', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'dongcamcongkho', 'đồng cam cộng khổ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'eap', 'e ấp', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hailong', 'hài lòng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hochanh', 'học hành', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'hungthuu', 'hứng thú', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'khotam', 'khổ tâm', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'laban', 'la bàn', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'langthang', 'lang thang', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'luclac', 'lục lạc', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'lyhon', 'ly hôn', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'macarong', 'ma cà rồng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'myle', 'mỹ lệ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nemdagiautay', 'ném đá giấu tay', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'ngangu', 'ngã ngũ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'ngucoc', 'ngũ cốc', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nhaccu', 'nhạc cụ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'nhahat', 'nhà hát', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'noigian', 'nội gián', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'noithat', 'nội thất', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'thoo', 'thờ ơ', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tamtoi', 'tăm tối', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'tinhtruong', 'tình trường', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xehoa', 'xe hoa', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xemtuong', 'xem tướng', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xichlo', 'xích lô', 0)");
            db.QueryData("INSERT INTO CauHoi  VALUES (null,'xuongrong', 'xương rồng', 0)");
        }
        Cursor cursor2 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='avt'");
        if (cursor2 == null || cursor2.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS avt (id INTEGER PRIMARY KEY AUTOINCREMENT, hinhAnh INTEGER,price INTEGER,tinhtrang INTEGER)");
            db.QueryData("INSERT INTO avt  VALUES (null,'avt1',0,1)");
            db.QueryData("INSERT INTO avt  VALUES (null,'avt2',5,0)");
        }
        Cursor cursor3 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='khung'");
        if (cursor3 == null || cursor3.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS khung (id INTEGER PRIMARY KEY AUTOINCREMENT, hinhAnh INTEGER,price INTEGER,tinhtrang INTEGER)");
            db.QueryData("INSERT INTO khung  VALUES (null,'khung1',0,1)");
            db.QueryData("INSERT INTO khung  VALUES (null,'khung2',5,0)");
        }
    }
//public void TaoCSDL(Context context) {
//    // Kiểm tra và tạo bảng Rubys
//    Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='Rubys'");
//    if (cursor1 == null || cursor1.getCount() <= 0) {
//        db.QueryData("CREATE TABLE IF NOT EXISTS Rubys (id INTEGER PRIMARY KEY AUTOINCREMENT, SoLuong Integer)");
//        db.QueryData("INSERT INTO Rubys  VALUES (null,9999)");
//    }
//
//    // Kiểm tra và tạo bảng CauHoi
//    Cursor cursor = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='CauHoi'");
//    if (cursor == null || cursor.getCount() <= 0) {
//        db.QueryData("CREATE TABLE IF NOT EXISTS CauHoi (id INTEGER PRIMARY KEY AUTOINCREMENT, HinhAnh TEXT, DapAn NVARCHAR(100), TinhTrang INTEGER DEFAULT 0)");
//
//        // Dữ liệu bạn muốn thêm vào bảng
//        String[] newData = {
//                "('baocao', 'báo cáo', 0)",
//                "('aomua', 'áo mưa', 0)",
//                // Thêm dữ liệu mới ở đây nếu cần
//        };
//
//        // Thêm dữ liệu mới vào bảng
//        for (String item : newData) {
//            db.QueryData("INSERT INTO CauHoi (HinhAnh, DapAn, TinhTrang) VALUES " + item);
//        }
//    }
//}
    public void insertNewData() {
        // Dữ liệu bạn muốn thêm vào bảng
        // Thêm các hình ảnh khác tương tự ở đây
        String[] hinhAnhList = {"xemtuong","xichlo","xuongrong"};

        // Thêm các câu trả lời khác tương tự ở đây
        String[] dapAnList = {"xem tướng","xích lô","xương rồng"};

        // Kiểm tra xem số lượng hình ảnh và câu trả lời có khớp nhau không
        if (hinhAnhList.length != dapAnList.length) {
            // Nếu không khớp, bạn có thể xử lý tùy thuộc vào yêu cầu cụ thể của ứng dụng, ví dụ: thông báo cho người dùng.
            // Ví dụ: Toast.makeText(context, "Số lượng hình ảnh và câu trả lời không khớp nhau.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Duyệt qua từng phần tử trong mảng hinhAnhList và dapAnList
        for (int i = 0; i < hinhAnhList.length; i++) {
            // Kiểm tra xem dữ liệu đã tồn tại trong cơ sở dữ liệu chưa
            Cursor cursor = db.GetData("SELECT * FROM CauHoi WHERE HinhAnh = '" + hinhAnhList[i] + "' AND DapAn = '" + dapAnList[i] + "'");
            if (cursor == null || cursor.getCount() <= 0) {
                // Nếu không tìm thấy dữ liệu tương ứng, thực hiện câu lệnh insert dữ liệu mới vào bảng
                db.QueryData("INSERT INTO CauHoi (HinhAnh, DapAn, TinhTrang) VALUES ('" + hinhAnhList[i] + "', '" + dapAnList[i] + "', 0)");
            } else {
                // Nếu dữ liệu đã tồn tại, bạn có thể thực hiện các hành động phù hợp, ví dụ: thông báo cho người dùng.
                // Ví dụ: Toast.makeText(context, "Dữ liệu đã tồn tại trong cơ sở dữ liệu.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void insertNewAvt(){

    }
    public void insertNewKhung(){

    }



    public  boolean KiemTraNhanVat(Context context){
        Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='ThongTinNguoiChoi'");
        if (cursor1 == null || cursor1.getCount() <= 0) {
//            db.QueryData("CREATE TABLE IF NOT EXISTS Rubys (id INTEGER PRIMARY KEY AUTOINCREMENT,SoLuong Integer)");
//            db.QueryData("INSERT INTO Rubys  VALUES (null,9999)");
            return true;
        }
        return false;
    }
    public void TaoNhanVat(String name){
        db.QueryData("CREATE TABLE IF NOT EXISTS ThongTinNguoiChoi (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name value, " +
                "ruby integer," +
                " level integer, " +
                "avt_ID integer," +
                " khung_id integer  )");
        db.QueryData("INSERT INTO ThongTinNguoiChoi  VALUES (null,'" + name+  "', 24,1,1,1)");
    }
    public void SuaThongTinNhanVat(String name, int avt_ID,int khung_id){
        db.QueryData("Update ThongTinNguoiChoi set name='"+name+"', avt_ID="+avt_ID+", khung_id="+khung_id);
    }
    public ThongTinNguoiChoi HienThongTinNhanVat(){
        Cursor dataCV=db.GetData("SELECT * FROM ThongTinNguoiChoi ");
        ThongTinNguoiChoi thongTinNguoiChoi=null;
        if (dataCV != null && dataCV.moveToFirst()) {
            int id = dataCV.getInt(0);
            String name=dataCV.getString(1);
            int ruby = dataCV.getInt(2);
            int level = dataCV.getInt(3);
            int avt_id = dataCV.getInt(4);
            int khung_id = dataCV.getInt(5);
            thongTinNguoiChoi= new ThongTinNguoiChoi(name,ruby,level,avt_id,khung_id);
        }
        else {
            int id = -1;
            String name="name";
            int ruby = 0;
            int level = 0;
            int avt_id = -1;
            int khung_id = -1;
            thongTinNguoiChoi= new ThongTinNguoiChoi(name,ruby,level,avt_id,khung_id);
        }
        return thongTinNguoiChoi;
    }

    public CauHoi HienCSDL(Context context){
        Cursor dataCV=db.GetData("SELECT * FROM CauHoi WHERE TinhTrang = 0 LIMIT 1");
        CauHoi cauHoi=null;
        if (dataCV != null && dataCV.moveToFirst()) {
            int id = dataCV.getInt(0);
            String hinhAnh = dataCV.getString(1);
            String dapAn = dataCV.getString(2);
            int tinhTrang = dataCV.getInt(3);
            cauHoi= new CauHoi(id, hinhAnh, dapAn, tinhTrang);
//            Toast.makeText(context, "id: " + dataCV.getInt(0) + "dapan: " + dataCV.getString(2), Toast.LENGTH_SHORT).show();
        }
        else {
            int id = -1;
            String hinhAnh = "hinh123";
            String dapAn = "dapan";
            int tinhTrang = 0;
            cauHoi= new CauHoi(id, hinhAnh, dapAn, tinhTrang);
        }
        return cauHoi;
    }
//    public int HienRuby(Context context){
//        Cursor dataCV=db.GetData("SELECT * FROM Ruby  LIMIT 1");
//        int soluong=0;
//        if (dataCV != null && dataCV.moveToFirst()) {
//             soluong = dataCV.getInt(1);
////            Toast.makeText(context, "id: " + dataCV.getInt(1) , Toast.LENGTH_SHORT).show();
//
//        }
//        return soluong;
//    }
//public int HienRuby(Context context) {
//    Cursor dataCV = db.GetData("SELECT * FROM Rubys LIMIT 1");
//    int soluong = 0;
//    if (dataCV != null && dataCV.moveToFirst()) {
//        soluong = dataCV.getInt(1);
//        // Log the retrieved value for debugging
//        Log.d("HienRuby", "SoLuong: " + soluong);
//        // Optionally show a toast message for debugging
//        // Toast.makeText(context, "SoLuong: " + soluong, Toast.LENGTH_SHORT).show();
//    } else {
//        // Log an error message if no data is found
//        Log.e("HienRuby", "No data found in Ruby table");
//    }
//    // Close the cursor to release resources
//    if (dataCV != null) {
//        dataCV.close();
//    }
//    return soluong;
//}
public ArrayList<SanPham> HienDS_AVT(){
    ArrayList<SanPham> danhSachSanPham = new ArrayList<>();
    Cursor dataCV = db.GetData("SELECT * FROM avt");

    while (dataCV.moveToNext()) {
        int id = dataCV.getInt(0);
        String hinhanh = dataCV.getString(1);
        int price=dataCV.getInt(2);
        int tinhtrang = dataCV.getInt(3);
        // Tạo một đối tượng SanPham từ dữ liệu và thêm vào danh sách
        SanPham sanPham = new SanPham(id, hinhanh,price,tinhtrang); // Cần sửa constructor của SanPham để phù hợp
        danhSachSanPham.add(sanPham);
    }

    // Đóng con trỏ sau khi sử dụng để tránh rò rỉ bộ nhớ
    dataCV.close();

    // Trả về danh sách các sản phẩm
    return danhSachSanPham;
}

    public ArrayList<SanPham> HienDS_Khung(){
        ArrayList<SanPham> danhSachSanPham = new ArrayList<>();
        Cursor dataCV = db.GetData("SELECT * FROM khung");

        while (dataCV.moveToNext()) {
            int id = dataCV.getInt(0);
            String hinhanh = dataCV.getString(1);
            int price=dataCV.getInt(2);
            int tinhtrang = dataCV.getInt(3);
            // Tạo một đối tượng SanPham từ dữ liệu và thêm vào danh sách
            SanPham sanPham = new SanPham(id, hinhanh,price,tinhtrang); // Cần sửa constructor của SanPham để phù hợp
            danhSachSanPham.add(sanPham);
        }

        // Đóng con trỏ sau khi sử dụng để tránh rò rỉ bộ nhớ
        dataCV.close();

        // Trả về danh sách các sản phẩm
        return danhSachSanPham;
    }

    //update câu hỏi
    public void Update(Context context, int id){
        db.QueryData("update CauHoi set TinhTrang=1 where id="+id);
    }
    public void UpdateRuby(Context context, int slg){

//        db.QueryData("update Rubys set SoLuong= SoLuong+"+slg);
        db.QueryData("update ThongTinNguoiChoi set ruby=ruby +"+slg);
    }
    public void UpdateThongTin( int level,int levelMax){
        if(level>levelMax)
            db.QueryData("update ThongTinNguoiChoi set level="+level);
    }
    //update mua sản phẩm
    public void UpdateSanPham(String table, int id){
        db.QueryData("Update "+ table +" set tinhtrang = 1 where id="+id);
    }
}
class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //truy vấn không trả kết quả:insert, update,delete
    public void QueryData (String sql){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL(sql);
    }
    //truy vấn có trả kết quả
    public Cursor GetData(String sql){
        SQLiteDatabase db= getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}