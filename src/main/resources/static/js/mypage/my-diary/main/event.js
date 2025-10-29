// 공유하기 버튼 클릭 이벤트

const shareButton = document.querySelector(".share-button");
const toast = document.querySelector(".toast");

shareButton.addEventListener("click", (e) => {
    toast.style.display = "block";
    toast.classList.remove("hide");
    toast.classList.add("show");
    setTimeout(() => {
        toast.classList.remove("show");
        toast.classList.add("hide");
        setTimeout(() => {
            toast.style.display = "none";
        }, 500);
    }, 3000);
    clip();
});

function clip() {
    var url = "";
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.location.href; // 현재 URL을 가져옵니다.
    textarea.value = url;
    textarea.select(); // 텍스트 영역의 내용을 선택합니다.
    document.execCommand("copy"); // 선택된 내용을 클립보드에 복사합니다.
    document.body.removeChild(textarea); // 텍스트 영역을 제거합니다.
}

// 여행 경로에서 경계선 주기
const color = [
    "#FF1744", // neon red (형광 빨강)
    "#FF9100", // neon orange (형광 주황)
    "#FFEA00", // neon yellow (형광 노랑)
    "#00E676", // neon green (형광 초록)
    "#2979FF", // neon blue (형광 파랑)
    "#304FFE", // neon navy (형광 남색 느낌)
    "#D500F9", // neon purple (형광 보라)
];
const tripPath = document.querySelectorAll(".trip-path");

tripPath.forEach((path) => {
    const markers = path.querySelectorAll(".marker");
    markers.forEach((marker, index) => {
        marker.firstElementChild.style.border = `1px solid ${
            color[index % color.length]
        }`;
    });
});

// 비행기 이동 애니메이션
const markers = document.querySelectorAll(".marker-circle.last");
let pos = -2;

setInterval(() => {
    pos += 1; // 1씩 증가
    if (pos > 95) {
        pos = -2;
        markers.forEach((marker) => {
            marker.style.transition = `left 0s linear`;
            marker.style.left = "-2%";
        });
    } else {
        markers.forEach((marker) => {
            marker.style.transition = `left 0.5s linear`;
            marker.style.left = pos + "%";
        });
    }
}, 100); // 0.1초마다 이동

// navbar 이벤트
const navButtons = document.querySelectorAll(".nav-button");
const crewMembers = document.querySelectorAll(".crew-member");

navButtons.forEach((button, i) => {
    button.addEventListener("click", (e) => {
        navButtons.forEach((btn) => btn.classList.remove("active"));
        button.classList.add("active");

        crewMembers.forEach((crew, j) => {
            if (i === j) {
                crew.classList.add("active");
            } else {
                crew.classList.remove("active");
            }
        });
        member(i);
        noEdit();
    });
});

// 편집 눌렀을 때

const updateButton = document.querySelector(".update-button");
const boxes = document.querySelectorAll(".checkbox");
const updateDiv = document.querySelector(".update-div");
const total = document.querySelector(".total");
const cancle = document.querySelector(".cancle");
const checks = document.querySelectorAll(".check");
const tripPathWraps = document.querySelectorAll(".trip-path-wrap");

updateButton.addEventListener("click", (e) => {
    boxes.forEach((box) => {
        box.style.display = "block";
    });

    updateDiv.style.display = "block";
    total.style.display = "block";
    updateButton.style.display = "none";

    checks.forEach((check) => (check.style.display = "block"));
    tripPathWraps.forEach((check) => (check.style = "opacity: 0.5"));
});

// 취소 눌렀을 때
function noEdit() {
    boxes.forEach((box) => {
        box.style.display = "none";
    });

    updateDiv.style.display = "none";
    total.style.display = "none";
    updateButton.style.display = "block";

    checkboxInputs.forEach((input) => {
        input.checked = false;
        count = 0;
        const wrapper = input.closest(".checkbox");
        wrapper.classList.remove("active");
        checkNumber.innerText = count;
    });
    checks.forEach((check) => (check.style.display = "none"));
    tripPathWraps.forEach((check) => (check.style = "opacity: 1"));
}
cancle.addEventListener("click", (e) => {
    noEdit();
});

// 편집 체크박스

