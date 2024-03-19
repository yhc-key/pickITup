interface Quiz {
  question: string;
  answer: boolean;
}

export const OXQuizDataMap: Map<string, Quiz[]> = new Map([
[
  "Javascript",
  [
    { question: "변수와 함수 선언이 스코프의 최상단으로 올려져 실행되는 것을 '호이스팅'이라고 한다.", answer: true },
    { question: "'const 변수'는 중복선언과 재할당이 불가능하고, 변수를 선언하기 전에 참조할 수 없다.", answer: true },
    { question: "이벤트가 발생한 요소부터 점점 부모 요소를 거슬러 올라가서 window까지 이벤트를 전파하는 것을 '이벤트 캡쳐링'이라고 한다.", answer: false },
    { question: "'let 변수'는 중복선언과 재할당이 가능하고, 변수를 선언하기 전에 참조가 가능하다.", answer: false },
    { question: "'async 작업'은 요청을 보낸 후 응답을 기다렸다가 해당 응답을 받아야 다음 동작을 수행한다.", answer: false },
    { question: "데이터 자체를 복사하지 않고 해당 데이터의 참조값을 전달하여 하나의 데이터를 공유하는 것은 '얕은 복사'에 대한 설명이다.", answer: false },
    { question: "브라우저가 가지고 있는 XMLHttpRequest 객체를 이용해서 서버와 브라우저가 비동기 방식으로 데이터를 교환할 수 있는 통신기능을 'Ajax'라고 한다.", answer: true },
    { question: "화살표 함수 안에서 this는 선언될 당시에 상위 실행 문맥, 그 스코프에 해당하는 실행 문맥 상의 this 바인딩 컴포넌트를 참조한다.", answer: true },
    { question: "콜 스택과 콜백 큐를 감시하는 역할로 콜백 큐에 함수가 존재하고 콜 스택이 비었다면 콜백 큐에서 콜백을 꺼내 콜 스택에 넣어주는 역할을 하는 것은 '이벤트 루프'이다.", answer: true },
    { question: "자바스크립트는 싱글 스레드 기반 언어이다.", answer: true },
  ],
],
]);