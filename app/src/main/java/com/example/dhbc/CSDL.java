package com.example.dhbc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='Ruby'");
        if (cursor1 == null || cursor1.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS Ruby (id INTEGER PRIMARY KEY AUTOINCREMENT,SoLuong Integer)");
            db.QueryData("INSERT INTO Ruby  VALUES (null,24)");
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
        }
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
    public int HienRuby(Context context){
        Cursor dataCV=db.GetData("SELECT * FROM Ruby  LIMIT 1");
        int soluong=0;
        if (dataCV != null && dataCV.moveToFirst()) {
             soluong = dataCV.getInt(1);
//            Toast.makeText(context, "id: " + dataCV.getInt(1) , Toast.LENGTH_SHORT).show();

        }
        return soluong;
    }
    public void Update(Context context, int id){
        db.QueryData("update CauHoi set TinhTrang=1 where id="+id);
    }
    public void UpdateRuby(Context context, int slg){
        db.QueryData("update Ruby set SoLuong= SoLuong+"+slg);
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