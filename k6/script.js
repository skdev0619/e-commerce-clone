import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
    stages: [
        {duration: '2m', target: 100},
        {duration: '5m', target: 1000},
        {duration: '2m', target: 0}
    ],
    thresholds:{
        http_req_duration : ['p(99)<200'],
        http_req_failed : ['rate<0.01'],
    },
    gracefulRampDown : '30s'
};

export default function () {
    http.get(
        'http://localhost:8080/api/v1/ranking/top-selling-products?startDate=2025-05-09&endDate=2025-05-11&limit=5',
        {headers: {'Content-Type': 'application/json'}}
    );
    sleep(1);
}
