package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.respon.Request;
import com.example.demo.entity.respon.Respon;
import com.example.demo.service.HoaDonSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "apihoadon")
public class HoaDonController {
    @Autowired
    HoaDonSer hoaDonSer;

    @PostMapping(value = "themHoaDon")
    public ResponseEntity<?> themHoaDon(@RequestBody Request<HoaDon> hoaDon) {
        return ResponseEntity.ok(hoaDonSer.themHoaDon(hoaDon));
    }

    @PutMapping(value = "suaHoaDon")
    public ResponseEntity<?> suaHoaDon( @RequestBody Request<HoaDon> hoaDon) {
        return ResponseEntity.ok(hoaDonSer.suaHoaDon(hoaDon));
    }

    @DeleteMapping(value = "xoaHoaDon")
    public ResponseEntity<?>xoaHoaDon(@RequestParam int id){
        return ResponseEntity.ok(hoaDonSer.xoaHoaDon(id));
    }

    @GetMapping(value = "layHoaDonTheoThoiGian")
    public ResponseEntity<?> layHoaDon(){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layDuLieuTheoTHoiGian(page));
    }

    @GetMapping(value = "layHoaDonTheoNam")
    public ResponseEntity<?> layHoaDonTheoNam(@RequestParam int year){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layDuLieuTheoNam(page, year));
    }

    @GetMapping(value = "layHoaDonTheoThang")
    public ResponseEntity<?>layHoaDonTheoThang(@RequestParam int month){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layDuLieuTheoThang(page, month));
    }

    @GetMapping(value = "layHoaDonGiua2Ngay")
    public ResponseEntity<?>layHoaDonGiua2Ngay(@RequestParam LocalDate day1, @RequestParam LocalDate day2){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layHoaDonGiua2Ngay(page, day1, day2));
    }

    @GetMapping(value = "layHoaDonTongTienTrongKhoang")
    public ResponseEntity<?>layHoaDonTrongKhoang(@RequestParam int tong1, @RequestParam int tong2){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layHoaDonTongTienBTW(page, tong1,tong2));
    }

    @GetMapping(value = "layHoaDonTheoMaGiaoDich")
    public ResponseEntity<?>layHoaDonTheoMaGD(@RequestParam String ma){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layHoaDonTheoMaGiaoDich(page, ma));
    }

    @GetMapping(value = "layHoaDonTheoTen")
    public ResponseEntity<?>layHoaDonTheoTen(@RequestParam String tenHD){
        Pageable page = PageRequest.of(0,25);
        return ResponseEntity.ok(hoaDonSer.layHoaDonTheoTen(page, tenHD));
    }
}