const checkboxInputs = document.querySelectorAll(".checkbox-input");
const checkNumber = document.querySelector(".check-number");
const remove = document.querySelector(".remove");
let count = 0;

checkboxInputs.forEach((input) => {
    input.addEventListener("change", (e) => {
        const wrapper = input.closest(".checkbox");
        if (input.checked) {
            wrapper.classList.add("active");
            count++;
        } else {
            wrapper.classList.remove("active");
            count--;
        }
        checkNumber.innerText = count;
        if (count >= 1) {
            remove.disabled = false;
            remove.classList.add("active");
        } else {
            remove.disabled = true;
            remove.classList.remove("active");
        }
    });
});

// 일기 개수

function updateTotal() {
    const totalNumber = document.querySelector(".total-number");
    const inputs = document.querySelectorAll(".checkbox-input");
    totalNumber.innerText = inputs.length;
}

// 삭제

remove.addEventListener("click", (e) => {
    checkboxInputs.forEach((input) => {
        if (input.checked) {
            count = 0;
            const profile = input.closest(".travel");
            profile.remove();
            checkNumber.innerText = count;
        }
    });
    updateTotal();
});

// 전체개수
const navFooter = document.querySelector(".nav-footer");

function member(i) {
    if (i === 0) {
        updateTotal();
        navFooter.style.display = "flex";
    } else {
        navFooter.style.display = "none";
    }
}

member(0);

// 여정추가 버튼

const addButton = document.querySelector(".add-button");
const addModal = document.querySelector(".add-modal");

addButton.addEventListener("click", (e) => {
    addModal.style.display = "flex";
});

// 나라 자동완성
const countries = [
    "가나",
    "가봉",
    "가이아나",
    "감비아",
    "과테말라",
    "그레나다",
    "그리스",
    "기니",
    "기니비사우",
    "나미비아",
    "나우루",
    "나이지리아",
    "남수단",
    "남아프리카공화국",
    "네덜란드",
    "네팔",
    "노르웨이",
    "뉴질랜드",
    "니카라과",
    "니제르",
    "대한민국",
    "덴마크",
    "도미니카공화국",
    "도미니카연방",
    "독일",
    "동티모르",
    "라오스",
    "라이베리아",
    "라트비아",
    "러시아",
    "레바논",
    "레소토",
    "루마니아",
    "룩셈부르크",
    "르완다",
    "리비아",
    "리투아니아",
    "리히텐슈타인",
    "마다가스카르",
    "마셜제도",
    "말라위",
    "말레이시아",
    "말리",
    "멕시코",
    "모나코",
    "모로코",
    "모리셔스",
    "모리타니",
    "모잠비크",
    "몬테네그로",
    "몰도바",
    "몰디브",
    "몰타",
    "몽골",
    "미국",
    "미얀마",
    "미크로네시아",
    "바누아투",
    "바레인",
    "바베이도스",
    "바하마",
    "방글라데시",
    "베냉",
    "베네수엘라",
    "베트남",
    "벨기에",
    "벨라루스",
    "벨리즈",
    "보스니아헤르체고비나",
    "보츠와나",
    "볼리비아",
    "부룬디",
    "부르키나파소",
    "부탄",
    "북마케도니아",
    "북한",
    "불가리아",
    "브라질",
    "브루나이",
    "사모아",
    "사우디아라비아",
    "사이프러스",
    "산마리노",
    "상투메프린시페",
    "세네갈",
    "세르비아",
    "세이셸",
    "세인트루시아",
    "세인트빈센트그레나딘",
    "세인트키츠네비스",
    "소말리아",
    "솔로몬제도",
    "수단",
    "수리남",
    "스리랑카",
    "스웨덴",
    "스위스",
    "스페인",
    "슬로바키아",
    "슬로베니아",
    "시리아",
    "시에라리온",
    "싱가포르",
    "아랍에미리트",
    "아르메니아",
    "아르헨티나",
    "아이슬란드",
    "아이티",
    "아일랜드",
    "아제르바이잔",
    "아프가니스탄",
    "안도라",
    "알바니아",
    "알제리",
    "앙골라",
    "앤티가바부다",
    "에콰도르",
    "에스와티니",
    "에스토니아",
    "에티오피아",
    "엘살바도르",
    "영국",
    "예멘",
    "오만",
    "오스트리아",
    "온두라스",
    "요르단",
    "우간다",
    "우루과이",
    "우즈베키스탄",
    "우크라이나",
    "이라크",
    "이란",
    "이스라엘",
    "이집트",
    "이탈리아",
    "인도",
    "인도네시아",
    "일본",
    "잠비아",
    "적도기니",
    "조지아",
    "중앙아프리카공화국",
    "중국",
    "지부티",
    "짐바브웨",
    "체코",
    "칠레",
    "카메룬",
    "카보베르데",
    "카자흐스탄",
    "카타르",
    "캄보디아",
    "캐나다",
    "케냐",
    "코모로",
    "코스타리카",
    "코트디부아르",
    "콜롬비아",
    "콩고공화국",
    "콩고민주공화국",
    "쿠바",
    "쿠웨이트",
    "쿡제도",
    "크로아티아",
    "키르기스스탄",
    "키리바시",
    "타지키스탄",
    "탄자니아",
    "태국",
    "토고",
    "통가",
    "투르크메니스탄",
    "투발루",
    "튀니지",
    "튀르키예",
    "트리니다드토바고",
    "파나마",
    "파라과이",
    "파키스탄",
    "파푸아뉴기니",
    "팔라우",
    "팔레스타인",
    "페루",
    "포르투갈",
    "폴란드",
    "프랑스",
    "피지",
    "핀란드",
    "필리핀",
];

