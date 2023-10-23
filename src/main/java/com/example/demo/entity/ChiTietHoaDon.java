package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chitiethoadon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idchitiet")
    private int idCT;
    @Column(name = "idhoadon", insertable = false, updatable = false)
    private int idHoaDon;
    @Column(name = "idsanpham", insertable = false, updatable = false)
    private int idSP;
    @Column(name = "soluong")
    private int soLuong;
    @Column(name = "dvt")
    private String dvt;
    @Column(name = "thanhtien")
    private double thanhTien;

    @ManyToOne()
    @JsonBackReference(value = "ref3")
    @JoinColumn(name = "idsanpham", foreignKey = @ForeignKey(name = "fk-ct-spS"))
    private SanPham sanPham;

    @ManyToOne()
    @JsonBackReference(value = "ref2")
    @JoinColumn(name = "idhoadon", foreignKey = @ForeignKey(name = "fk-ct-hd"))
    private HoaDon hoadon;

}
