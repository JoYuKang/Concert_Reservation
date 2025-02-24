import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend } from 'k6/metrics';

// 좌석 조회 응답 시간을 측정하는 트렌드 메트릭
let seatQueryTimeTrend = new Trend('seat_query_time');

export let options = {
    vus: 1500,         // 예: 1000명의 유저
    duration: '1m',    // 1분 동안 테스트 실행
};

// setup() 단계에서는 토큰 발급을 미리 수행하여 결과를 반환 (측정 대상 X)
export function setup() {
    let tokens = [];
    // 1900명의 토큰을 미리 발급
    for (let i = 0; i < 1500; i++) {
        let res = http.get('http://host.docker.internal:8080/token/create');
        let token = res.headers['Token'];
        if (token) {
            tokens.push(token);
        }
        // 너무 빠른 호출로 인한 부하를 방지하기 위해 짧게 sleep 할 수 있음
        sleep(0.01);
    }
    // 미리 발급된 토큰 배열을 반환 (이 값은 default 함수에서 data로 사용됨)
    return { tokens: tokens };
}

export default function (data) {
    // 스케줄러에 의한 토큰 활성화를 고려하여 15초 대기
    sleep(15);

    // 1부터 10 사이의 랜덤한 콘서트 ID 생성
    let randomConcertId = Math.floor(Math.random() * 10) + 1;

    // 각 VU는 setup()에서 미리 발급된 토큰 중 하나를 사용
    // __VU는 현재 가상 사용자의 번호 (1부터 시작)
    let token = data.tokens[(__VU - 1) % data.tokens.length];

    // 좌석 조회 요청 및 측정
    let seatStart = Date.now();
    let res = http.get(`http://host.docker.internal:8080/concert/${randomConcertId}/seat`, {
        headers: { Token: token }
    });
    let seatQueryTime = Date.now() - seatStart;
    seatQueryTimeTrend.add(seatQueryTime);

    check(res, {
        '좌석 조회 성공': (r) => r.status === 200,
    });

    sleep(1); // 다음 요청 전 잠시 대기
}
