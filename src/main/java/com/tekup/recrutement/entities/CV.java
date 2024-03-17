package com.tekup.recrutement.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String nom;
    private String url;
    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;
    private Date uploadDate;

    public CV(String fileName, String uuid, String url, byte[] bytes, Date uploadDate) {
        this.nom = fileName;
        this.uuid = uuid;
        this.url = url;
        this.data = bytes;
        this.uploadDate = uploadDate;
    }
}
