interface Quiz {
  question: string;
  answer: string;
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
  return (
    <div>
      <div className="flex justify-center my-8">
        <div className="flex justify-start min-h-24 w-[60%] bg-f5green-100 rounded-lg p-5 shadow-md">
          <div className="flex justify-center items-center w-7 h-7 rounded-full bg-f5green-300 text-neutral-50 font-semibold mr-3 text-lg">
            {index}
          </div>
          <div className=" flex flex-wrap text-f5black-400 text-sm">
            {question.question}
          </div>
        </div>
      </div>
      <div className="flex justify-end mr-20">
        <button onClick={onNextClick}>{"다음 문제 >>"} </button>
      </div>
    </div>
  );
}
