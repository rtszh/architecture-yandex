package ru.yandex.practcum.device_management_api.model.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private String deviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
