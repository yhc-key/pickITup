"use client";
import { useState, useEffect } from "react";
import { useMediaQuery } from "react-responsive";
import Image from "next/image";

import Realistic from "../../realistic";
import WrongBox from "./wrongBox";
import RightBox from "./rightBox";
import useAuthStore, { AuthState } from "@/store/authStore";

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
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const [showConfetti, setShowConfetti] = useState<boolean>(false);
  const apiURL = "https://spring.pickITup.online/quizzes/win";
  const isLoggedIn: boolean = useAuthStore(
    (state: AuthState) => state.isLoggedIn
  );

  // 7문제 이상 정답 시 뱃지 획득을 위한 승리횟수 1 증가
  const addWinNumber = async () => {
    if (isLoggedIn) {
      const accessToken = sessionStorage.getItem("accessToken");
      try {
        await fetch(apiURL, {
          method: "PATCH",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        });
      } catch (error) {
        console.log(error);
      }
    } else {
      alert("게임 결과를 저장하기 위해서 로그인이 필요합니다!");
    }
  };

  useEffect(() => {
    const correctCount = answer.filter((e: Answer) => e.correct).length;

    if (correctCount >= 7) {
      setShowConfetti(true);
      addWinNumber();
    }
  }, [answer]);

  return (
    <div>
      <div className="flex flex-wrap items-center justify-center">
        <div className="flex flex-col mt-5 mb-2 ml-10">
          <div className="flex flex-wrap justify-center my-10 text-4xl font-semibold tracking-widest mb:text-3xl">
            <div className="mr-3 text-f5green-300">게임</div>
            <div className="text-f5black-400">결과</div>
          </div>
          <div className="text-xs text-f5black-400"></div>
        </div>
        {isMobile ? (
          <Image
            src="/images/oxIntro.png"
            alt="oxIntro"
            width={95}
            height={65}
            priority={true}
          />
        ) : (
          <Image
            src="/images/oxIntro.png"
            alt="oxIntro"
            width={110}
            height={110}
            priority={true}
          />
        )}
      </div>
      <div className="flex justify-center">
        <span className="flex items-center justify-center h-16 rounded-full w-28 bg-f5gray-300 mb:h-10">
          <b className="text-3xl mb:text-2xl">
            {answer.filter((e: Answer): boolean => e.correct).length}
          </b>
          &nbsp;&nbsp;/&nbsp;&nbsp;10
        </span>
      </div>
      <div className="mt-12 mx-28 mb:mx-8">
        <div className="flex flex-wrap justify-center gap-5">
          {answer.slice(0, 10).map((e: Answer, idx: number) => (
            <div key={idx}>{e.correct ? <RightBox /> : <WrongBox />}</div>
          ))}
        </div>
      </div>
      {showConfetti && <Realistic />}
    </div>
  );
}
