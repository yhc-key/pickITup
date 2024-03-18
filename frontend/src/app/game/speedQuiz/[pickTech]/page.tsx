"use client";
import { useState, useEffect, useCallback } from "react";
import Image from "next/image";

import { speedQuizDataMap } from "@/../data/speedQuizData";
import BackBtn from "../../../../../components/game/backBtn";
import Question from "../../../../../components/game/question";

interface Quiz {
  question: string;
  answer: string;
}

export default function SpeedQuiz(props: any) {
  const [index, setIndex] = useState(0);
  const [questionList, setQuestionList] = useState<Quiz[]>([]);
  const [answerList, setAnswerList] = useState<string[]>([]);
  const [answer, setAnswer] = useState("");

  // 선택한 주제
  const pickTech: string = props.params.pickTech;

  useEffect(() => {
    // 선택한 주제에 대한 질문 받아오기
    const questions: Quiz[] | undefined = speedQuizDataMap.get(pickTech);
    if (questions) {
      setQuestionList(questions);
    }
  }, [pickTech]);

  // 정답 정보 저장
  const addValueToAnswer = useCallback(() => {
    if (index >= 0) {
      // 현재 답변
      let curAnswer: string = "";
    }
  }, []);

  // 다음문제로 넘어가기
  const onNextClick = useCallback(() => {
    addValueToAnswer();
    setIndex((prev) => prev + 1);
  }, [addValueToAnswer]);

  return (
    <div className="flex flex-col">
      <div className="mx-10 mt-2">
        <BackBtn />
      </div>
      {/* <div>{props.params.pickTech}</div> */}
      {questionList[index] ? (
        <div>
          <div className="flex flex-wrap justify-center items-center">
            <div className="flex flex-col mx-1">
              <div className="flex flex-wrap justify-center font-semibold text-4xl tracking-widest my-3">
                <div className="text-f5green-300 mr-3">스피드</div>
                <div className="text-f5black-400">퀴즈</div>
              </div>
              <div className="text-xs text-f5black-400">
                문제를 읽고 알맞은 정답을 입력해주세요!
              </div>
            </div>
            <Image
              src="/images/hourglass2.png"
              alt="gameMachine"
              width={130}
              height={130}
              priority={true}
            />
          </div>
          <Question
            question={questionList[index]}
            index={index + 1}
            onNextClick={onNextClick}
          />
        </div>
      ) : (
        <div>결과화면</div>
      )}
    </div>
  );
}