function searchCountry(params) {
    const lines = document.querySelectorAll(".line");

    lines.forEach((line) => {
        const countryInput = line.querySelector(".input-tag-wrap");
        const countryDropdown = line.querySelector("#countryDropdown");

        if (countryInput && countryDropdown) {
            countryInput.addEventListener("input", () => {
                const value = countryInput.value.trim();
                countryDropdown.innerHTML = "";

                if (!value) {
                    countryDropdown.style.display = "none";
                    return;
                }

                const filtered = countries.filter((c) => c.includes(value));

                if (filtered.length) {
                    filtered.forEach((country) => {
                        const li = document.createElement("li");
                        li.textContent = country;
                        li.addEventListener("click", () => {
                            countryInput.value = country;
                            countryDropdown.style.display = "none";
                        });
                        countryDropdown.appendChild(li);
                    });
                    countryDropdown.style.display = "block";
                } else {
                    countryDropdown.style.display = "none";
                }
            });

            // 바깥 클릭 시 닫기
            document.addEventListener("click", (e) => {
                if (!e.target.closest(".autocomplete")) {
                    countryDropdown.style.display = "none";
                }
            });
        }
    });
}

// 경로 추가

const pathAddButton = document.querySelector(".path-add-button");
const pathContainer = document.querySelector(".path-container");

pathAddButton.addEventListener("click", () => {
    const newLine = document.createElement("div");
    newLine.className = "line fill";
    newLine.innerHTML = `
            <input type="text" placeholder="나라 이름을 입력해주세요." class="input-tag-wrap">
            <ul id="countryDropdown" class="write-content-select-dropdown" style="display: block;"></ul>
            <button type="button" class="delete-sub-img" title="삭제" aria-label="삭제">
                <svg class="icon" width="18" height="18" fill="currentColor" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet">
                <path d="M6 19V7h12v12a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2zM19 4v2H5V4h3.5l1-1h5l1 1H19z"></path>
                </svg>
            </button>
    `;

    const lines = pathContainer.querySelectorAll(".line.fill");
    const lastLine = lines[lines.length - 1];

    pathContainer.insertBefore(newLine, lastLine);
    const deletePaths = document.querySelectorAll(".delete-sub-img");

    // 경로 삭제
    deletePaths.forEach((btn) => {
        console.log("들어옴");

        btn.addEventListener("click", (e) => {
            btn.closest(".line").remove();
        });
    });
    searchCountry();
});

const modalClose = document.querySelector(".add-close-button");
const modalAddClose = document.querySelector(".modal-add-button");

modalClose.addEventListener("click", (e) => {
    addModal.style.display = "none";
});

modalAddClose.addEventListener("click", (e) => {
    addModal.style.display = "none";
});
