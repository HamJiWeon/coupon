// load-test.js
import http from 'k6/http';

export const options = {
    vus: 30,        // 동시 가상 유저 30명
    duration: '20s',
};

export default function () {
    const userId = Math.floor(Math.random() * 5000) + 1; // 1~5000 랜덤
    http.post(`http://localhost:8080/coupons/1/issue?userId=${userId}`);
}