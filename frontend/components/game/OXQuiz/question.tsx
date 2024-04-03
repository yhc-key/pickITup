"use client";
interface Quiz {
  question: string;
  answer: boolean;
}

interface questionProps {
  question: Quiz;
  index: number;
}

export default function Question({ question, index }: questionProps) {
  return (
    <div>
      <div className="flex justify-center my-8">
        <div className="flex justify-start min-h-24 w-[60%] bg-f5green-100 rounded-lg p-5 shadow-md mb:w-[82%]">
          <div className="flex items-center justify-center p-2 mr-3 text-lg font-semibold rounded-full w-7 h-7 bg-f5green-300 text-neutral-50 mb:text-sm">
            {index}
          </div>
          <div className="flex flex-wrap mb:text-sm text-f5black-400">
            {question.question}
          </div>
        </div>
      </div>
    </div>
  );
}
