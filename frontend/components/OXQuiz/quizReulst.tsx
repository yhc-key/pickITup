"use client";
import { useState, useEffect } from "react";
import Image from "next/image";

import Realistic from "../realistic";
import WrongBox from "./wrongBox";
import RightBox from "./rightBox";

interface Answer {
  question: string;
  answer: boolean;
  user: boolean;
  correct: boolean;
  index: number;
}

interface QuizResultProps {
  answer: Answer[];
}

export default function QuizResult({ answer }: QuizResultProps) {
  const [showConfetti, setShowConfetti] = useState(false);

  useEffect(() => {
    const correctCount = answer.filter((e: Answer) => e.correct).length;

    if (correctCount >= 7) {
      setShowConfetti(true);
    }
  }, [answer]);

  return (
    <div>
      <div className="flex flex-wrap items-center justify-center">
        <div className="flex flex-col mx-1 ml-20">
          <div className="flex flex-wrap justify-center my-3 text-4xl font-semibold tracking-widest">
            <div className="mr-3 text-f5green-300">게임</div>
            <div className="text-f5black-400">결과</div>
          </div>
          <div className="text-xs text-f5black-400"></div>
        </div>
        <Image
          src="/images/oxIntro.png"
          alt="oxIntro"
          width={190}
          height={130}
          priority={true}
        />
      </div>
      <div className="flex justify-center">
        <span className="flex items-center justify-center h-16 rounded-full w-28 bg-f5gray-300">
          <b className="text-3xl">
            {answer.filter((e: Answer): boolean => e.correct).length}
          </b>
          &nbsp;&nbsp;/&nbsp;&nbsp;10
        </span>
      </div>
      <div className="mt-12 mx-28">
        <div className="flex justify-around">
          {answer.slice(0, 5).map((e: Answer, idx: number) => (
            <div key={idx}>{e.correct ? <RightBox /> : <WrongBox />}</div>
          ))}
        </div>
        <div className="flex justify-around mt-8">
          {answer.slice(5, 10).map((e: Answer, idx: number) => (
            <div key={idx}>{e.correct ? <RightBox /> : <WrongBox />}</div>
          ))}
        </div>
      </div>
      {showConfetti && <Realistic />}
    </div>
  );
};
