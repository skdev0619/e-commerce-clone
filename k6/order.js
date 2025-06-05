import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    //Load Test
    stages: [
        { duration: '1m', target: 100 },
        { duration: '2m', target: 100 },
        { duration: '1m', target: 0 },
    ],
    //Spike Test
    // stages: [
    //     { duration: '30s', target: 10 },    // 워밍업
    //     { duration: '30s', target: 100 },   // 30초 동안 급격한 부하 증가 (스파이크)
    //     { duration: '30s', target: 0 },     // 30초 동안 부하 급감
    // ],
    thresholds: {
        http_req_duration: ['p(99)<200'],
        http_req_failed: ['rate<0.01'],
    },
};

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

//주문 항목(OrderItem) 리스트 생성 (1~3개 상품, 랜덤 수량)
function generateOrderItems() {
    const itemCount = getRandomInt(1, 3); // 주문 항목 개수 (1~3개)
    const items = [];

    for (let i = 0; i < itemCount; i++) {
        items.push({
            productId: getRandomInt(1, 5),  // 상품 ID (1~5번 중 랜덤)
            quantity: getRandomInt(1, 5),   // 수량 (1~5개)
            price: 1,                       // 가격 (고정값 1)
        });
    }

    return items;
}

export default function () {
    const url = 'http://localhost:8080/api/v1/orders';

    const payload = JSON.stringify({
        userId: getRandomInt(1, 500),       // 사용자 ID (1~500 중 랜덤)
        orderItems: generateOrderItems(),   // 주문 항목
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const res = http.post(url, payload, params);

    // 응답 상태 코드가 201인지 확인
    const success = check(res, {
        'status is 201': (r) => r.status === 201,
    });

    // 요청 실패 시 로그 출력 (응답 코드, 응답 시간, 응답 본문)
    if (!success) {
        console.error(`Failed Status : ${res.status} / Duration : ${res.timings.duration}ms / Body : ${res.body}`);
    }
}
