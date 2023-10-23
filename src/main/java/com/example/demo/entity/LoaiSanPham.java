package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "loaisanpham")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoaiSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idloaisanpham", insertable = false, updatable = false)
    private int idLSP;
    @Column(name = "tenloaisanpham")
    private String tenLoaiSanPham;

    @OneToMany(mappedBy = "loaiSanPham", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "ref4")
    private List<SanPham> sanphams;

}
