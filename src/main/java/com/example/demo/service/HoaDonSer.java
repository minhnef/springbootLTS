package com.example.demo.service;

import com.example.demo.entity.ChiTietHoaDon;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.respon.Request;
import com.example.demo.entity.respon.Respon;
import com.example.demo.repository.IChiTietRepo;
import com.example.demo.repository.IHoaDonRepo;
import com.example.demo.repository.IKhachHangRepo;
import com.example.demo.repository.ISanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonSer {
    @Autowired
    IHoaDonRepo hoaDonRepo;
    @Autowired
    IChiTietRepo chiTietRepo;
    @Autowired
    ISanPhamRepo sanPhamRepo;
    @Autowired
    IKhachHangRepo khachHangRepo;

    public Respon<?> themHoaDon(Request<HoaDon> hoaDon) {
        HoaDon hd = hoaDon.getData();
        hoaDonRepo.save(hoaDon.getData());
        double tongtien = 0;

        Optional<KhachHang> kh = khachHangRepo.findById(hoaDon.getData().getKhachHang().getIdKH());
        if (kh.isEmpty()) {
            khachHangRepo.save(kh.get());
            return new Respon<>(0, "Kiem tra thong tin khach hang co ID: " + hoaDon.getData().getKhachHang().getIdKH(), hoaDon.getData().getKhachHang());
        }
        for (ChiTietHoaDon ct : hoaDon.getData().getChiTietHoaDons()) {
            Optional<SanPham> sp = sanPhamRepo.findById(ct.getSanPham().getIdSP());
            if (sp.isEmpty()) {
                return new Respon<>(0, "Sản phẩm chưa tồn tại. Vui lòng kiểm tra lại!", null);
            }
            tongtien = tongtien + ct.getThanhTien() * ct.getSoLuong();
            chiTietRepo.save(ct);
        }
        hd.setThoiGianTao(LocalDate.now());
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        Date date = Date.valueOf(hd.getThoiGianTao());
        String maGD = sim.format(date) + "_00" + hoaDon.getData().getIdHD();
        hd.setMaGiaoDich(maGD);
        hd.setTongTien(tongtien);
        hoaDonRepo.save(hd);

        return new Respon<HoaDon>(1, "them thanh cong", hd);
    }

    public Respon<?> suaHoaDon(Request<HoaDon> hoaDon) {
        SimpleDateFormat simfor = new SimpleDateFormat("yyyyMMdd");
        Date date = Date.valueOf(hoaDon.getData().getThoiGianCapNhat());
        String maGD = simfor.format(date) + "_00" + hoaDon.getData().getIdHD();
        Optional<HoaDon> hd = hoaDonRepo.findById(hoaDon.getData().getIdHD());
        if (hd.isEmpty()) return new Respon<HoaDon>(0, "ID hoa don khong ton tai", null);
        HoaDon hdon = hd.get();
        hdon.setTenHoaDon(hoaDon.getData().getTenHoaDon());
        hdon.setKhachHang(hoaDon.getData().getKhachHang());
        hdon.setThoiGianTao(hoaDon.getData().getThoiGianTao());
        hdon.setThoiGianCapNhat(LocalDate.now());
        hdon.setGhiChu(hoaDon.getData().getGhiChu());
        hdon.setMaGiaoDich(maGD);
        hoaDonRepo.save(hdon);

        //tinh tong tien hoa don
        double tong = 0;
        for (ChiTietHoaDon ctiet : hoaDon.getData().getChiTietHoaDons()) {
            if (sanPhamRepo.findById(ctiet.getSanPham().getIdSP()).isPresent()) {
                Optional<ChiTietHoaDon> chiTiet = chiTietRepo.findById(ctiet.getIdCT());
                if (chiTiet.isPresent()) {
                    Optional<SanPham> sanPham = sanPhamRepo.findById(ctiet.getSanPham().getIdSP());

                    chiTiet.get().setIdHoaDon(ctiet.getHoadon().getIdHD());
                    chiTiet.get().setThanhTien(ctiet.getThanhTien());
                    chiTiet.get().setHoadon(ctiet.getHoadon());
                    chiTiet.get().setSanPham(ctiet.getSanPham());
                    chiTiet.get().setDvt(ctiet.getDvt());
                    chiTiet.get().setHoadon(ctiet.getHoadon());
                    chiTietRepo.save(chiTiet.get());
//                    tong = tong + ctiet.getSoLuong() * ctiet.getThanhTien();

                } else {
                    chiTietRepo.save(ctiet);
                }
                tong = tong + ctiet.getSoLuong() * ctiet.getThanhTien();
            } else {
                return new Respon<SanPham>(0, "Sản phẩm chưa tồn tại. Vui lòng kiểm tra lại", ctiet.getSanPham());
            }
        }
        hdon.setTongTien(tong);
        hdon.setChiTietHoaDons(hoaDon.getData().getChiTietHoaDons());
        hoaDonRepo.save(hdon);

        return new Respon<HoaDon>(1, "Cap nhat thanh cong", hdon);
    }

    public Respon<HoaDon> xoaHoaDon(int id) {
        Optional<HoaDon> hd = hoaDonRepo.findById(id);
        if (hd.isEmpty()) {
            return new Respon<>(0, "ID hoa don khong ton tai", null);
        }
        for (ChiTietHoaDon ct :
                chiTietRepo.findAll()) {
            if (ct.getHoadon().getIdHD() == hd.get().getIdHD()) {
                chiTietRepo.delete(ct);
            }
        }
        hoaDonRepo.deleteById(id);
        return new Respon<HoaDon>(1, "Xoa thanh cong", hd.get());
    }

    //    Viết API lấy dữ liệu hóa đơn cùng chi tiết của hóa đơn đó, sắp xếp theo thời gian tạo mới nhất.
    public Page<HoaDon> layDuLieuTheoTHoiGian(Pageable page) {
        return  hoaDonRepo.findAllByOrderByThoiGianTao(page);
    }

    //    Viết API để lọc hóa đơn theo yêu cầu sau:
//    Lấy hóa đơn theo năm, tháng
    public Page<HoaDon> layDuLieuTheoNam(Pageable page, int year) {
//        List<HoaDon> list = new ArrayList<>();
//        for (HoaDon hd :
//                hoaDonRepo.findAll()) {
//            if (hd.getThoiGianTao().getYear() == year) {
//                list.add(hd);
//            }
//        }
//        return new Respon<List<HoaDon>>(1, "Hien thi hoa don theo nam " + year, list);
        return hoaDonRepo.findAllByThoiGianTaoYear(page, year);
    }

    public Page<?> layDuLieuTheoThang(Pageable page, int month) {
//        List<HoaDon> list = new ArrayList<>();
//        for (HoaDon hd :
//                hoaDonRepo.findAll()) {
//            if (hd.getThoiGianTao().getMonthValue() == month) {
//                list.add(hd);
//            }
//        }
//        return new Respon<List<HoaDon>>(1, "Hien thi hoa don theo thang " + month, list);
        return hoaDonRepo.findAllByThoiGianTaoMonth(page, month);
    }

    //    Lấy hóa đơn được tạo từ ngày ... đến ngày
    public Page<?> layHoaDonGiua2Ngay(Pageable page, LocalDate day1, LocalDate day2) {
//        return new Respon<>(1, "Hien thi hoa don tu ngay " + day1 + " den " + day2, hoaDonRepo.findAllByThoiGianTaoBetween(page, day1, day2));
        return hoaDonRepo.findAllByThoiGianTaoBetween(page, day1, day2);
    }

    //    Lấy hóa đơn theo tổng tiền từ XXXX -> XXXX
    public Page<?> layHoaDonTongTienBTW(Pageable page, int tong1, int tong2) {
//        return new Respon<>(1, "Hien thi hoa don tong tien tu " + tong1 + " den " + tong2, hoaDonRepo.findAllByTongTienBetween(page, tong1, tong2));
        return hoaDonRepo.findAllByTongTienBetween(page, tong1, tong2);
    }

    //    Tìm kiếm hóa đơn theo Mã giao dịch hoặc tên hóa đơn
    public Page<?> layHoaDonTheoMaGiaoDich(Pageable page, String ma) {
//        return new Respon<>(1, "Hien thi hoa don co ma giao dich: " + ma, hoaDonRepo.findAllByMaGiaoDichEquals(page, ma));
        return hoaDonRepo.findAllByMaGiaoDichEquals(page, ma);
    }

    public Page<HoaDon> layHoaDonTheoTen(Pageable page, String tenHD) {
//        return new Respon<>(1, "Hien thi hoa don co ten " + tenHD, hoaDonRepo.findAllByTenHoaDonEquals(tenHD));
        return hoaDonRepo.findAllByTenHoaDonEquals(page, tenHD);
    }


}




