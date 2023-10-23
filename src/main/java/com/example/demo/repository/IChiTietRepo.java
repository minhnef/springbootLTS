package com.example.demo.repository;

import com.example.demo.entity.ChiTietHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChiTietRepo extends JpaRepository<ChiTietHoaDon, Integer> {
}
