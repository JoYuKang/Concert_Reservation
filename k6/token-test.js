import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 10000,
    duration: '1m',
};

export default function () {
    let createRes = http.get('http://host.docker.internal:8080/token/create'); // docker 

    let token = createRes.headers['Token'];

    check(createRes, {
        '토큰 생성 성공': (r) => r.status === 201 && token !== undefined,
    });

    console.log(`Received Token: ${token}`);

    let queueRes = http.get(`http://host.docker.internal:8080/token/${token}`);

    check(queueRes, {
        '대기열 조회 성공': (r) => r.status === 200,
    });

    console.log(`대기열 응답 시간: ${queueRes.timings.duration}ms`);
    sleep(1);
}
