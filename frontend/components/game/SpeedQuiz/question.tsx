"use client";
import { useCallback, useRef, useEffect } from "react";

interface Quiz {
  question: string;
  answer: string;
}

interface questionProps {
  question: Quiz;
  index: number;
  onNextClick: () => void;
}

// 입력한 문자열이 한글 완성형 문자인지 확인하는 함수
function isHangulCompleted(char: string): boolean {
  const unicode = char.charCodeAt(0);
  return unicode >= 44032 && unicode <= 55203;
}

// 입력한 문자열이 영어인지 확인하는 함수
function isEnglish(char: string): boolean {
  const unicode = char.charCodeAt(0);
  return (unicode >= 65 && unicode <= 90) || (unicode >= 97 && unicode <= 122);
}

//  입력한 문자열이 특수문자인지 확인하는 함수
const isSpecialCharacters = (value: string) => {
  const regex = /[~!@#$%^&*()_+={}\[\]:;<>,.?\\/]/g;
  return regex.test(value);
};

export default function Question({
  question,
  index,
  onNextClick,
}: questionProps) {
  const inputHTML = useRef<HTMLInputElement[]>([]);

  const onInputChange = useCallback((e: any, idx: number) => {
    // console.log(inputHTML.current.length);
    const inputValue = e.target.value;
    // 입력값이 한글 완성형이면 엔터눌렀을 때 다음 input으로 포커스를 이동
    if (isHangulCompleted(inputValue)) {
      if (e.key === "Enter") {
        if (
          inputHTML.current.length > 0 &&
          idx < inputHTML.current.length - 1 &&
          inputHTML.current[idx + 1]
        ) {
          inputHTML.current[idx + 1].focus();
        }
      }
    } else if (isEnglish(inputValue)) {
      // 입력값이 영어이면 한 글자만 입력해도 다음 input으로 포커스를 이동
      if (inputValue.length === 1) {
        inputHTML.current[idx + 1]?.focus();
      }
    } else if(isSpecialCharacters(inputValue)) {
      if (inputValue.length === 1) {
        inputHTML.current[idx + 1]?.focus();
      }
    }
  }, []);

  const inputKeyDown = useCallback(
    (e: React.KeyboardEvent<HTMLInputElement>, idx: number) => {
      // 엔터키 눌렀을 때
      if (e.key === "Enter") {
        // 마지막 칸에서 ENTER을 누르면 다음 문제로
        if (idx === inputHTML.current.length - 1) {
          onNextClick();
          inputHTML.current[0]?.focus();
        }
        // 그렇지 않으면 다음 칸으로
        else inputHTML.current[idx + 1]?.focus();
      } else if (
        // 첫 번째 칸이 아니고, 비어있는 칸에서 BACKSPACE 눌렀을 때
        e.key === "Backspace" &&
        idx !== 0 &&
        inputHTML.current[idx].value.length === 0
      ) {
        // 한 칸 앞으로 이동
        const previousInput = inputHTML.current[idx - 1];
        if (previousInput) previousInput.focus();
      }
    },
    [onNextClick]
  );

  useEffect(() => {
    // question.answer.length 변경될 때마다 inputHTML.current 배열 업데이트
    inputHTML.current = inputHTML.current.slice(0, question.answer.length);
  }, [question.answer.length]);

  // input태그 렌더링
  const renderInput = useCallback(() => {
    const input: JSX.Element[] = [];
    inputHTML.current = [];
    for (let i = 0; i < question.answer.length; i++) {
      input.push(
        <input
          type="text"
          className="w-20 h-20 mx-3 my-3 text-center shadow-md question-input rounded-xl bg-f5gray-200 mb:mx-2"
          maxLength={1}
          key={i}
          onChange={(e) => onInputChange(e, i)}
          onKeyUp={(e) => inputKeyDown(e, i)}
          ref={(el) => {
            if (el) {
              inputHTML.current[i] = el;
            }
          }}
        />
      );
    }

    return input;
  }, [question, onInputChange, inputKeyDown]);

  return (
    <div>
      <div className="flex justify-center my-5">
        <div className="flex justify-start min-h-24 w-[60%] bg-f5green-100 rounded-lg p-5 shadow-md mb:w-[82%]">
          <div className="flex items-center justify-center p-2 mr-3 text-lg font-semibold rounded-full w-7 h-7 bg-f5green-300 text-neutral-50 mb:text-sm">
            {index}
          </div>
          <div className="flex flex-wrap text-sm text-f5black-400">
            {question.question}
          </div>
        </div>
      </div>
      <div className="flex justify-center mx-auto mb:w-[80%]">
        <div className="flex flex-wrap justify-center mb-9">{renderInput()}</div>
      </div>
    </div>
  );
}
