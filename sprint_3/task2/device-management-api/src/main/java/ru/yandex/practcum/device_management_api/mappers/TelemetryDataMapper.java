package ru.yandex.practcum.device_management_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceTelemetryResponseMessage;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.redis.TelemetryData;
import ru.yandex.practcum.device_management_api.model.rest.DeviceRegisterDataResponse;
import ru.yandex.practcum.device_management_api.model.rest.DeviceTelemetryResponse;

@Mapper
public interface TelemetryDataMapper {

    TelemetryData toTelemetryData(DeviceTelemetryResponseMessage message);
    DeviceTelemetryResponse toDeviceRegisterDataResponse(TelemetryData data);
}
