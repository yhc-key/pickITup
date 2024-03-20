"use client";
import { useState, useEffect, useCallback } from "react";
import Image from "next/image";
import { useRouter } from "next/navigation";

import { speedQuizDataMap } from "@/data/speedQuizData";
import BackBtn from "@/components/game/backBtn";
import Question from "@/components/SpeedQuiz/question";
import TimeBar from "@/components/SpeedQuiz/timebar";
import NextBtn from "@/components/SpeedQuiz/nextBtn";
import QuizResult from "@/components/SpeedQuiz/quizResult";

interface Quiz {
  question: string;
  answer: string;
}

interface Answer {
  question: string;
  answer: string;
  user: string;
  correct: boolean;
  index: number;
}

export default function SpeedQuiz(props: any) {
  const router = useRouter();

  const [index, setIndex] = useState(0);
  const [questionList, setQuestionList] = useState<Quiz[]>([]);
  const [answerList, setAnswerList] = useState<string[]>([]);
  const [answer, setAnswer] = useState<Answer[]>([]);

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
  const addValueToAnswer: () => void = useCallback(() => {
    if (index >= 0) {
      let curAnswer: string = "";
      document
        .querySelectorAll<HTMLInputElement>(".question-input")
        .forEach((e: HTMLInputElement) => {
          curAnswer += e.value === "" ? " " : e.value;
          e.value = "";
        });

      // 영어인 경우 대소문자를 구분하지 않고 비교
      const isEnglish: RegExp = /^[A-Za-z]+$/;
      const correct: boolean = isEnglish.test(curAnswer)
        ? curAnswer.toUpperCase() === questionList[index].answer.toUpperCase()
        : curAnswer === questionList[index].answer;

      // answer 배열에 추가
      setAnswer([
        ...answer,
        // 질문, 사용자 입력값, 정답유무, 문제번호
        {
          question: questionList[index].question,
          answer: questionList[index].answer,
          user: curAnswer,
          correct: correct,
          index: index + 1,
        },
      ]);
    }
  }, [questionList, answer, index]);

  // 다음문제로 넘어가기
  const onNextClick: () => void = useCallback(() => {
    addValueToAnswer();
    // 문제번호 1 증가
    setIndex((prev: number) => prev + 1);
    // 제한시간 10초로 갱신
  }, [addValueToAnswer]);

  const listCilckHandler = (): void => {
    router.push("/game");
  };

  return (
    <div className="flex flex-col">
      {/* <div>{props.params.pickTech}</div> */}
      {questionList[index] ? (
        <div>
          <div className="mx-10 mt-4">
            <BackBtn />
          </div>
          <div className="flex flex-wrap items-center justify-center">
            <div className="flex flex-col mx-1 ml-10">
              <div className="flex flex-wrap justify-center my-3 text-4xl font-semibold tracking-widest">
                <div className="mr-3 text-f5green-300">스피드</div>
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
          <TimeBar onNextClick={onNextClick} index={index} />
          <NextBtn onNextClick={onNextClick} />
        </div>
      ) : (
        <div>
          <QuizResult answer={answer} />
          <div className="flex justify-end mt-8 mr-28">
            <button
              onClick={listCilckHandler}
              className="px-5 py-2 text-sm font-semibold bg-opacity-80 rounded-3xl text-neutral-100 bg-f5gray-500 hover:bg-opacity-100 ring-1 ring-inset ring-f5gray-400/10"
            >
              {"게임 목록 >>"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
