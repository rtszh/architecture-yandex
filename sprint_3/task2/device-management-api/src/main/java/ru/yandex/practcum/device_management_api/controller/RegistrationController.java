package ru.yandex.practcum.device_management_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practcum.device_management_api.model.rest.DeviceRegisterDataResponse;
import ru.yandex.practcum.device_management_api.model.rest.DeviceResponse;
import ru.yandex.practcum.device_management_api.model.rest.NewDeviceRequest;
import ru.yandex.practcum.device_management_api.service.DeviceRegistrationService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final DeviceRegistrationService service;

    @PostMapping("/device/register/init")
    public CompletableFuture<ResponseEntity<DeviceResponse>> deviceRegisterInit(@RequestBody NewDeviceRequest request) {
        return service.registerDevice(request)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/device/register/data/{messageId}")
    public ResponseEntity<DeviceRegisterDataResponse> deviceRegisterData(@PathVariable("messageId") String messageId) {
        return ResponseEntity.ok(service.findRegisterDataByMessageId(messageId));
    }
}
