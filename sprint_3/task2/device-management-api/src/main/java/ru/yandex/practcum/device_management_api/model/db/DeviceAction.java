package ru.yandex.practcum.device_management_api.model.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_action")
public class DeviceAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;
}
