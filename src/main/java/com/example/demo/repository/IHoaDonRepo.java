package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IHoaDonRepo extends JpaRepository<HoaDon, Integer> {
    public Page<HoaDon> findAllByOrderByThoiGianTao(Pageable page);

    @Query(value = "select  * from hoadon u WHERE year(thoigiantao) = :year", nativeQuery = true)
    public Page<HoaDon> findAllByThoiGianTaoYear(Pageable page, @Param("year") int year);

    @Query(value = "select  * from hoadon u WHERE month(thoigiantao) = :month", nativeQuery = true)
    public Page<HoaDon> findAllByThoiGianTaoMonth(Pageable page, @Param("month") int month);

    public Page<HoaDon> findAllByThoiGianTaoBetween(Pageable page, LocalDate day1, LocalDate day2);

    public Page<HoaDon> findAllByTongTienBetween(Pageable page, int tong1, int tong2);

    public Page<HoaDon> findAllByMaGiaoDichEquals(Pageable page, String ma);

    public Page<HoaDon> findAllByTenHoaDonEquals(Pageable page, String tenHD);
}
