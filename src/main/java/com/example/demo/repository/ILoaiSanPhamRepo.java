package com.example.demo.repository;

import com.example.demo.entity.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoaiSanPhamRepo extends JpaRepository<LoaiSanPham, Integer> {
}
