package com.vtb.dhbc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vtb.dhbc.ClassDL.CaDao;
import com.vtb.dhbc.ClassDL.CauHoi;
import com.vtb.dhbc.ClassDL.SanPham;
import com.vtb.dhbc.ClassDL.ThongTinNguoiChoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSDL {
    DataBase db;
    public CSDL(Context applicationContext) {
        db = new DataBase(applicationContext, "DHBC.sql", null, 1);
    }
    public void recreateDatabase() {
        db.deleteAllTables();
        TaoCSDL();
        insertNewAvt();
        insertNewData();
        insertNewKhung();
        TaoCSDL_gameshow_round2();
        TaoNhanVat("Khach");
    }

    public void ChoiLai(Context context){
        db.QueryData("DROP TABLE IF EXISTS CauHoi" );
        TaoCSDL();
        Toast.makeText(context, "Bạn đã chọn chơi lại từ đầu", Toast.LENGTH_SHORT).show();
    }
    public void TaoCSDL() {
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
            db.QueryData("INSERT INTO avt  VALUES (null,'avt3',10,0)");
        }
        Cursor cursor3 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='khung'");
        if (cursor3 == null || cursor3.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS khung (id INTEGER PRIMARY KEY AUTOINCREMENT, hinhAnh INTEGER,price INTEGER,tinhtrang INTEGER)");
            db.QueryData("INSERT INTO khung  VALUES (null,'khung1',0,1)");
            db.QueryData("INSERT INTO khung  VALUES (null,'khung2',5,0)");
        }
    }


    public void TaoCSDL_gameshow_round2(){
        Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='CaDaoTucNgu'");
        if (cursor1 == null || cursor1.getCount() <= 0) {
            db.QueryData("CREATE TABLE IF NOT EXISTS CaDaoTucNgu (id INTEGER PRIMARY KEY AUTOINCREMENT, HinhAnh TEXT, DapAn NVARCHAR(100))");

            String[] hinhAnhList = {"an_khong_noi_co","anh_em_nhu_the_chan_tay","ba_mat_mot_loi","binh_chan_nhu_vai",
                                    "bit_mat_bat_de","boi_beo_ra_bo","but_sa_ga_chet","cai_kho_lo_cai_khon","cam_can_nay_muc",
                                    "cho_treo_meo_day","coc_mo_co_xoi","com_ao_gao_tien","con_sau_lam_rau_noi_canh",
                                    "du_long_du_canh","dung_nui_nay_trong_nui_no","gai_muoi_bay_be_gay_sung_trau",
                                    "hai_ban_tay_moi_vo_thanh_tieng","hoa_thom_danh_ca_cum","ke_cap_gap_ba_gia",
                                    "lay_trung_choi_da","meo_ma_ga_dong","mot_nu_cuoi_bang_muoi_thang_thuoc_bo",
                                    "mot_tien_ga_ba_tien_thoc","nhay_bao_bo","nhay_lo_co","nuoi_ong_tay_ao_nuoi_cao_trong_nha",
                                    "phu_quy_sinh_le_nghia","ruou_vao_loi_ra","rut_day_dong_rung","thoc_gay_banh_xe",
                                    "thung_rong_keu_to","tien_tram_hau_tau","to_gan_lon_mat","tranh_vo_dua_gap_vo_dua",
                                    "troi_nong_chong_khat_troi_mat_chong_doi","trong_danh_xuoi_ken_thoi_nguoc",
                                    "vat_dau_ca_va_dau_tom","ve_duong_cho_huou_chay",};

            // Thêm các câu trả lời khác tương tự ở đây
            String[] dapAnList = {"ăn không nói có","anh em như thể chân tay","ba mặt một lời","bình chân như vại",
                                "bịt mắt bắt dê","bới bèo ra bọ","bút sa gà chết","cái khó ló cái khôn","cầm cân nảy mực",
                                "chó treo mèo đậy","cốc mò cò xơi","cơm áo gạo tiền","con sâu làm rầu nồi canh",
                                "đủ lông đủ cánh","đứng núi này trông núi nọ","gái mười bảy bẻ gãy sừng trâu",
                                "hai bàn tay mới vỗ thành tiếng","hoa thơm đánh cả cụm","kẻ cắp gặp bà già",
                                "lấy trứng chọi đá","mèo mả gà đồng","một nụ cười bằng mười thang thuốc bổ",
                                "một tiền gà ba tiền thóc","nhảy bao bố","nhảy lò cò","nuôi ong tay áo nuôi cáo trong nhà",
                                "phú quý sinh lễ nghĩa","rượu vào lời ra","rút dây động rừng","thọc gậy bánh xe",
                                "thùng rỗng kêu to","tiền trảm hậu tấu","to gan lớn mật","tránh vỏ dưa gặp vỏ dừa",
                                "trời nóng chóng khát trời mát chóng đói","trống đánh xuôi, kèn thổi ngược",
                                "vặt đầu cá vá đầu tôm","vẽ đường cho hươu chạy"};
            // Kiểm tra xem số lượng hình ảnh và câu trả lời có khớp nhau không
            if (hinhAnhList.length != dapAnList.length) {
                // Nếu không khớp, bạn có thể xử lý tùy thuộc vào yêu cầu cụ thể của ứng dụng, ví dụ: thông báo cho người dùng.
                // Ví dụ: Toast.makeText(context, "Số lượng hình ảnh và câu trả lời không khớp nhau.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Duyệt qua từng phần tử trong mảng hinhAnhList và dapAnList
            for (int i = 0; i < hinhAnhList.length; i++) {
                // Kiểm tra xem dữ liệu đã tồn tại trong cơ sở dữ liệu chưa
                Cursor cursor = db.GetData("SELECT * FROM CaDaoTucNgu WHERE HinhAnh = '" + hinhAnhList[i] + "' AND DapAn = '" + dapAnList[i] + "'");
                if (cursor == null || cursor.getCount() <= 0) {
                    // Nếu không tìm thấy dữ liệu tương ứng, thực hiện câu lệnh insert dữ liệu mới vào bảng
                    db.QueryData("INSERT INTO CaDaoTucNgu (HinhAnh, DapAn) VALUES ('" + hinhAnhList[i] + "', '" + dapAnList[i] + "')");
                } else {
                    // Nếu dữ liệu đã tồn tại, bạn có thể thực hiện các hành động phù hợp, ví dụ: thông báo cho người dùng.
                    // Ví dụ: Toast.makeText(context, "Dữ liệu đã tồn tại trong cơ sở dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public List<CaDao> getCauHoiRound2(int max) {
        List<CaDao> cauHoiList = new ArrayList<>();
        Cursor dataCV = db.GetData("SELECT * FROM CaDaoTucNgu ORDER BY RANDOM() LIMIT "+max);

        if (dataCV != null && dataCV.moveToFirst()) {
            do {
                int id = dataCV.getInt(0);
                String hinhAnh = dataCV.getString(1);
                String dapAn = dataCV.getString(2);
                CaDao cauHoi = new CaDao(id, hinhAnh, dapAn);
                cauHoiList.add(cauHoi);
            } while (dataCV.moveToNext());
        } else {
            for (int i = 0; i < 10; i++) {
                int id = -1;
                String hinhAnh = "hinh123";
                String dapAn = "dapan";
                CaDao cauHoi = new CaDao(id, hinhAnh, dapAn);
                cauHoiList.add(cauHoi);
            }
        }

        if (dataCV != null) {
            dataCV.close();
        }

        return cauHoiList;
    }
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
        // Khởi tạo mảng hình ảnh và giá
        String[] hinhAnhList = new String[15];
        int[] priceList = new int[15];

        // Tạo dữ liệu cho mảng hình ảnh và giá
        for (int i = 0; i < 15; i++) {
            hinhAnhList[i] = "avt" + (i + 1); // Tạo tên hình ảnh theo mẫu "avt1", "avt2",...
            priceList[i] = i * 5; // Giá tăng lên 5 sau mỗi lần
        }

        // Kiểm tra xem số lượng hình ảnh và giá có khớp nhau không
        if (hinhAnhList.length != priceList.length) {
            // Xử lý tùy thuộc vào yêu cầu cụ thể của ứng dụng, ví dụ: thông báo cho người dùng.
            return;
        }

        // Duyệt qua từng phần tử trong mảng hinhAnhList và priceList
        for (int i = 0; i < hinhAnhList.length; i++) {
            // Kiểm tra xem dữ liệu đã tồn tại trong cơ sở dữ liệu chưa
            Cursor cursor = db.GetData("SELECT * FROM avt WHERE hinhAnh = '" + hinhAnhList[i] + "' AND price = " + priceList[i]);
            if (cursor == null || cursor.getCount() <= 0) {
                // Nếu không tìm thấy dữ liệu tương ứng, thực hiện câu lệnh insert dữ liệu mới vào bảng
                db.QueryData("INSERT INTO avt (hinhAnh, price, tinhtrang) VALUES ('" + hinhAnhList[i] + "', " + priceList[i] + ", 0)");
            } else {
                // Nếu dữ liệu đã tồn tại, bạn có thể thực hiện các hành động phù hợp, ví dụ: thông báo cho người dùng.
            }
        }
    }
    public void insertNewKhung(){
        // Dữ liệu bạn muốn thêm vào bảng khung
        // Thêm các hình ảnh khung khác tương tự ở đây
        String[] hinhAnhList = new String[15];
        int[] priceList = new int[15];

        // Khởi tạo dữ liệu cho mảng hình ảnh và giá cả
        for (int i = 0; i < 15; i++) {
            hinhAnhList[i] = "khung" + (i + 1);
            priceList[i] = i * 5;
        }

        // Duyệt qua từng phần tử trong mảng hinhAnhList và priceList
        for (int i = 0; i < hinhAnhList.length; i++) {
            // Kiểm tra xem dữ liệu đã tồn tại trong cơ sở dữ liệu chưa
            Cursor cursor = db.GetData("SELECT * FROM khung WHERE hinhAnh = '" + hinhAnhList[i] + "' AND price = " + priceList[i]);
            if (cursor == null || cursor.getCount() <= 0) {
                // Nếu không tìm thấy dữ liệu tương ứng, thực hiện câu lệnh insert dữ liệu mới vào bảng
                db.QueryData("INSERT INTO khung (hinhAnh, price, tinhtrang) VALUES ('" + hinhAnhList[i] + "', " + priceList[i] + ", 0)");
            } else {
                // Nếu dữ liệu đã tồn tại, bạn có thể thực hiện các hành động phù hợp, ví dụ: thông báo cho người dùng.
            }
        }
    }
    public  boolean KiemTraNhanVat(Context context){
        Cursor cursor1 = db.GetData("SELECT name FROM sqlite_master WHERE type='table' AND name='ThongTinNguoiChoi1'");
        if (cursor1 == null || cursor1.getCount() <= 0) {
            return true;
        }
        return false;
    }
    public void TaoNhanVat(String name){
        db.QueryData("CREATE TABLE IF NOT EXISTS ThongTinNguoiChoi1 (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, " +
                "ruby INTEGER," +
                "level INTEGER, " +
                "avt_ID INTEGER," +
                "khung_id INTEGER," +
                "damua_khung TEXT," +
                "damua_avt TEXT)"); // Close the parenthesis here
        db.QueryData("INSERT INTO ThongTinNguoiChoi1 (name, ruby, level, avt_ID, khung_id, damua_khung, damua_avt) VALUES ('" + name + "', 24, 0, 1, 1, '1', '1')");
    }

    public void SuaThongTinNhanVat(String name, int avt_ID,int khung_id){
        db.QueryData("Update ThongTinNguoiChoi1 set name='"+name+"', avt_ID="+avt_ID+", khung_id="+khung_id);
        updatePlayerInfoOnFirebase();
    }
    public ThongTinNguoiChoi HienThongTinNhanVat(){
        Cursor dataCV=db.GetData("SELECT * FROM ThongTinNguoiChoi1 ");
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
    public ThongTinNguoiChoi HienThongTinNhanVat2(){
        Cursor dataCV=db.GetData("SELECT * FROM ThongTinNguoiChoi1 ");
        ThongTinNguoiChoi thongTinNguoiChoi=null;
        if (dataCV != null && dataCV.moveToFirst()) {
            int id = dataCV.getInt(0);
            String name=dataCV.getString(1);
            int ruby = dataCV.getInt(2);
            int level = dataCV.getInt(3);
            int avt_id = dataCV.getInt(4);
            int khung_id = dataCV.getInt(5);
            String khung_damua=dataCV.getString(6);
            String avt_damua=dataCV.getString(7);
            thongTinNguoiChoi= new ThongTinNguoiChoi(name,ruby,level,avt_id,khung_id,khung_damua,avt_damua);
        }
        else {
            int id = -1;
            String name="name";
            int ruby = 0;
            int level = 0;
            int avt_id = -1;
            int khung_id = -1;
            String khung_damua="1";
            String avt_damua="1";
            thongTinNguoiChoi= new ThongTinNguoiChoi(name,ruby,level,avt_id,khung_id,khung_damua,avt_damua);

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
    public List<CauHoi> getCauHoiRound1(int max) {
        List<CauHoi> cauHoiList = new ArrayList<>();
        Cursor dataCV = db.GetData("SELECT * FROM CauHoi  ORDER BY RANDOM() LIMIT "+max);

        if (dataCV != null && dataCV.moveToFirst()) {
            do {
                int id = dataCV.getInt(0);
                String hinhAnh = dataCV.getString(1);
                String dapAn = dataCV.getString(2);
                int tinhTrang = dataCV.getInt(3);
                CauHoi cauHoi = new CauHoi(id, hinhAnh, dapAn, tinhTrang);
                cauHoiList.add(cauHoi);
            } while (dataCV.moveToNext());
        } else {
            for (int i = 0; i < 10; i++) {
                int id = -1;
                String hinhAnh = "hinh123";
                String dapAn = "dapan";
                int tinhTrang = 0;
                CauHoi cauHoi = new CauHoi(id, hinhAnh, dapAn, tinhTrang);
                cauHoiList.add(cauHoi);
            }
        }

        if (dataCV != null) {
            dataCV.close();
        }

        return cauHoiList;
    }

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
    public void Update(int id){
        db.QueryData("update CauHoi set TinhTrang=1 where id="+id);
    }
    public void UpdateRuby(int slg){

//        db.QueryData("update Rubys set SoLuong= SoLuong+"+slg);
        db.QueryData("update ThongTinNguoiChoi1 set ruby=ruby +"+slg);
        updatePlayerInfoOnFirebase();
    }
    public void UpdateThongTin( int level,int levelMax){
        if(level>levelMax) {
            db.QueryData("update ThongTinNguoiChoi1 set level="+level);
            updatePlayerInfoOnFirebase();
        }
    }
    //update mua sản phẩm
    public void UpdateSanPham(String table, int id) {
        // Append id to damua_khung or damua_avt using string concatenation in SQLite
        if(id != 1) {
            if (!table.equalsIgnoreCase("khung")) {
                db.QueryData("UPDATE ThongTinNguoiChoi1 SET damua_khung = damua_khung || '," + id + "'");
            } else {
                db.QueryData("UPDATE ThongTinNguoiChoi1 SET damua_avt = damua_avt || '," + id + "'");
            }
        }

        // Update the 'tinhtrang' field in the specified table
        db.QueryData("UPDATE " + table + " SET tinhtrang = 1 WHERE id = " + id);
        updatePlayerInfoOnFirebase();
    }
    public void UpdateSanPhamLai(String table, int id) {
        db.QueryData("UPDATE " + table + " SET tinhtrang = 1 WHERE id = " + id);
    }



    //get câu hỏi chơi online
    public CauHoi getCauHoi(int id){
        Cursor dataCV=db.GetData("SELECT * FROM CauHoi WHERE id="+id);
        CauHoi cauHoi=null;
        if (dataCV != null && dataCV.moveToFirst()) {
            int id1 = dataCV.getInt(0);
            String hinhAnh = dataCV.getString(1);
            String dapAn = dataCV.getString(2);
            int tinhTrang = dataCV.getInt(3);
            cauHoi= new CauHoi(id1, hinhAnh, dapAn, tinhTrang);
//            Toast.makeText(context, "id: " + dataCV.getInt(0) + "dapan: " + dataCV.getString(2), Toast.LENGTH_SHORT).show();
        }
        else {
            int id1 = -1;
            String hinhAnh = "hinh123";
            String dapAn = "dapan";
            int tinhTrang = 0;
            cauHoi= new CauHoi(id1, hinhAnh, dapAn, tinhTrang);
        }
        return cauHoi;
    }
        public CaDao getCaDao(int id) {
        CaDao cauHoi = null;
        Cursor dataCV = db.GetData("SELECT * FROM CaDaoTucNgu WHERE id=" + id);

        if (dataCV != null && dataCV.moveToFirst()) {
            int id1 = dataCV.getInt(0);
            String hinhAnh = dataCV.getString(1);
            String dapAn = dataCV.getString(2);
            cauHoi = new CaDao(id1, hinhAnh, dapAn);
        } else {
            int id1 = -1;
            String hinhAnh = "hinh123";
            String dapAn = "dapan";
            cauHoi = new CaDao(id1, hinhAnh, dapAn);
        }

        if (dataCV != null) {
            dataCV.close();
        }

        return cauHoi;
    }
    private void updatePlayerInfoOnFirebase() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        //Khởi tạo đối tượng FirebaseDatabase để thực hiện lưu trữ thông tin người dùng
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            ThongTinNguoiChoi thongTinNguoiChoi = HienThongTinNhanVat2();
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", user.getUid());
            map.put("name", thongTinNguoiChoi.getName());
            map.put("ruby", thongTinNguoiChoi.getRuby());
            map.put("level", thongTinNguoiChoi.getLevel());
            map.put("avt_id", thongTinNguoiChoi.getAvt_id());
            map.put("khung_id", thongTinNguoiChoi.getKhung_id());
            map.put("damua_khung", thongTinNguoiChoi.getDamua_khung());
            map.put("damua_avt", thongTinNguoiChoi.getDamua_avt());

            // Cập nhật thông tin người chơi lên Firebase
            firebaseDatabase.getReference().child("users").child(user.getUid()).updateChildren(map);
        }
    }
    public void getPlayerInfoFromFirebase() {  //
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(user.getUid());

            // Lấy thông tin người chơi từ Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Kiểm tra xem dataSnapshot có tồn tại không
                    if (dataSnapshot.exists()) {
                        ThongTinNguoiChoi thongTinNguoiChoi = dataSnapshot.getValue(ThongTinNguoiChoi.class);
                        if (thongTinNguoiChoi != null) {
                            LoginTroLai(thongTinNguoiChoi,HienThongTinNhanVat2());
                        }
                        System.out.println("Id khung:"+thongTinNguoiChoi.getDamua_khung());
                        System.out.println("Id avt:"+thongTinNguoiChoi.getDamua_avt());
                    } else {
                        System.out.println("User does not exist.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getMessage());
                }
            });
        } else {
            System.out.println("User not logged in.");
        }

    }
    public void LoginTroLai(ThongTinNguoiChoi thongTinNguoiChoi, ThongTinNguoiChoi old){
        SuaThongTinNhanVat(thongTinNguoiChoi.getName(),thongTinNguoiChoi.getAvt_id(),thongTinNguoiChoi.getKhung_id());
        UpdateRuby(thongTinNguoiChoi.getRuby()-old.getRuby());
        System.out.println("level: "+thongTinNguoiChoi.getLevel());
        if(thongTinNguoiChoi.getLevel()>0){
            for(int i=1;i<=thongTinNguoiChoi.getLevel();i++){
                Update(i);
            }
        }
        UpdateThongTin(thongTinNguoiChoi.getLevel(),0);
        String[] listAvt = thongTinNguoiChoi.getDamua_avt().split(",");
        String[] listKhung = thongTinNguoiChoi.getDamua_khung().split(",");
        for (int i = 0; i < listAvt.length; i++) {
            UpdateSanPham("avt",Integer.parseInt(listAvt[i]));
        }
        for (int i = 0; i < listKhung.length; i++) {
            UpdateSanPham("khung",Integer.parseInt(listKhung[i]));
        }
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
    // Phương thức xóa tất cả các bảng
    public void deleteAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Truy vấn danh sách các bảng
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên bảng
                String tableName = cursor.getString(0);
                // Xóa bảng
                db.execSQL("DROP TABLE IF EXISTS " + tableName);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}