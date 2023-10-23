package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sanpham")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsanpham")
    private int idSP;
    @Column(name = "idloaisanpham", insertable = false, updatable = false)
    private int idLSP;
    @Column(name = "tensanpham")
    private String tenSP;
    @Column(name = "giathanh")
    private double giaThanh;
    @Column(name = "mota")
    private String moTa;
    @Column(name = "ngayhethan")
    private LocalDate ngayHetHan;
    @Column(name = "kyhieusanpham")
    private String kyHieuSP;

    @ManyToOne()
    @JoinColumn(name = "idloaisanpham", foreignKey = @ForeignKey(name = "fk-sp-lsp"))
    @JsonBackReference(value = "ref4")
    private LoaiSanPham loaiSanPham;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ref3")
    private List<ChiTietHoaDon> chiTietHoaDons;

}


