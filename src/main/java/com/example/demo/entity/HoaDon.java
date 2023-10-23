package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhoadon")
    private int idHD;
    @Column(name = "idkhachhang", insertable = false, updatable = false)
    private int idKH;
    @Column(name = "tenhoadon")
    private String tenHoaDon;
    @Column(name = "magiaodich")
    private String maGiaoDich;
    @Column(name = "thoigiantao")
    private LocalDate thoiGianTao;
    @Column(name = "thoigiancapnhat")
    private LocalDate thoiGianCapNhat;
    @Column(name = "ghichu")
    private String ghiChu;
    @Column(name = "tongtien")
    private double tongTien;

    @OneToMany(mappedBy = "hoadon", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ref2")
    private List<ChiTietHoaDon> chiTietHoaDons;

    @ManyToOne()
    @JsonBackReference(value = "ref1")
    @JoinColumn(name = "idkhachhang", foreignKey = @ForeignKey(name = "fk-hd-kh"))
    private KhachHang khachHang;


}
