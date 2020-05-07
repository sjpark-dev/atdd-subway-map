import {EVENT_TYPE} from "../../utils/constants.js";
import {colorSelectOptionTemplate, subwayLinesTemplate} from "../../utils/templates.js";
import {subwayLineColorOptions} from "../../utils/defaultSubwayData.js";
import Modal from "../../ui/Modal.js";
import api from "../../api/index.js";

function AdminLine() {
  const $subwayLinesInfo = document.querySelector(".lines-info");
  const $subwayLineFirstTimeView = $subwayLinesInfo.children[1];
  const $subwayLineLastTimeView = $subwayLinesInfo.children[3];
  const $subwayLineIntervalTimeView = $subwayLinesInfo.children[5];

  const $subwayLineList = document.querySelector("#subway-line-list");
  const $subwayLineNameInput = document.querySelector("#subway-line-name");
  const $subwayLineColorInput = document.querySelector("#subway-line-color");
  const $subwayLineFirstTimeInput = document.querySelector("#first-time");
  const $subwayLineLastTimeInput = document.querySelector("#last-time");
  const $subwayLineIntervalTimeInput = document.querySelector("#interval-time");

  const $createSubwayLineButton = document.querySelector(
      "#subway-line-create-form #submit-button"
  );
  const subwayLineModal = new Modal();

  const onCreateSubwayLine = event => {
    event.preventDefault();
    const newSubwayLine = {
      name: $subwayLineNameInput.value,
      color: $subwayLineColorInput.value,
      startTime: $subwayLineFirstTimeInput.value,
      endTime: $subwayLineLastTimeInput.value,
      intervalTime: $subwayLineIntervalTimeInput.value
    };

    api.line.create(newSubwayLine).then(line => {
      if (!line.name) {
        return;
      }
      $subwayLineList.insertAdjacentHTML(
          "beforeend",
          subwayLinesTemplate(line)
      );
    });
    subwayLineModal.toggle();
    $subwayLineNameInput.value = "";
    $subwayLineColorInput.value = "";
    $subwayLineFirstTimeInput.value = "";
    $subwayLineLastTimeInput.value = "";
    $subwayLineIntervalTimeInput.value = "";
  };

  const onDeleteSubwayLine = event => {
    const $target = event.target;
    const isDeleteButton = $target.classList.contains("mdi-delete");
    if (isDeleteButton) {
      $target.closest(".subway-line-item").remove();
    }
  };

  const onUpdateSubwayLine = event => {
    const $target = event.target;
    const isUpdateButton = $target.classList.contains("mdi-pencil");
    if (isUpdateButton) {
      subwayLineModal.toggle();
    }
  };

  const onEditSubwayLine = event => {
    const $target = event.target;
    const isDeleteButton = $target.classList.contains("mdi-pencil");
  };

  const onSelectSubwayLine = event => {
    const $target = event.target;
    const isSubwayLineItem = $target.classList.contains("subway-line-item");
    if (isSubwayLineItem) {
      const id = $target.dataset.id
      api.line.get(id).then(line => {
        $subwayLineFirstTimeView.innerText = line.startTime;
        $subwayLineLastTimeView.innerText = line.endTime;
        $subwayLineIntervalTimeView.innerText = `${line.intervalTime}분`;
      });
    }
  }

  const initDefaultSubwayLines = () => {
    api.line.get().then(lines => {
      lines.map(line => {
        $subwayLineList.insertAdjacentHTML(
            "beforeend",
            subwayLinesTemplate(line)
        );
      });
    });
  };

  const initEventListeners = () => {
    $subwayLineList.addEventListener(EVENT_TYPE.CLICK, onDeleteSubwayLine);
    $subwayLineList.addEventListener(EVENT_TYPE.CLICK, onUpdateSubwayLine);
    $subwayLineList.addEventListener(EVENT_TYPE.CLICK, onSelectSubwayLine);
    $createSubwayLineButton.addEventListener(
        EVENT_TYPE.CLICK,
        onCreateSubwayLine
    );
  };

  const onSelectColorHandler = event => {
    event.preventDefault();
    const $target = event.target;
    if ($target.classList.contains("color-select-option")) {
      document.querySelector("#subway-line-color").value =
          $target.dataset.color;
    }
  };

  const initCreateSubwayLineForm = () => {
    const $colorSelectContainer = document.querySelector(
        "#subway-line-color-select-container"
    );
    const colorSelectTemplate = subwayLineColorOptions
        .map((option, index) => colorSelectOptionTemplate(option, index))
        .join("");
    $colorSelectContainer.insertAdjacentHTML("beforeend", colorSelectTemplate);
    $colorSelectContainer.addEventListener(
        EVENT_TYPE.CLICK,
        onSelectColorHandler
    );
  };

  this.init = () => {
    initDefaultSubwayLines();
    initEventListeners();
    initCreateSubwayLineForm();
  };
}

const adminLine = new AdminLine();
adminLine.init();
