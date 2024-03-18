"use-client"
import { useCallback, useRef } from "react";

interface Quiz {
  question: string;
  answer: string;
}

interface questionProps {
  question: Quiz;
  index: number;
  onNextClick: () => void;
}

export default function Question({ question, index, onNextClick }: questionProps) {
  const inputHTML = useRef<HTMLInputElement[]>([]);

  const onInputChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>, idx: number) => {
      if (e.target.value.length === 1) {
        if (
          inputHTML.current.length > 0 &&
          idx < inputHTML.current.length - 1 &&
          inputHTML.current[idx + 1]
        ) {
          inputHTML.current[idx + 1].focus();
        }
      }
    },
    [inputHTML],
  );
  
  const inputKeyDown = useCallback(
    (e: React.KeyboardEvent<HTMLInputElement>, idx: number) => {
      if (e.key === "Enter") {
        if (idx === inputHTML.current.length - 1) onNextClick();
        else inputHTML.current[idx + 1].focus();
      } else if (
        e.key === "Backspace" &&
        idx !== 0 &&
        inputHTML.current[idx].value.length === 0
      ) {
        inputHTML.current[idx - 1].focus();
      }
    },
    [onNextClick],
  );
  
  const renderInput = useCallback(() => {
    const input = [];
    inputHTML.current = [];
    for (let i = 0; i < question.answer.length; i++) {
      input.push(
        <input
          type="text"
          className="border "
          maxLength={1}
          key={i}
          onChange={(e) => onInputChange(e, i)}
          onKeyUp={(e) => inputKeyDown(e, i)}
          ref={(el) => (inputHTML.current[i] = el as HTMLInputElement)}
        />
      );
    }
  
    return input;
  }, [question, onInputChange, inputKeyDown]);
  return (
    <div>
      <div className="flex justify-center my-8">
        <div className="flex justify-start min-h-24 w-[60%] bg-f5green-100 rounded-lg p-5 shadow-md">
          <div className="flex items-center justify-center mr-3 text-lg font-semibold rounded-full w-7 h-7 bg-f5green-300 text-neutral-50">
            {index}
          </div>
          <div className="flex flex-wrap text-sm text-f5black-400">
            {question.question}
          </div>
        </div>
      </div>
      <div className="flex justify-center">
          <div>{renderInput()}</div>
          </div>
    </div>
  );
}
