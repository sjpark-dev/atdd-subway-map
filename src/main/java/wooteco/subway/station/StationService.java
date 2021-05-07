package wooteco.subway.station;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    private void validateStationId(Long id) {
        if (!stationDao.hasId(id)) {
            throw new IllegalArgumentException("존재하지 않는 역 ID 입니다.");
        }
    }

    private void validateStationName(String name) {
        if (stationDao.hasName(name)) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    public StationResponse createStation(StationRequest stationRequest) {
        validateStationName(stationRequest.getName());
        Station station = new Station(stationRequest.getName());
        Station newStation = stationDao.save(station);
        return new StationResponse(newStation.getId(), newStation.getName());
    }

    public List<StationResponse> showStations() {
        List<Station> stations = stationDao.findAll();

        return stations.stream()
            .map(station -> new StationResponse(station.getId(), station.getName()))
            .collect(Collectors.toList());
    }

    public void deleteStation(Long id) {
        validateStationId(id);
        stationDao.delete(id);
    }
}
