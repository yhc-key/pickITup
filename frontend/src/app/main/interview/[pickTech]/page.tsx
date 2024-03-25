"use client";
import { useState, useEffect, useCallback } from "react";
import Image from "next/image";
import Link from "next/link";

import Question from "@/components/interview/question";
import TimeBar from "@/components/interview/timebar";
import NextBtn from "@/components/interview/nextBtn";
import BackBtn from "@/components/interview/backBtn";

import { interviewDataMap } from "@/data/interviewData";
interface Quiz {
  question: string;
  answer: string;
}

interface Answer {
  question: string;
  user: string;
  index: number;
}

export default function InterView(props: any) {
  const [index, setIndex] = useState(0);
  const [questionList, setQuestionList] = useState<Quiz[]>([]);
  const [answer, setAnswer] = useState<Answer[]>([]);

  // 선택한 주제
  const pickTech: string = props.params.pickTech;

  useEffect(() => {
    // 선택한 주제에 대한 질문 받아오기
    const questions: Quiz[] | undefined = interviewDataMap.get(pickTech);
    if (questions) {
      setQuestionList(questions);
    }
  }, [pickTech]);

  // 정답 정보 저장
  const addValueToAnswer = useCallback(() => {
    if (index >= 0) {
      let curAnswer: string = "";
      document.querySelectorAll<HTMLTextAreaElement>(".question-textarea");

      // answer 배열에 추가
      setAnswer([
        ...answer,
        // 질문, 사용자 입력값, 정답유무, 문제번호
        {
          question: questionList[index].question,
          user: curAnswer,
          index: index + 1,
        },
      ]);
      console.log(answer);
    }
  }, [questionList, answer, index]);

  // 다음문제로 넘어가기
  const onNextClick: () => void = useCallback(() => {
    addValueToAnswer();
    // 문제번호 1 증가
    setIndex((prev: number) => prev + 1);
    // 제한시간 10초로 갱신
  }, [addValueToAnswer]);

  return (
    <div className="flex flex-col my-4">
      <div className="mx-10">
        <BackBtn />
      </div>
      <div className="flex flex-wrap justify-center mx-auto mb-1">
        <div className="flex flex-col justify-evenly">
          <div className="flex items-center justify-center text-5xl font-semibold tracking-wider">
            <Image
              src="/images/pencil.png"
              alt="pencil"
              width={120}
              height={120}
              priority={true}
            />
            <div className="my-2 mr-1 text-f5green-300">면접 대비 </div>
            <div className="my-2 ml-2 text-f5black-400">QUIZ</div>
          </div>
        </div>
      </div>
      <Question
        question={questionList[index]}
        index={index + 1}
        onNextClick={onNextClick}
      />
      <TimeBar onNextClick={onNextClick} index={index} />
      <NextBtn onNextClick={onNextClick} />
    </div>
  );
}
