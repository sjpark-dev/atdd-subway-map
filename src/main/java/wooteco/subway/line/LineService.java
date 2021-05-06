package wooteco.subway.line;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.section.Distance;
import wooteco.subway.section.Section;
import wooteco.subway.section.SectionDao;
import wooteco.subway.station.Station;
import wooteco.subway.station.StationDao;
import wooteco.subway.station.StationResponse;

@Service
public class LineService {

    private final LineDao lineDao;
    private final StationDao stationDao;
    private final SectionDao sectionDao;

    public LineService(LineDao lineDao, StationDao stationDao, SectionDao sectionDao) {
        this.lineDao = lineDao;
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    public LineResponse createLine(LineRequest lineRequest) {
        Station upStation = stationDao.findById(lineRequest.getUpStationId());
        Station downStation = stationDao.findById(lineRequest.getDownStationId());

        Section section = new Section(upStation, downStation, Distance.of(lineRequest.getDistance()));
        Line newLine = lineDao.save(new Line(lineRequest.getName(), lineRequest.getColor(), section));
        sectionDao.save(section, newLine.getId());

        return new LineResponse(newLine.getId(), newLine.getName(), newLine.getColor(), stationResponsesByLine(newLine));
    }

    private List<StationResponse> stationResponsesByLine(Line line) {
        return line.path()
            .stream()
            .map(station -> new StationResponse(station.getId(), station.getName()))
            .collect(Collectors.toList());
    }
}
