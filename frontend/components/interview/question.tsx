"use client";
import { useCallback, useRef, useEffect } from "react";

interface Quiz {
  id: number;
  question: string;
}

interface questionProps {
  question: Quiz;
  index: number;
  onNextClick: () => void;
}

export default function Question({
  question,
  index,
  onNextClick,
}: questionProps) {
  const textAreaHTML = useRef<HTMLTextAreaElement>(null);

  const onTextAreaChange = useCallback(
    (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      const newValue = e.target.value;
      if (textAreaHTML.current) {
        textAreaHTML.current.value = newValue;
      }
    },
    []
  );

  return (
    <div>
      <div className="flex flex-col flex-wrap justify-center my-5">
        <div className="flex justify-center">
          <div className="flex justify-start min-h-24 w-[60%] bg-f5green-100 rounded-lg p-5 shadow-md mb:w-[82%]">
            <div className="flex items-center justify-center mr-3 text-lg font-semibold rounded-full w-7 h-7 bg-f5green-300 text-neutral-50 mb:text-sm">
              {index}
            </div>
            <div className="flex flex-wrap mb:text-sm text-f5black-400">
              {question?.question}
            </div>
          </div>
        </div>
        <div className="flex justify-center my-4">
          <textarea
            className="question-textarea p-6 w-[60%] h-36 text-strat shadow-md question-input rounded-xl text-sm bg-f5gray-200 resize-none mb:w-[80%]"
            onChange={onTextAreaChange}
            ref={textAreaHTML}
          ></textarea>
        </div>
      </div>
    </div>
  );
}
