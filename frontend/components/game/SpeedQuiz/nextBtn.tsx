"use client";

interface nextBtnProps {
  onNextClick: () => void;
}

export default function NextBtn({ onNextClick }: nextBtnProps) {
  const nextCilckHandler = () => {
    onNextClick();
  };

  return (
    <div className="flex justify-end mt-4 mr-40 mb:fixed mb:top-4 mb:right-1 mb:mr-10">
      <button
        onClick={nextCilckHandler}
        className="px-5 py-2 text-sm font-semibold rounded-3xl text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
      >
        {"다음 문제 >>"}
      </button>
    </div>
  );
}
