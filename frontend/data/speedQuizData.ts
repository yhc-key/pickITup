interface Quiz {
  question: string;
  answer: string;
}

export const speedQuizDataMap: Map<string, Quiz[]> = new Map([
[
  "Javascript",
  [
    { question: "변수와 함수 선언이 스코프의 최상단으로 올려져 실행되는 것은?", answer: "호이스팅" },
    { question: "중복 선언과 재할당이 불가능하고, 변수를 선언하기 전에 참조할 수 없는 변수는?", answer: "const" },
    { question: "자신이 속한 객체 또는 자신이 생성할 인스턴스를 가리키는 자기 참조 변수는?", answer: "this" },
    { question: "이벤트가 발생한 요소부터 점점 부모 요소를 거슬러 올라가서 window까지 이벤트를 전파하는 것은?", answer: "이벤트버블링" },
    { question: "해당 변수가 선언되어있는 공간이나 환경으로 각각의 변수, 매개변수, 함수 등이 유효한  영역은?", answer: "스코프" },
    { question: "ES6에서 도입된 문법으로 객체, 배열의 값을 추출해서 변수에 바로 할당할 수 있는 문법은?", answer: "구조분해할당" },
    { question: "브라우저가 가지고 있는 XMLHttpRequest 객체를 이용해서 서버와 브라우저가 비동기 방식으로 데이터를 교환할 수 있는 통신기능은?", answer: "ajax" },
    { question: "콜 스택과 콜백 큐를 감시하는 역할로 콜백 큐에 함수가 존재하고 콜 스택이 비었다면 콜백 큐에서 콜백을 꺼내 콜 스택에 넣어주는 역할을 하는 것은?", answer: "이벤트루프" },
    { question: "실행 가능한 코드가 실행되기 위해서 필요한 환경을 의미하는 것은?", answer: "실행컨텍스트" },
    { question: "자바스크립트는 00000기반 언어로, 다른 객체에 대한 링크를 보유하는 비공개 속성은 무엇인가요? ", answer: "프로토타입" },
  ],
],
]);