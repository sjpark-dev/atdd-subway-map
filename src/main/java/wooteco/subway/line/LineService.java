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

    private void validateLineId(Long id) {
        if (!lineDao.hasId(id)) {
            throw new IllegalArgumentException("존재하지 않는 노선 ID 입니다.");
        }
    }

    private void validateLineName(Long id, String name) {
        if (lineDao.hasNameAndDifferentId(id, name)) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    private void validateLineColor(Long id, String color) {
        if (lineDao.hasColorAndDifferentId(id, color)) {
            throw new IllegalArgumentException("이미 존재하는 색깔입니다.");
        }
    }

    private void validateStationId(Long id) {
        if (!stationDao.hasId(id)) {
            throw new IllegalArgumentException("존재하지 않는 역 id 입니다.");
        }
    }

    private void validateDuplicateStationId(Long upStationId, Long downStationId) {
        if (upStationId.equals(downStationId)) {
            throw new IllegalArgumentException("상행 종점역과 하행 종점역이 같습니다.");
        }
    }

    public LineResponse createLine(LineRequest lineRequest) {
        validateLineCreation(lineRequest);
        Station upStation = stationDao.findById(lineRequest.getUpStationId());
        Station downStation = stationDao.findById(lineRequest.getDownStationId());

        Section section = new Section(upStation, downStation,
            Distance.of(lineRequest.getDistance()));
        Line newLine = lineDao
            .save(new Line(lineRequest.getName(), lineRequest.getColor(), section));
        sectionDao.save(section, newLine.getId());

        return new LineResponse(newLine.getId(), newLine.getName(), newLine.getColor(),
            stationResponsesByLine(newLine));
    }

    private void validateLineCreation(LineRequest lineRequest) {
        validateStationId(lineRequest.getUpStationId());
        validateStationId(lineRequest.getDownStationId());
        validateDuplicateStationId(lineRequest.getUpStationId(), lineRequest.getDownStationId());
        validateLineName(lineRequest.getName());
        validateLineColor(lineRequest.getColor());
    }

    private void validateLineColor(String color) {
        if (lineDao.hasColor(color)) {
            throw new IllegalArgumentException("이미 존재하는 색깔입니다.");
        }
    }

    private void validateLineName(String name) {
        if (lineDao.hasName(name)) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    private List<StationResponse> stationResponsesByLine(Line line) {
        return line.path()
            .stream()
            .map(station -> new StationResponse(station.getId(), station.getName()))
            .collect(Collectors.toList());
    }

    public List<LineResponse> showLines() {
        List<Line> lines = lineDao.findAll();

        return lines.stream()
            .map(it -> new LineResponse(it.getId(), it.getName(), it.getColor()))
            .collect(Collectors.toList());
    }


    public LineResponse showLine(Long id) {
        validateLineId(id);
        Line line = lineDao.findById(id);
        return new LineResponse(line.getId(), line.getName(), line.getColor());
    }

    public void updateLine(Long id, LineRequest lineRequest) {
        validateLineUpdate(id, lineRequest);
        Line line = new Line(id, lineRequest.getName(), lineRequest.getColor());
        lineDao.updateById(id, line);
    }

    private void validateLineUpdate(Long id, LineRequest lineRequest) {
        validateLineId(id);
        validateLineName(id, lineRequest.getName());
        validateLineColor(id, lineRequest.getColor());
    }

    public void deleteLine(Long id) {
        validateLineId(id);
        lineDao.deleteById(id);
    }
}
